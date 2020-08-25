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

    private static final StatObject DEFAULT_OBJECT_TYPE = StatObject.AUTHOR;
    private static final StatObject UPDATED_OBJECT_TYPE = StatObject.PUBLISHER;

    private static final String DEFAULT_OBJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STAT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_STAT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STAT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_STAT_VALUE = "BBBBBBBBBB";

    @Autowired
    private StatRepository statRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatMockMvc;

    private static Stat douglasAdams() {
        return new Stat()
            .objectType(StatObject.AUTHOR)
            .objectName("Dougles Adams");
    private Stat stat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stat createEntity(EntityManager em) {
        Stat stat = new Stat()
            .objectType(DEFAULT_OBJECT_TYPE)
            .objectName(DEFAULT_OBJECT_NAME)
            .statType(DEFAULT_STAT_TYPE)
            .statValue(DEFAULT_STAT_VALUE);
        return stat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stat createUpdatedEntity(EntityManager em) {
        Stat stat = new Stat()
            .objectType(UPDATED_OBJECT_TYPE)
            .objectName(UPDATED_OBJECT_NAME)
            .statType(UPDATED_STAT_TYPE)
            .statValue(UPDATED_STAT_VALUE);
        return stat;
    }

    @BeforeEach
    public void initTest() {
        stat = createEntity(em);
    }

    @Test
    @Transactional
    public void createStat() throws Exception {
        int databaseSizeBeforeCreate = statRepository.findAll().size();
        // Create the Stat
        restStatMockMvc.perform(post("/api/stats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stat)))
            .andExpect(status().isCreated());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeCreate + 1);
        Stat testStat = statList.get(statList.size() - 1);
        assertThat(testStat.getObjectType()).isEqualTo(DEFAULT_OBJECT_TYPE);
        assertThat(testStat.getObjectName()).isEqualTo(DEFAULT_OBJECT_NAME);
        assertThat(testStat.getStatType()).isEqualTo(DEFAULT_STAT_TYPE);
        assertThat(testStat.getStatValue()).isEqualTo(DEFAULT_STAT_VALUE);
    }

    @Test
    @Transactional
    public void createStatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statRepository.findAll().size();

        // Create the Stat with an existing ID
        stat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatMockMvc.perform(post("/api/stats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stat)))
            .andExpect(status().isBadRequest());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStats() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);

        // Get all the statList
        restStatMockMvc.perform(get("/api/stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stat.getId().intValue())))
            .andExpect(jsonPath("$.[*].objectType").value(hasItem(DEFAULT_OBJECT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].objectName").value(hasItem(DEFAULT_OBJECT_NAME)))
            .andExpect(jsonPath("$.[*].statType").value(hasItem(DEFAULT_STAT_TYPE)))
            .andExpect(jsonPath("$.[*].statValue").value(hasItem(DEFAULT_STAT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getStat() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);

        // Get the stat
        restStatMockMvc.perform(get("/api/stats/{id}", stat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stat.getId().intValue()))
            .andExpect(jsonPath("$.objectType").value(DEFAULT_OBJECT_TYPE.toString()))
            .andExpect(jsonPath("$.objectName").value(DEFAULT_OBJECT_NAME))
            .andExpect(jsonPath("$.statType").value(DEFAULT_STAT_TYPE))
            .andExpect(jsonPath("$.statValue").value(DEFAULT_STAT_VALUE));
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
    public void updateStat() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);

        int databaseSizeBeforeUpdate = statRepository.findAll().size();

        // Update the stat
        Stat updatedStat = statRepository.findById(stat.getId()).get();
        // Disconnect from session so that the updates on updatedStat are not directly saved in db
        em.detach(updatedStat);
        updatedStat
            .objectType(UPDATED_OBJECT_TYPE)
            .objectName(UPDATED_OBJECT_NAME)
            .statType(UPDATED_STAT_TYPE)
            .statValue(UPDATED_STAT_VALUE);

        restStatMockMvc.perform(put("/api/stats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStat)))
            .andExpect(status().isOk());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate);
        Stat testStat = statList.get(statList.size() - 1);
        assertThat(testStat.getObjectType()).isEqualTo(UPDATED_OBJECT_TYPE);
        assertThat(testStat.getObjectName()).isEqualTo(UPDATED_OBJECT_NAME);
        assertThat(testStat.getStatType()).isEqualTo(UPDATED_STAT_TYPE);
        assertThat(testStat.getStatValue()).isEqualTo(UPDATED_STAT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingStat() throws Exception {
        int databaseSizeBeforeUpdate = statRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatMockMvc.perform(put("/api/stats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stat)))
            .andExpect(status().isBadRequest());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStat() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);

        int databaseSizeBeforeDelete = statRepository.findAll().size();

        // Delete the stat
        restStatMockMvc.perform(delete("/api/stats/{id}", stat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
