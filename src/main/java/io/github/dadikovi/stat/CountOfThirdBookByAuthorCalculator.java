package io.github.dadikovi.stat;

import io.github.dadikovi.domain.AggregationResult;
import io.github.dadikovi.domain.Book;
import io.github.dadikovi.domain.Stat;
import io.github.dadikovi.domain.enumeration.StatObject;
import io.github.dadikovi.domain.enumeration.StatType;
import io.github.dadikovi.repository.BookRepository;
import io.github.dadikovi.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountOfThirdBookByAuthorCalculator extends AbstractSimpleCachedQueryStatCalculator {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StatRepository statRepository;

    @Override
    public StatObject getStatObject() {
        return StatObject.AUTHOR;
    }

    @Override
    public StatType getStatType() {
        return StatType.COUNT_OF_THIRD_BOOK;
    }

    @Override
    public void calculateAndSaveStat() {
        List<Book> result = bookRepository.getCountOfThirdBookByAuthor();
        result
            .stream()
            .map(r -> new Stat()
                .objectType(this.getStatObject())
                .objectName(r.getAuthor())
                .statType(this.getStatType())
                .statValue(r.getCount().toString()))
            .forEach(statRepository::save);
    }
}
