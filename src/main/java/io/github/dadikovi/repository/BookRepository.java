package io.github.dadikovi.repository;

import io.github.dadikovi.domain.AggregationResult;
import io.github.dadikovi.domain.Book;

import io.github.dadikovi.domain.CountByMaxYearAndAuthor;
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

    @Query("select new io.github.dadikovi.domain.AggregationResult(b.publisher, cast(sum(b.count) as string)) "
        + "from Book b "
        + "group by b.publisher")
    List<AggregationResult> getCountByPublishers();

    @Query("select avg(year(current_date()) - b.publishYear) "
        + "from Book b ")
    Integer getAvgAge();

    Book findTopByOrderByPublishYear();
    Book findTopByOrderByPublishYearDesc();

    @Query("select new io.github.dadikovi.domain.CountByMaxYearAndAuthor(b.publishYear, b.author, ( "
            + "select sum(s.count) from Book s where s.publishYear <= b.publishYear "
        + ")) "
        + "from Book b "
        + "group by b.publishYear, b.author")
    List<CountByMaxYearAndAuthor> getCountByMaxYearAndAuthor();
}
