package com.ufps.gidisoft.services;

import com.ufps.gidisoft.entities.AcademicPeriods;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.repositories.AcademicPeriodsRepository;
import com.ufps.gidisoft.responses.academic_periods.AcademicPeriodsSelect;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public AcademicPeriods getAcademicPeriodById(Long id) {
        return academicPeriodsRepository.findById(id).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.ACAPER01.getMessage()));
    }

    public List<AcademicPeriodsSelect> getAllYearsAcademicPeriods() {
        return this.academicPeriodsRepository.getAllYears().stream().map(academicPeriod -> {
            AcademicPeriodsSelect years = new AcademicPeriodsSelect();
            years.setValue(academicPeriod.getValue());
            return years;
        }).toList();
    }

    public List<AcademicPeriodsSelect> getAllPeriodssAcademicPeriods() {
        return this.academicPeriodsRepository.getAllPeriods().stream().map(academicPeriod -> {
            AcademicPeriodsSelect years = new AcademicPeriodsSelect();
            years.setValue(academicPeriod.getValue());
            return years;
        }).toList();
    }

}
