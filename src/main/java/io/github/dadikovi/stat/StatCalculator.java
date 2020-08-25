package io.github.dadikovi.stat;

import io.github.dadikovi.domain.enumeration.StatObject;

public interface StatCalculator {
    StatObject getStatObject();
    String getStatType();
    void calculateAndSaveStat();
}
