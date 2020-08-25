package io.github.dadikovi.web.rest;

import io.github.dadikovi.domain.Stat;
import io.github.dadikovi.repository.StatRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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
     * {@code GET  /stats} : get all the stats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stats in body.
     */
    @GetMapping("/stats")
    @ApiOperation("Gets all stats.")
    public List<Stat> getAllStats() {
        log.debug("REST request to get all Stats");
        return statRepository.findAll();
    }

    /**
     * {@code GET  /stats-filtered} : get all the stats filtered by the provided attribute values.
     *
     * @param stat the example which will be the param of the query by example query
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the filtered list of stats in body.
     */
    @GetMapping("/stats-filtered")
    @ApiOperation("Gets all stats which are matching with the provided example.")
    public List<Stat> getAllStatsByExample(@ApiParam(
        name = "stat",
        type = "Stat",
        value = "The example which will be the param of the query-by-example query. "
            + "A stat will be returned if and only if all of the field values equal with the field values of this parameter."
    ) @Valid Stat stat) {
        log.debug("REST request to get filtered Stat : {}", stat);
        return statRepository.findAll(Example.of(stat));
    }


}
