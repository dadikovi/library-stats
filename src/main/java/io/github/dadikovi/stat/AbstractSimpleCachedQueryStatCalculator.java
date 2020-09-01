package io.github.dadikovi.stat;

import io.github.dadikovi.domain.Stat;
import io.github.dadikovi.domain.enumeration.StatObject;
import io.github.dadikovi.domain.enumeration.StatType;
import io.github.dadikovi.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractSimpleCachedQueryStatCalculator implements StatCalculator {

    @Autowired
    private StatRepository statRepository;

    @Override public abstract StatObject getStatObject();

    @Override public abstract StatType getStatType();

    @Override public abstract void calculateAndSaveStat();

    @Override
    public String getStatForObject( String objectId ) {
        Stat stat = statRepository.findByStatTypeAndObjectName(this.getStatType(), objectId);
        return stat == null ? null : stat.getStatValue();
    }
}
