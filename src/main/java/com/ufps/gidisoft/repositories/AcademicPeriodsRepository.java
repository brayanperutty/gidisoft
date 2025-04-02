package com.ufps.gidisoft.repositories;

import com.ufps.gidisoft.entities.AcademicPeriods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicPeriodsRepository extends JpaRepository<AcademicPeriods, Long> {
}
