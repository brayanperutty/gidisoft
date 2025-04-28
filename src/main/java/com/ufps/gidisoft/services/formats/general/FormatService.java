package com.ufps.gidisoft.services.formats.general;

import com.ufps.gidisoft.entities.formats.general.Format;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.enums.projects.ProjectStatusEnum;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.repositories.formats.FormatRepository;
import com.ufps.gidisoft.requests.formats.FormatRequest;
import com.ufps.gidisoft.responses.format.FormatDto;
import com.ufps.gidisoft.responses.format.FormatListDto;
import com.ufps.gidisoft.services.academic_periods.AcademicPeriodsService;
import com.ufps.gidisoft.services.faculties.FacultyService;
import com.ufps.gidisoft.services.formats.projects.ProjectStatusService;
import com.ufps.gidisoft.services.groups.InvestigationGroupService;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.transaction.Transactional;
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
    private final ProjectStatusService projectStatusService;
    private final ManagerUserFormatService managerUserFormatService;
    private final InvestigationGroupService investigationGroupService;
    private final FacultyService facultyService;

    public FormatDto findFormatById(Long formatId) {
        return new FormatDto(formatRepository.findById(formatId).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.FORMAT01.getMessage())),
                managerUserFormatService.findByFormatId(formatId));
    }

    @Transactional
    public void createFormat(FormatRequest formatRequest) {
        this.validateIfExistsFormatByAcademicPeriod(formatRequest.getAcademicPeriod());
        Format format = new Format();
        format.setCode(formatRequest.getCode());
        format.setVersion(formatRequest.getVersion());
        format.setDate(formatRequest.getDate());
        format.setName("INFORME DE GESTIÓN DE LOS GRUPOS DE INVESTIGACIÓN");
        format.setGroup(this.investigationGroupService.findById(formatRequest.getGroup()));
        format.setUnity(formatRequest.getUnity());
        format.setDirector(this.userService.getUserById(formatRequest.getDirectorId()));
        format.setDepartment(formatRequest.getDepartment());
        format.setFaculty(this.facultyService.findById(formatRequest.getFaculty()));
        format.setAcademicPeriod(this.academicPeriodsService.getAcademicPeriodById(formatRequest.getAcademicPeriod()));
        format.setStatus(this.projectStatusService.findById(ProjectStatusEnum.DRAFT.getId()));
        this.formatRepository.save(format);

        if(formatRequest.getManagerUsers() != null){
            this.managerUserFormatService.createManagerUserFormat(formatRequest.getManagerUsers(), format);
        }
    }

    private void validateIfExistsFormatByAcademicPeriod(Long academicPeriodId){
        if(this.formatRepository.existsByAcademicPeriodId(academicPeriodId)){
            throw new IllegalArgumentException(ExceptionCodeEnum.FORMAT02.getMessage());
        }
    }

    public boolean existsFormatById(Long formatId){
        return this.formatRepository.existsById(formatId);
    }

    @Transactional
    public void deleteById(Long formatId){
        if(this.existsFormatById(formatId)){
            if(this.managerUserFormatService.existsManagerUserFormatByFormatId(formatId)){
                this.managerUserFormatService.deleteByFormatId(formatId);
            }
            this.formatRepository.deleteById(formatId);
        }else throw new IllegalArgumentException(ExceptionCodeEnum.FORMAT01.getMessage());
    }

    public void publishFormat(Long formatId){
        Format format = this.formatRepository.findById(formatId).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.FORMAT01.getMessage()));
        format.setStatus(this.projectStatusService.findById(ProjectStatusEnum.PUBLICATED.getId()));
        this.formatRepository.save(format);
    }

    public List<FormatListDto> findAllFormats(){
        return this.formatRepository.findAll().stream().map(FormatListDto::new).toList();
    }
}
