package com.ufps.gidisoft.services;

import com.ufps.gidisoft.entities.format.Format;
import com.ufps.gidisoft.repositories.format.FormatRepository;
import com.ufps.gidisoft.requests.formats.FormatRequest;
import com.ufps.gidisoft.responses.format.FormatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormatService {

    /*
     * Repositories
     */
    private final FormatRepository formatRepository;

    /*
     * Services
     */
    private final UserService userService;
    private final AcademicPeriodsService academicPeriodsService;

    public List<FormatDto> findAllFormats() {
        return formatRepository.findAll().stream().map(FormatDto::new).toList();
    }

    public void createFormat(FormatRequest formatRequest) {
        Format format = new Format();
        format.setName(formatRequest.getName());
        format.setCode(formatRequest.getCode());
        format.setDirector(userService.getUserById(formatRequest.getDirectorId()));
        format.setAcademicPeriod(academicPeriodsService.getAcademicPeriodById(formatRequest.getAcademicPeriod()));
        format.setSectionsValues(formatRequest.getSectionsValues());
        this.formatRepository.save(format);
    }
}
