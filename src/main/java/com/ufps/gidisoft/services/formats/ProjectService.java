package com.ufps.gidisoft.services.formats;

import com.ufps.gidisoft.entities.formats.Project;
import com.ufps.gidisoft.enums.projects.ProjectStatusEnum;
import com.ufps.gidisoft.repositories.formats.ProyectRepository;
import com.ufps.gidisoft.requests.formats.ProyectRequest;
import com.ufps.gidisoft.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final FormatService formatService;
    private final ProjectStatusService projectStatusService;
    private final UserService userService;

    public Project createDraftProyect(ProyectRequest proyectRequest, Long formatId, Long userId) {
        Project project = new Project();
        project.setName(proyectRequest.getName());
        project.setActivities(proyectRequest.getActivities());
        project.setStartDate(proyectRequest.getStartDate());
        project.setEndDate(proyectRequest.getEndDate());
        project.setCompliancePercentage(proyectRequest.getCompliancePercentage());
        project.setFormat(this.formatService.findByIdToRelations(formatId));
        project.setStatus(this.projectStatusService.findById(ProjectStatusEnum.DRAFT.getId()));
        project.setCreatedBy(this.userService.getUserById(userId));
        return this.proyectRepository.save(project);
    }
}
