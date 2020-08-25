package io.github.dadikovi.web.rest;

import io.github.dadikovi.LibraryStatsApp;
import io.github.dadikovi.domain.Stat;
import io.github.dadikovi.repository.BookRepository;
import io.github.dadikovi.repository.StatRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.dadikovi.domain.enumeration.StatObject;
/**
 * Integration tests for the {@link StatResource} REST controller.
 */
@SpringBootTest(classes = LibraryStatsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StatResourceIT {

    @Autowired
    private StatRepository statRepository;

    @Autowired
    private MockMvc restStatMockMvc;

    private static Stat douglasAdams() {
        return new Stat()
            .objectType(StatObject.AUTHOR)
            .objectName("Dougles Adams")
            .statType("Count of books")
            .statValue(String.valueOf(10));
    }

    private static Stat megadodoPublications() {
        return new Stat()
            .objectType(StatObject.PUBLISHER)
            .objectName("Megadodo Publications")
            .statType("Count of books")
            .statValue(String.valueOf(10));
    }

    @Test
    @Transactional
    public void getAllStats() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(douglasAdams());

        // Get all the statList
        restStatMockMvc.perform(get("/api/stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].objectType").value(hasItem(StatObject.AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].objectName").value(hasItem("Dougles Adams")))
            .andExpect(jsonPath("$.[*].statType").value(hasItem("Count of books")))
            .andExpect(jsonPath("$.[*].statValue").value(hasItem(String.valueOf(10))));
    }

    @Test
    @Transactional
    public void getOneStatByExample() throws Exception {
        statRepository.saveAndFlush(megadodoPublications());
        statRepository.saveAndFlush(douglasAdams());

        restStatMockMvc.perform(get("/api/stats-filtered?objectType=" + StatObject.AUTHOR.toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].objectName").value(hasItem("Dougles Adams")))
            .andExpect(jsonPath("$.[*].statType").value(hasItem("Count of books")))
            .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    @Transactional
    public void getAllStatsByExample() throws Exception {
        statRepository.saveAndFlush(megadodoPublications());
        statRepository.saveAndFlush(douglasAdams());

        restStatMockMvc.perform(get("/api/stats-filtered?statValue=10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Transactional
    public void getAllStatsByNonExistingExample() throws Exception {
        statRepository.saveAndFlush(megadodoPublications());
        statRepository.saveAndFlush(douglasAdams());

        restStatMockMvc.perform(get("/api/stats-filtered?statValue=20"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    public void getNonExistingStat() throws Exception {
        // Get the stat
        restStatMockMvc.perform(get("/api/stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }


}
