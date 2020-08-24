package io.github.dadikovi.web.rest;

import io.github.dadikovi.LibraryStatsApp;
import io.github.dadikovi.domain.Stat;
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
    private EntityManager em;

    @Autowired
    private MockMvc restStatMockMvc;

    @Test
    @Transactional
    public void getAllStats() throws Exception {
        // TODO implement me
    }

    @Test
    @Transactional
    public void getStat() throws Exception {
        // TODO implement me
    }

    @Test
    @Transactional
    public void getNonExistingStat() throws Exception {
        // Get the stat
        restStatMockMvc.perform(get("/api/stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void getOneStatByExample() throws Exception {
        // TODO implement me
    }

    @Test
    @Transactional
    public void getAllStatsByExample() throws Exception {
        // TODO implement me
    }

    @Test
    @Transactional
    public void getAllStatsByNonExistingExample() throws Exception {
        // TODO implement me
    }
}
