package io.github.dadikovi.repository;

import io.github.dadikovi.domain.CountByMaxYearAndAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CountByMaxYearAndAuthor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountByMaxYearAndAuthorRepository extends JpaRepository<CountByMaxYearAndAuthor, Long> {
    CountByMaxYearAndAuthor findTopByMaxYearLessThanEqualAndAuthorOrderByMaxYearDesc(Long maxYear, String author);
}
