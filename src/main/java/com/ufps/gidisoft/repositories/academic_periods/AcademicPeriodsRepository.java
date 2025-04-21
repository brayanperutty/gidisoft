package com.ufps.gidisoft.repositories.academic_periods;

import com.ufps.gidisoft.entities.academic_periods.AcademicPeriods;
import com.ufps.gidisoft.projections.academic_periods.AcademicPeriodValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicPeriodsRepository extends JpaRepository<AcademicPeriods, Long> {
}
