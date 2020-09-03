package io.github.dadikovi.web.rest;

import io.github.dadikovi.domain.Stat;
import io.github.dadikovi.domain.enumeration.StatType;
import io.github.dadikovi.stat.StatCalculator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.dadikovi.domain.Stat}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StatResource {

    @Autowired
    private BeanFactory beanFactory;

    @GetMapping("/stat")
    @ApiOperation("Gets all stats which are matching with the provided example.")
    public ResponseEntity<Stat> getStat(@ApiParam(
            name = "statType",
            type = "StatType",
            value = "The type of the statistics"
        ) @Valid @RequestParam StatType statType,
        @ApiParam(
            name = "statObjectName",
            type = "String",
            value = "The object the stat should be returned to. For global stats you can leave this empty."
        ) @Valid @RequestParam String statObjectName,
        @ApiParam(
            name = "allRequestParams",
            type = "String",
            value = "Some stats may require additional parameters."
        ) @RequestParam Map<String,String> allRequestParams ) {
        StatCalculator calculator = (StatCalculator) beanFactory.getBean(statType.getBeanType());

        Optional<String> value = Optional.ofNullable(calculator.getStatForObject(statObjectName, allRequestParams));

        return ResponseEntity.ok(new Stat()
            .statValue(value.orElse(""))
            .statType(statType)
            .objectType(calculator.getStatObject())
            .objectName(statObjectName));
    }
}
