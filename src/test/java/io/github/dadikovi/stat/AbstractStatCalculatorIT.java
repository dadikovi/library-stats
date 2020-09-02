package io.github.dadikovi.stat;

import io.github.dadikovi.config.ShelfChangedListener;
import io.github.dadikovi.domain.Book;
import io.github.dadikovi.domain.ShelfChangedMessage;
import io.github.dadikovi.domain.enumeration.ChangeType;
import io.github.dadikovi.domain.enumeration.StatType;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractStatCalculatorIT<T extends StatCalculator> {

    private static final String WAR_AND_PEACE = "War and Peace";
    private static final String LEO_TOLSTOY = "Leo Tolstoy";

    @Autowired
    private MockMvc restStatMockMvc;

    @Autowired
    private ShelfChangedListener listener;

    @Autowired
    private BeanFactory factory;

    private T calculator;
    private StatType statType;

    private Map<String, String> additionalParams;

    AbstractStatCalculatorIT(StatType statType) {
        this.statType = statType;
    }

    @PostConstruct
    void initCalculator() {
        calculator = (T) factory.getBean(statType.getBeanType());
    }

    protected abstract void testWithOneValue() throws Exception;
    protected abstract void testWithMultipleValues() throws Exception;

    protected void testWithEmptyValues() throws Exception {
        performRestMockAndExpectOkStatus("dummy");
    }

    protected @NotNull ResultActions addValuesAndExpectOneResult( List<Book> booksToAdd) throws Exception {
        booksToAdd.forEach(book -> listener.handle(new ShelfChangedMessage(ChangeType.CREATE, book)));

        return performRestMockAndExpectOneResult();
    }

    protected void addValuesAndExpectGivenResult(List<Book> booksToAdd, String expectedResult) throws Exception {
        addValuesAndExpectOneResult(booksToAdd)
            .andExpect(jsonPath("$.statValue").value(is(expectedResult)));
    }

    protected void setAdditionalParams( Map<String, String> additionalParams ) {
        this.additionalParams = additionalParams;
    }

    @NotNull
    private ResultActions performRestMockAndExpectOneResult() throws Exception {
        return performRestMockAndExpectOkStatus(getRelevantObjectName(warAndPeace()))
            .andExpect(jsonPath("$.statType").value(is(calculator.getStatType().name())));
    }

    @NotNull
    private ResultActions performRestMockAndExpectOkStatus( String objectName ) throws Exception {
        return restStatMockMvc
            .perform(get(getUrlTemplate(objectName)))
            .andExpect(status().isOk());
    }

    private String getRelevantObjectName(Book book) {
        switch ( this.calculator.getStatObject() ) {
            case AUTHOR:
                return book.getAuthor();
            case PUBLISHER:
                return book.getPublisher();
            case GLOBAL:
                return "GLOBAL - TODO";
            default:
                return "NONE";
        }
    }

    private String getUrlTemplate(String objectName) {
        StringBuilder urlTemplateBuilder
            = new StringBuilder("/api/stat?statObjectName=" + objectName + "&statType=" + calculator.getStatType().name());

        if (!CollectionUtils.isEmpty(this.additionalParams)) {
            this.additionalParams.forEach((key, value) -> urlTemplateBuilder.append("&" + key + "=" + value));
        }

        return urlTemplateBuilder.toString();
    }

    protected static Book warAndPeace() {
        return new Book()
            .title(WAR_AND_PEACE)
            .author(LEO_TOLSTOY)
            .publisher("The Russian Messenger")
            .publishYear(1869L)
            .createdAt(Instant.now())
            .count(2L);
    }

    protected static Book hitchhikersGuideToTheGalaxy() {
        return new Book()
            .title("The Hitchhiker's Guide to the Galaxy")
            .author(LEO_TOLSTOY) // Its not, but we have to lie now.
            .publisher("Megadodo Publications")
            .publishYear(1978L)
            .createdAt(Instant.now())
            .count(2L);
    }
}
