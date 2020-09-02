package io.github.dadikovi.stat;

import io.github.dadikovi.domain.enumeration.StatObject;
import io.github.dadikovi.domain.enumeration.StatType;

import java.util.Map;

public abstract class AbstractSimpleCachedQueryGlobalStatCalculator extends AbstractSimpleCachedQueryStatCalculator {

    @Override
    public StatObject getStatObject() {
        return StatObject.GLOBAL;
    }

    @Override
    public abstract StatType getStatType();

    @Override public abstract void calculateAndSaveStat();

    @Override
    public String getStatForObject( String objectId, Map<String, String> params ) {
        return super.getStatForObject(StatObject.GLOBAL.name(), params);
    }
}
