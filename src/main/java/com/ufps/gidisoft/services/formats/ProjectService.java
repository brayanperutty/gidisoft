package com.ufps.gidisoft.services.formats;

import com.ufps.gidisoft.entities.formats.Project;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.enums.projects.ProjectStatusEnum;
import com.ufps.gidisoft.repositories.formats.ProyectRepository;
import com.ufps.gidisoft.requests.formats.ProjectRequest;
import com.ufps.gidisoft.responses.format.ProjectDto;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void createDraftProyect(List<ProjectRequest> projectsRequest, Long formatId, Long userId) {
        projectsRequest.forEach(projectRequest ->
            this.proyectRepository.save(getNewProject(projectRequest, formatId, userId))
        );
    }

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

    public List<ProjectDto> findByFormatId(Long formatId) {
        return this.proyectRepository.findByFormatId(formatId).stream().map(ProjectDto::new).toList();
    }
}
