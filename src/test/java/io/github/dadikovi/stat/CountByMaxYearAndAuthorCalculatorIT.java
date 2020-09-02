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
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = LibraryStatsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CountByMaxYearAndAuthorCalculatorIT extends AbstractStatCalculatorIT<CountByMaxYearAndAuthorCalculator> {

    CountByMaxYearAndAuthorCalculatorIT() {
        super(StatType.COUNT_BY_MAX_YEAR_AND_AUTHOR);
    }

    @Test
    @Transactional
    public void testWithEmptyValues() throws Exception {
        super.setAdditionalParams(maxYear1978());
        super.testWithEmptyValues();
    }

    @Test
    @Transactional
    public void testWithOneValue() throws Exception {
        super.setAdditionalParams(maxYear1978());
        super.addValuesAndExpectGivenResult(Collections.singletonList(warAndPeace()), String.valueOf(2));
    }

    @Test
    @Transactional
    public void testWithMultipleValues() throws Exception {
        super.setAdditionalParams(maxYear1978());
        super.addValuesAndExpectGivenResult(Arrays.asList(warAndPeace(), hitchhikersGuideToTheGalaxy()), String.valueOf(4));
    }

    private static Map<String, String> maxYear1978() {
        Map<String, String> params = new HashMap<>();
        params.put("maxYear", "1978");
        return params;
    }
}
