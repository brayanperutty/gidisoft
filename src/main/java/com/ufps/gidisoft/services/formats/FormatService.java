package com.ufps.gidisoft.services.formats;

import com.ufps.gidisoft.entities.formats.Format;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.repositories.formats.FormatRepository;
import com.ufps.gidisoft.requests.formats.FormatRequest;
import com.ufps.gidisoft.responses.format.FormatDto;
import com.ufps.gidisoft.services.academic_periods.AcademicPeriodsService;
import com.ufps.gidisoft.services.users.UserService;
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

    public Format findFormatById(Long id) {
        return formatRepository.findById(id).orElseThrow(() -> new NotFoundException(ExceptionCodeEnum.FORMAT01.getMessage()));
    }

    public List<FormatDto> findAllFormats() {
        return formatRepository.findAll().stream().map(FormatDto::new).toList();
    }

    public void createFormat(FormatRequest formatRequest) {
        Format format = new Format(formatRequest, this.userService.getUserById(formatRequest.getDirectorId()),
                this.academicPeriodsService.getAcademicPeriodById(formatRequest.getAcademicPeriod()));
        this.formatRepository.save(format);
    }
}
