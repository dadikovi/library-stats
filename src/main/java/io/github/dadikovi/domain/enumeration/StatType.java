package io.github.dadikovi.domain.enumeration;

import io.github.dadikovi.stat.AvgAgeCalculator;
import io.github.dadikovi.stat.CountByAuthorCalculator;
import io.github.dadikovi.stat.CountByMaxYearAndAuthorCalculator;
import io.github.dadikovi.stat.CountByPublisherCalculator;
import io.github.dadikovi.stat.LatestBookCalculator;
import io.github.dadikovi.stat.OldestBookCalculator;

public enum StatType {
    COUNT_BY_AUTHOR(CountByAuthorCalculator.class),
    COUNT_BY_PUBLISHER(CountByPublisherCalculator.class),
    AVG_AGE(AvgAgeCalculator.class),
    OLDEST_BOOK(OldestBookCalculator.class),
    LATEST_BOOK(LatestBookCalculator.class),
    COUNT_BY_MAX_YEAR_AND_AUTHOR( CountByMaxYearAndAuthorCalculator.class);

    private Class beanType;

    public Class getBeanType() {
        return this.beanType;
    }

    StatType( Class beanType ) {
        this.beanType = beanType;
    }
}
