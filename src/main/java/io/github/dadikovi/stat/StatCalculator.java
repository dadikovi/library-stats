package io.github.dadikovi.stat;

import io.github.dadikovi.domain.enumeration.StatObject;
import io.github.dadikovi.domain.enumeration.StatType;

public interface StatCalculator {
    StatObject getStatObject();
    StatType getStatType();
    void calculateAndSaveStat();
    String getStatForObject(String objectId);
}
