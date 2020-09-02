package io.github.dadikovi.stat;

import io.github.dadikovi.domain.Stat;
import io.github.dadikovi.domain.enumeration.StatObject;
import io.github.dadikovi.domain.enumeration.StatType;
import io.github.dadikovi.repository.BookRepository;
import io.github.dadikovi.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvgAgeCalculator extends AbstractSimpleCachedQueryGlobalStatCalculator {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StatRepository statRepository;

    @Override
    public StatType getStatType() {
        return StatType.AVG_AGE;
    }

    @Override
    public void calculateAndSaveStat() {
        statRepository.save(new Stat()
            .objectType(this.getStatObject())
            .objectName(this.getStatObject().name())
            .statType(this.getStatType())
            .statValue(bookRepository.getAvgAge().toString()));
    }
}
