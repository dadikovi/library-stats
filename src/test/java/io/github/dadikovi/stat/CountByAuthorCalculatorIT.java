package io.github.dadikovi.stat;

import io.github.dadikovi.LibraryStatsApp;
import io.github.dadikovi.config.ShelfChangedListener;
import io.github.dadikovi.domain.Book;
import io.github.dadikovi.domain.ShelfChangedMessage;
import io.github.dadikovi.domain.enumeration.ChangeType;
import io.github.dadikovi.domain.enumeration.StatObject;
import io.github.dadikovi.repository.BookRepository;
import io.github.dadikovi.service.StatCalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = LibraryStatsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CountByAuthorCalculatorIT {

    @Autowired
    private MockMvc restStatMockMvc;

    @Autowired
    private ShelfChangedListener listener;

    public static final String WAR_AND_PEACE = "War and Peace";
    public static final String LEO_TOLSTOY = "Leo Tolstoy";

    private static Book warAndPeace() {
        return new Book()
            .title(WAR_AND_PEACE)
            .author(LEO_TOLSTOY)
            .publisher("The Russian Messenger")
            .publishYear(1869L)
            .createdAt(Instant.now())
            .count(2L);
    }

    private static Book hitchhikersGuideToTheGalaxy() {
        return new Book()
            .title("The Hitchhiker's Guide to the Galaxy")
            .author(LEO_TOLSTOY) // Its not, but we have to lie now.
            .publisher("Megadodo Publications")
            .publishYear(1978L)
            .createdAt(Instant.now())
            .count(2L);
    }

    @Test
    @Transactional
    public void testWithEmptyValues() throws Exception {
        restStatMockMvc.perform(get("/api/stats-filtered?objectType=" + StatObject.AUTHOR.toString() + "&statType=Count by author"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    public void testWithOneValue() throws Exception {
        listener.handle(new ShelfChangedMessage(ChangeType.CREATE, warAndPeace()));

        restStatMockMvc.perform(get("/api/stats-filtered?objectType=" + StatObject.AUTHOR.toString() + "&statType=Count by author"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].objectType").value(hasItem(StatObject.AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].objectName").value(hasItem(LEO_TOLSTOY)))
            .andExpect(jsonPath("$.[*].statType").value(hasItem("Count by author")))
            .andExpect(jsonPath("$.[*].statValue").value(hasItem(String.valueOf(2))))
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Transactional
    public void testWithMultipleValues() throws Exception {
        listener.handle(new ShelfChangedMessage(ChangeType.CREATE, warAndPeace()));
        listener.handle(new ShelfChangedMessage(ChangeType.CREATE, hitchhikersGuideToTheGalaxy()));

        restStatMockMvc.perform(get("/api/stats-filtered?objectType=" + StatObject.AUTHOR.toString() + "&statType=Count by author"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].objectType").value(hasItem(StatObject.AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].objectName").value(hasItem(LEO_TOLSTOY)))
            .andExpect(jsonPath("$.[*].statType").value(hasItem("Count by author")))
            .andExpect(jsonPath("$.[*].statValue").value(hasItem(String.valueOf(4))))
            .andExpect(jsonPath("$", hasSize(1)));
    }
}
