package io.github.dadikovi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "countByMaxYearAndAuthor")
public class CountByMaxYearAndAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "maxYear")
    private Long maxYear;

    @Column(name = "author")
    private String author;

    @Column(name = "count")
    private Long count;

    public CountByMaxYearAndAuthor( Long maxYear, String author, Long count ) {
        this.maxYear = maxYear;
        this.author = author;
        this.count = count;
    }

    public Long getMaxYear() {
        return maxYear;
    }

    public String getAuthor() {
        return author;
    }

    public Long getCount() {
        return count;
    }
}
