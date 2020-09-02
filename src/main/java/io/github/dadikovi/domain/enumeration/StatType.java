package io.github.dadikovi.domain.enumeration;

import io.github.dadikovi.stat.CountByAuthorCalculator;
import io.github.dadikovi.stat.CountByMaxYearAndAuthorCalculator;

public enum StatType {
    COUNT_BY_AUTHOR(CountByAuthorCalculator.class),
    COUNT_BY_MAX_YEAR_AND_AUTHOR( CountByMaxYearAndAuthorCalculator.class);

    private Class beanType;

    public Class getBeanType() {
        return this.beanType;
    }

    StatType( Class beanType ) {
        this.beanType = beanType;
    }
}
