package com.ufps.gidisoft.services.academic_periods;

import com.ufps.gidisoft.entities.academic_periods.AcademicPeriods;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.repositories.academic_periods.AcademicPeriodsRepository;
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

    public List<AcademicPeriodsSelect> findAllAcademicPeriods() {
        return this.academicPeriodsRepository.findAll().stream().map(academicPeriod -> {
            AcademicPeriodsSelect years = new AcademicPeriodsSelect();
            years.setId(academicPeriod.getId());
            years.setValue(academicPeriod.getYear() + " - " + academicPeriod.getPeriod());
            return years;
        }).toList();
    }



}
