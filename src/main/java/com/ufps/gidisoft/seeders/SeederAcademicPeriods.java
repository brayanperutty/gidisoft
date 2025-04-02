package com.ufps.gidisoft.seeders;

import com.ufps.gidisoft.entities.AcademicPeriods;
import com.ufps.gidisoft.services.AcademicPeriodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SeederAcademicPeriods {

    private final AcademicPeriodsService academicPeriodsService;

    public void seed() {
        getAcademicPeriods(academicPeriodsService);
    }

    public static void getAcademicPeriods(AcademicPeriodsService academicPeriodsService) {
        if (academicPeriodsService.countAcademicPeriods() == 0){
            AcademicPeriods academicPeriods20251 = new AcademicPeriods(null, 2025L, "I");
            AcademicPeriods academicPeriods20252 = new AcademicPeriods(null, 2025L, "II");
            AcademicPeriods academicPeriods20261 = new AcademicPeriods(null, 2026L, "I");
            AcademicPeriods academicPeriods20262 = new AcademicPeriods(null, 2026L, "II");
            AcademicPeriods academicPeriods20271 = new AcademicPeriods(null, 2027L, "I");
            AcademicPeriods academicPeriods20272 = new AcademicPeriods(null, 2027L, "II");
            AcademicPeriods academicPeriods20281 = new AcademicPeriods(null, 2028L, "I");
            AcademicPeriods academicPeriods20282 = new AcademicPeriods(null, 2028L, "II");
            AcademicPeriods academicPeriods20291 = new AcademicPeriods(null, 2029L, "I");
            AcademicPeriods academicPeriods20292 = new AcademicPeriods(null, 2029L, "II");

            academicPeriodsService.createAcademicPeriods(academicPeriods20251);
            academicPeriodsService.createAcademicPeriods(academicPeriods20252);
            academicPeriodsService.createAcademicPeriods(academicPeriods20261);
            academicPeriodsService.createAcademicPeriods(academicPeriods20262);
            academicPeriodsService.createAcademicPeriods(academicPeriods20271);
            academicPeriodsService.createAcademicPeriods(academicPeriods20272);
            academicPeriodsService.createAcademicPeriods(academicPeriods20281);
            academicPeriodsService.createAcademicPeriods(academicPeriods20282);
            academicPeriodsService.createAcademicPeriods(academicPeriods20291);
            academicPeriodsService.createAcademicPeriods(academicPeriods20292);
        }
    }
}
