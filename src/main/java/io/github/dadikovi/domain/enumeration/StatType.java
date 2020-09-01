package io.github.dadikovi.domain.enumeration;

import io.github.dadikovi.stat.CountByAuthorCalculator;

public enum StatType {
    COUNT_BY_AUTHOR(CountByAuthorCalculator.class);

    private Class beanType;

    public Class getBeanType() {
        return this.beanType;
    }

    StatType( Class beanType ) {
        this.beanType = beanType;
    }
}
