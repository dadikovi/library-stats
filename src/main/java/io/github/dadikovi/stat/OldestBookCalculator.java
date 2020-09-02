package io.github.dadikovi.stat;

import io.github.dadikovi.domain.Book;
import io.github.dadikovi.domain.Stat;
import io.github.dadikovi.domain.enumeration.StatType;
import io.github.dadikovi.repository.BookRepository;
import io.github.dadikovi.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OldestBookCalculator extends AbstractSimpleCachedQueryGlobalStatCalculator {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StatRepository statRepository;

    @Override
    public StatType getStatType() {
        return StatType.OLDEST_BOOK;
    }

    @Override
    public void calculateAndSaveStat() {
        Book oldest = bookRepository.findTopByOrderByPublishYear();
        statRepository.save(new Stat()
            .objectType(this.getStatObject())
            .objectName(this.getStatObject().name())
            .statType(this.getStatType())
            .statValue(oldest.getAuthor() + ": " + oldest.getTitle()));
    }
}
