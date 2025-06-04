package com.ufps.gidisoft.repositories.formats.general;

import com.ufps.gidisoft.entities.formats.general.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormatRepository extends JpaRepository<Format, Long> {
    boolean existsByAcademicPeriodId(Long academicPeriodId);

    @Query(value = "SELECT f FROM Format f " +
            "WHERE (:directorId IS NULL OR f.director.id = :directorId) " +
            "AND (:academicPeriodId IS NULL OR f.academicPeriod.id = :academicPeriodId) " +
            "AND (:groupId IS NULL OR f.group.id = :groupId) " +
            "AND (:status IS NULL OR f.status.id = :status) " +
            "ORDER BY f.id ASC")
    List<Format> findByFilters(Long directorId,
                               Long academicPeriodId,
                               Long groupId,
                               Long status);
}
