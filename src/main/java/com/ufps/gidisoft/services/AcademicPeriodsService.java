package com.ufps.gidisoft.services;

import com.ufps.gidisoft.entities.AcademicPeriods;
import com.ufps.gidisoft.repositories.AcademicPeriodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcademicPeriodsService {

    private final AcademicPeriodsRepository academicPeriodsRepository;

    public void createAcademicPeriods(AcademicPeriods academicPeriods) {
        academicPeriodsRepository.save(academicPeriods);
    }

    public Long countAcademicPeriods() {
        return academicPeriodsRepository.count();
    }
}
