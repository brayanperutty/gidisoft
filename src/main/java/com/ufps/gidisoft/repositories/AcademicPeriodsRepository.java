package com.ufps.gidisoft.repositories;

import com.ufps.gidisoft.entities.AcademicPeriods;
import com.ufps.gidisoft.projections.academic_periods.AcademicPeriodValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicPeriodsRepository extends JpaRepository<AcademicPeriods, Long> {

    @Query(value = "SELECT DISTINCT(year) as value FROM academic_periods ORDER BY year ASC", nativeQuery = true)
    List<AcademicPeriodValueProjection> getAllYears();

    @Query(value = "SELECT DISTINCT(period) as value FROM academic_periods ORDER BY period ASC", nativeQuery = true)
    List<AcademicPeriodValueProjection> getAllPeriods();
}
