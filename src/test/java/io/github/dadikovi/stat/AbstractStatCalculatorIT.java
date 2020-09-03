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
    protected static final String LEO_TOLSTOY = "Leo Tolstoy";

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

    protected @NotNull ResultActions addValuesAndExpectOneResult( List<Book> booksToAdd, String objectName ) throws Exception {
        booksToAdd.forEach(book -> listener.handle(new ShelfChangedMessage(ChangeType.CREATE, book)));

        return performRestMockAndExpectOneResult(objectName);
    }

    protected void addValuesAndExpectGivenResult(List<Book> booksToAdd, String objectName, String expectedResult) throws Exception {
        addValuesAndExpectOneResult(booksToAdd, objectName)
            .andExpect(jsonPath("$.statValue").value(is(expectedResult)));
    }

    protected void setAdditionalParams( Map<String, String> additionalParams ) {
        this.additionalParams = additionalParams;
    }

    @NotNull
    private ResultActions performRestMockAndExpectOneResult( String objectName ) throws Exception {
        return performRestMockAndExpectOkStatus(objectName)
            .andExpect(jsonPath("$.statType").value(is(calculator.getStatType().name())));
    }

    @NotNull
    private ResultActions performRestMockAndExpectOkStatus( String objectName ) throws Exception {
        return restStatMockMvc
            .perform(get(getUrlTemplate(objectName)))
            .andExpect(status().isOk());
    }

    private String getUrlTemplate(String objectName) {
        StringBuilder urlTemplateBuilder
            = new StringBuilder("/api/stat?statObjectName=" + objectName + "&statType=" + calculator.getStatType().name());

        if (!CollectionUtils.isEmpty(this.additionalParams)) {
            this.additionalParams.forEach((key, value) -> urlTemplateBuilder.append("&" + key + "=" + value));
        }

        return urlTemplateBuilder.toString();
    }

    protected static Book annaKarenina() {
        return new Book()
            .title("Anna Karenina")
            .author(LEO_TOLSTOY)
            .publisher("The Russian Messenger")
            .publishYear(1877L)
            .count(10L);
    }

    protected static Book resurrection() {
        return new Book()
            .title("Anna Karenina")
            .author(LEO_TOLSTOY)
            .publisher("The Russian Messenger")
            .publishYear(1899L)
            .count(15L);
    }

    protected static Book warAndPeace() {
        return new Book()
            .title(WAR_AND_PEACE)
            .author(LEO_TOLSTOY)
            .publisher("The Russian Messenger")
            .publishYear(1869L)
            .createdAt(Instant.parse("2020-01-01T10:15:30Z"))
            .count(2L);
    }

    protected static Book hitchhikersGuideToTheGalaxy() {
        return new Book()
            .title("The Hitchhiker's Guide to the Galaxy")
            .author(LEO_TOLSTOY) // Its not, but we have to lie now.
            .publisher("Megadodo Publications")
            .publishYear(1978L)
            .createdAt(Instant.parse("2020-01-01T10:15:30Z"))
            .count(2L);
    }

    protected static Book onPhotography() {
        return new Book()
            .title("On Photography")
            .author("Susan Sontag")
            .count(10L);
    }

    protected static Book illnessAsMetaphor() {
        return new Book()
            .title("Illness as Metaphor")
            .author("Susan Sontag")
            .count(11L);
    }

    protected static Book regardingThePainOfOthers() {
        return new Book()
            .title("Regarding the Pain of Others ")
            .author("Susan Sontag")
            .count(12L);
    }
}
