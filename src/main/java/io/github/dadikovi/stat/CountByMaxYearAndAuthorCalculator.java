package io.github.dadikovi.stat;

import io.github.dadikovi.domain.CountByMaxYearAndAuthor;
import io.github.dadikovi.domain.enumeration.StatObject;
import io.github.dadikovi.domain.enumeration.StatType;
import io.github.dadikovi.repository.BookRepository;
import io.github.dadikovi.repository.CountByMaxYearAndAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CountByMaxYearAndAuthorCalculator implements StatCalculator {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CountByMaxYearAndAuthorRepository repository;

    @Override
    public StatObject getStatObject() {
        return StatObject.AUTHOR;
    }

    @Override
    public StatType getStatType() {
        return StatType.COUNT_BY_MAX_YEAR_AND_AUTHOR;
    }

    @Override
    public void calculateAndSaveStat() {
        bookRepository.getCountByMaxYearAndAuthor().forEach(repository::save);
    }

    @Override
    public String getStatForObject( String objectId, Map<String, String> params ) {
        if (params.get("maxYear") == null) {
            throw new IllegalArgumentException("maxYear must be provided for this stat.");
        }
        CountByMaxYearAndAuthor count = repository.findByMaxYearAndAuthor(Long.valueOf(params.get("maxYear")), objectId);
        return count == null ? null : count.getCount().toString();
    }
}
