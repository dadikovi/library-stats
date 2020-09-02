package io.github.dadikovi.stat;

import io.github.dadikovi.LibraryStatsApp;
import io.github.dadikovi.domain.enumeration.StatType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@SpringBootTest(classes = LibraryStatsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OldestBookCalculatorIT extends AbstractStatCalculatorIT<OldestBookCalculator> {

    OldestBookCalculatorIT() {
        super(StatType.OLDEST_BOOK);
    }

    @Test
    @Transactional
    public void testWithEmptyValues() throws Exception {
        super.testWithEmptyValues();
    }

    @Test
    @Transactional
    public void testWithOneValue() throws Exception {
        super.addValuesAndExpectGivenResult(Collections.singletonList(warAndPeace()), LEO_TOLSTOY,
            warAndPeace().getAuthor() + ": " + warAndPeace().getTitle());
    }

    @Test
    @Transactional
    public void testWithMultipleValues() throws Exception {
        super.addValuesAndExpectGivenResult(Arrays.asList(warAndPeace(), hitchhikersGuideToTheGalaxy()), LEO_TOLSTOY,
            warAndPeace().getAuthor() + ": " + warAndPeace().getTitle());
    }
}
