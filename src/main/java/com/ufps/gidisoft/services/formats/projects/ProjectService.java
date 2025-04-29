package com.ufps.gidisoft.services.formats.projects;

import com.ufps.gidisoft.entities.formats.projects.Project;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.repositories.formats.projects.ProjectRepository;
import com.ufps.gidisoft.requests.formats.ProjectRequest;
import com.ufps.gidisoft.responses.format.ProjectDto;
import com.ufps.gidisoft.services.formats.general.FormatServiceSec;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    /*
     * Repositories
     */
    private final ProjectRepository projectRepository;

    /*
     * Services
     */
    private final FormatServiceSec formatServiceSec;
    private final UserService userService;
    private final ProjectUserService projectUserService;

    @Transactional
    public void createProject(ProjectRequest projectRequest, User user) {
        Project project = this.projectRepository.save(getNewProject(projectRequest, projectRequest.getFormatId(), user.getId()));
        this.projectUserService.createProjectUser(project, user);
    }

    private Project getNewProject(ProjectRequest projectRequest, Long formatId, Long userId) {
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setActivities(projectRequest.getActivities());
        project.setCompliancePercentage(projectRequest.getCompliancePercentage());
        project.setFormat(this.formatServiceSec.findByIdToRelations(formatId));
        project.setCreatedBy(this.userService.getUserById(userId));
        return project;
    }

    public List<ProjectDto> findByFormatId(Long formatId) {
        List<ProjectDto> projects = new ArrayList<>();
        for (Project project : this.projectRepository.findByFormatId(formatId)) {
            projects.add(new ProjectDto(project, this.projectUserService.findUsersByProject(project.getId())));
        }
        projects.sort(Comparator.comparing(ProjectDto::getId));
        return projects;
    }

    @Transactional
    public void deleteById(Long projectId) {
        this.projectUserService.deleteByProjectId(projectId);
        this.projectRepository.deleteById(projectId);
    }

    public boolean validateProjectWithUser(Long projectId, User user) {
        return this.projectUserService.validateExistProjecAndUser(this.projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionCodeEnum.PROJ01.getMessage())), user);
    }

    public void createRelationsWithUsers(List<Long> users, Long projectId) {
        users.forEach(user -> {
            Project project = this.projectRepository.findById(projectId).orElseThrow(()
                    -> new IllegalArgumentException(ExceptionCodeEnum.PROJ01.getMessage()));
            this.projectUserService.createProjectUser(project, this.userService.getUserById(user));
        });
    }
}
