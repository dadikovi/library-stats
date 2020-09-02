package io.github.dadikovi.stat;

import io.github.dadikovi.domain.Book;
import io.github.dadikovi.domain.Stat;
import io.github.dadikovi.domain.enumeration.StatType;
import io.github.dadikovi.repository.BookRepository;
import io.github.dadikovi.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LatestBookCalculator extends AbstractSimpleCachedQueryGlobalStatCalculator {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StatRepository statRepository;

    @Override
    public StatType getStatType() {
        return StatType.LATEST_BOOK;
    }

    @Override
    public void calculateAndSaveStat() {
        Book latest = bookRepository.findTopByOrderByPublishYearDesc();
        statRepository.save(new Stat()
            .objectType(this.getStatObject())
            .objectName(this.getStatObject().name())
            .statType(this.getStatType())
            .statValue(latest.getAuthor() + ": " + latest.getTitle()));
    }
}
