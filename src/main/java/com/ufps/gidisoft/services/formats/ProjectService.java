package com.ufps.gidisoft.services.formats;

import com.ufps.gidisoft.entities.formats.Project;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.enums.projects.ProjectStatusEnum;
import com.ufps.gidisoft.enums.roles.RolesEnum;
import com.ufps.gidisoft.repositories.formats.ProyectRepository;
import com.ufps.gidisoft.requests.formats.ProjectRequest;
import com.ufps.gidisoft.responses.format.ProjectDto;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    /*
     * Repositories
     */
    private final ProyectRepository proyectRepository;

    /*
     * Services
     */
    private final FormatServiceSec formatServiceSec;
    private final ProjectStatusService projectStatusService;
    private final UserService userService;

    @Transactional
    public Project createProject(ProjectRequest projectRequest, User user) {
        return this.proyectRepository.save(getNewProject(projectRequest, projectRequest.getFormatId(), user.getId()));
    }

    private Project getNewProject(ProjectRequest projectRequest, Long formatId, Long userId) {
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setActivities(projectRequest.getActivities());
        project.setStartDate(projectRequest.getStartDate());
        project.setEndDate(projectRequest.getEndDate());
        project.setCompliancePercentage(projectRequest.getCompliancePercentage());
        project.setFormat(this.formatServiceSec.findByIdToRelations(formatId));
        project.setStatus(this.projectStatusService.findById(ProjectStatusEnum.DRAFT.getId()));
        project.setCreatedBy(this.userService.getUserById(userId));

        return project;
    }

    public List<ProjectDto> findByFormatId(Long formatId, User user) {
        List<ProjectDto> projectDtos = new ArrayList<>();
        List<Project> projects = this.proyectRepository.findByFormatIdAndCreatedBy(formatId, user);
        List<Project> otherProjects = this.proyectRepository.findByFormatId(formatId).stream()
                .filter(project -> !project.getCreatedBy().equals(user))
                .filter(project -> project.getStatus().getId().equals(ProjectStatusEnum.PUBLICATED.getId())).toList();
        projectDtos.addAll(projects.stream().map(ProjectDto::new).toList());
        projectDtos.addAll(otherProjects.stream().map(ProjectDto::new).toList());
        return projectDtos;

    }

    public Project publishProject(Long id){
        Project project = this.proyectRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException(ExceptionCodeEnum.PROJ01.getMessage()));
        project.setStatus(this.projectStatusService.findById(ProjectStatusEnum.PUBLICATED.getId()));
        return this.proyectRepository.save(project);
    }
}
