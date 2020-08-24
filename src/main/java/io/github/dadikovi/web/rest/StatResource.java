package io.github.dadikovi.web.rest;

import io.github.dadikovi.domain.Stat;
import io.github.dadikovi.repository.StatRepository;
import io.github.dadikovi.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.dadikovi.domain.Stat}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StatResource {

    private final Logger log = LoggerFactory.getLogger(StatResource.class);

    private static final String ENTITY_NAME = "libraryStatsStat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatRepository statRepository;

    public StatResource(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    /**
     * {@code POST  /stats} : Create a new stat.
     *
     * @param stat the stat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stat, or with status {@code 400 (Bad Request)} if the stat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stats")
    public ResponseEntity<Stat> createStat(@RequestBody Stat stat) throws URISyntaxException {
        log.debug("REST request to save Stat : {}", stat);
        if (stat.getId() != null) {
            throw new BadRequestAlertException("A new stat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Stat result = statRepository.save(stat);
        return ResponseEntity.created(new URI("/api/stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stats} : Updates an existing stat.
     *
     * @param stat the stat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stat,
     * or with status {@code 400 (Bad Request)} if the stat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stats")
    public ResponseEntity<Stat> updateStat(@RequestBody Stat stat) throws URISyntaxException {
        log.debug("REST request to update Stat : {}", stat);
        if (stat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Stat result = statRepository.save(stat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stats} : get all the stats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stats in body.
     */
    @GetMapping("/stats")
    public List<Stat> getAllStats() {
        log.debug("REST request to get all Stats");
        return statRepository.findAll();
    }

    /**
     * {@code GET  /stats/:id} : get the "id" stat.
     *
     * @param id the id of the stat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stats/{id}")
    public ResponseEntity<Stat> getStat(@PathVariable Long id) {
        log.debug("REST request to get Stat : {}", id);
        Optional<Stat> stat = statRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stat);
    }

    /**
     * {@code DELETE  /stats/:id} : delete the "id" stat.
     *
     * @param id the id of the stat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stats/{id}")
    public ResponseEntity<Void> deleteStat(@PathVariable Long id) {
        log.debug("REST request to delete Stat : {}", id);
        statRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
