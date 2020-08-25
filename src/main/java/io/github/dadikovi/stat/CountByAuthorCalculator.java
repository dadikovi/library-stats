package io.github.dadikovi.stat;

import io.github.dadikovi.domain.Stat;
import io.github.dadikovi.domain.enumeration.StatObject;
import io.github.dadikovi.repository.BookRepository;
import io.github.dadikovi.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountByAuthorCalculator implements StatCalculator {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StatRepository statRepository;

    @Override
    public StatObject getStatObject() {
        return StatObject.AUTHOR;
    }

    @Override
    public String getStatType() {
        return "Count by author";
    }

    @Override
    public void calculateAndSaveStat() {
        bookRepository.getCountByAuthors()
            .stream()
            .map(r -> new Stat()
                .objectType(this.getStatObject())
                .objectName(r.getKey())
                .statType(this.getStatType())
                .statValue(r.getValue()))
            .forEach(statRepository::save);
    }
}
