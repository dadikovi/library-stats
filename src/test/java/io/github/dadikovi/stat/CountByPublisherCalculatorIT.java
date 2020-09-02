package io.github.dadikovi.stat;

import io.github.dadikovi.LibraryStatsApp;
import io.github.dadikovi.domain.Book;
import io.github.dadikovi.domain.enumeration.StatType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

@SpringBootTest(classes = LibraryStatsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CountByPublisherCalculatorIT extends AbstractStatCalculatorIT<CountByPublisherCalculator> {

    CountByPublisherCalculatorIT() {
        super(StatType.COUNT_BY_PUBLISHER);
    }

    @Test
    @Transactional
    public void testWithEmptyValues() throws Exception {
        super.testWithEmptyValues();
    }

    @Test
    @Transactional
    public void testWithOneValue() throws Exception {
        super.addValuesAndExpectGivenResult(Collections.singletonList(warAndPeace()), "The Russian Messenger", String.valueOf(2));
    }

    @Test
    @Transactional
    public void testWithMultipleValues() throws Exception {
        super.addValuesAndExpectGivenResult(Arrays.asList(warAndPeace(), annaKarenina()), "The Russian Messenger", String.valueOf(12));
    }

    private static Book annaKarenina() {
        return new Book()
            .title("Anna Karenina")
            .publisher("The Russian Messenger")
            .count(10L);
    }
}
