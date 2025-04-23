package com.ufps.gidisoft.services.formats;

import com.ufps.gidisoft.entities.formats.Format;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.enums.projects.ProjectStatusEnum;
import com.ufps.gidisoft.enums.roles.RolesEnum;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.repositories.formats.FormatRepository;
import com.ufps.gidisoft.requests.formats.FormatRequest;
import com.ufps.gidisoft.responses.format.FormatDto;
import com.ufps.gidisoft.responses.format.FormatListDto;
import com.ufps.gidisoft.services.academic_periods.AcademicPeriodsService;
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
    private final FormatUserService formatUserService;

    public FormatDto findFormatById(Long formatId) {
        return new FormatDto(formatRepository.findById(formatId).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.FORMAT01.getMessage())),
                managerUserFormatService.findByFormatId(formatId));
    }

    public List<FormatListDto> findAllFormatsByPermission(User user) {
        if(user.getRole().getType().equals(RolesEnum.ADMIN.getRole())){
            return this.formatRepository.findAll().stream().map(FormatListDto::new).toList();
        }else {
            return this.formatUserService.findAllFormatsByPermissionAndStatus(user, ProjectStatusEnum.PUBLICATED.getId());
        }
    }

    @Transactional
    public void createFormat(FormatRequest formatRequest) {
        this.validateIfExistsFormatByAcademicPeriod(formatRequest.getAcademicPeriod());
        Format format = new Format();
        format.setCode(formatRequest.getCode());
        format.setVersion(formatRequest.getVersion());
        format.setDate(formatRequest.getDate());
        format.setName("INFORME DE GESTIÓN DE LOS GRUPOS DE INVESTIGACIÓN");
        format.setGroup(formatRequest.getGroup());
        format.setUnity(formatRequest.getUnity());
        format.setDirector(this.userService.getUserById(formatRequest.getDirectorId()));
        format.setDepartment(formatRequest.getDepartment());
        format.setFaculty(formatRequest.getFaculty());
        format.setAcademicPeriod(this.academicPeriodsService.getAcademicPeriodById(formatRequest.getAcademicPeriod()));
        format.setStatus(this.projectStatusService.findById(ProjectStatusEnum.DRAFT.getId()));
        this.formatRepository.save(format);

        this.formatUserService.createFormatUser(format, this.userService.getUserById(formatRequest.getDirectorId()));

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
            this.formatUserService.deleteByFormatId(formatId);
            this.formatRepository.deleteById(formatId);
        }else throw new IllegalArgumentException(ExceptionCodeEnum.FORMAT01.getMessage());
    }

    public void createRelationsWithUsers(List<Long> users, Long formatId){
        users.forEach(user -> {
            Format format = this.formatRepository.findById(formatId).orElseThrow(()
                    -> new NotFoundException(ExceptionCodeEnum.FORMAT01.getMessage()));
            this.formatUserService.createFormatUser(format, this.userService.getUserById(user));
        });
    }
}
