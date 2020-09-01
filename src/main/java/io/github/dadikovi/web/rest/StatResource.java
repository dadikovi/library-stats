package io.github.dadikovi.web.rest;

import io.github.dadikovi.domain.Stat;
import io.github.dadikovi.domain.enumeration.StatType;
import io.github.dadikovi.stat.StatCalculator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public Stat getStat(@ApiParam(
        name = "statType",
        type = "StatType",
        value = "The type of the statistics"
    ) @Valid StatType statType,
        @ApiParam(
            name = "statObjectName",
            type = "String",
            value = "The object the stat should be returned to"
        ) @Valid String statObjectName) {
        StatCalculator calculator = (StatCalculator) beanFactory.getBean(statType.getBeanType());
        String statValue = calculator.getStatForObject(statObjectName);
        return statValue == null ? null : new Stat()
            .statValue(statValue)
            .statType(statType)
            .objectType(calculator.getStatObject())
            .objectName(statObjectName);
    }
}
