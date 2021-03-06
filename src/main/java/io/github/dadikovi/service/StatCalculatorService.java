package io.github.dadikovi.service;

import io.github.dadikovi.repository.StatRepository;
import io.github.dadikovi.stat.StatCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatCalculatorService {

    @Autowired
    private List<StatCalculator> calculators;

    @Autowired
    private StatRepository repository;

    public void calculateAllStats() {
        repository.deleteAll();
        calculators.forEach(StatCalculator::calculateAndSaveStat);
    }
}
