package io.github.dadikovi.repository;

import io.github.dadikovi.domain.Stat;

import io.github.dadikovi.domain.enumeration.StatType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Stat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {
    Stat findByStatTypeAndObjectName(StatType statType, String objectName);
}
