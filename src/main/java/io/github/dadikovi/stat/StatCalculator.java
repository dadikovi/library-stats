package io.github.dadikovi.stat;

import io.github.dadikovi.domain.StatParam;
import io.github.dadikovi.domain.enumeration.StatObject;
import io.github.dadikovi.domain.enumeration.StatType;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface StatCalculator {
    StatObject getStatObject();
    StatType getStatType();
    void calculateAndSaveStat();
    String getStatForObject( String objectId, Map<String, String> params );
}
