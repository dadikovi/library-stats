package io.github.dadikovi.stat;

import io.github.dadikovi.LibraryStatsApp;
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
public class CountOfThirdBookByAuthorCalculatorIT extends AbstractStatCalculatorIT<CountOfThirdBookByAuthorCalculator> {

    CountOfThirdBookByAuthorCalculatorIT() {
        super(StatType.COUNT_OF_THIRD_BOOK);
    }

    @Test
    @Transactional
    public void testWithEmptyValues() throws Exception {
        super.testWithEmptyValues();
    }

    @Test
    @Transactional
    public void testWithOneValue() throws Exception {
        super.addValuesAndExpectGivenResult(Arrays.asList(
            warAndPeace(), annaKarenina(), resurrection()
        ), LEO_TOLSTOY, String.valueOf(15));
    }

    @Test
    @Transactional
    public void testWithMultipleValues() throws Exception {
        super.addValuesAndExpectGivenResult(Arrays.asList(
            onPhotography(), illnessAsMetaphor(), resurrection(),
            warAndPeace(), annaKarenina(), resurrection(), hitchhikersGuideToTheGalaxy()
        ), LEO_TOLSTOY, String.valueOf(15));
    }
}
