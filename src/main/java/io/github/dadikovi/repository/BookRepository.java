package io.github.dadikovi.repository;

import io.github.dadikovi.domain.AggregationResult;
import io.github.dadikovi.domain.Book;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Book entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select new io.github.dadikovi.domain.AggregationResult(b.author, cast(sum(b.count) as string)) "
        + "from Book b "
        + "group by b.author")
    List<AggregationResult> getCountByAuthors();
}
