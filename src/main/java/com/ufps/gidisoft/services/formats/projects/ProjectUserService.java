package com.ufps.gidisoft.services.formats.projects;

import com.ufps.gidisoft.entities.formats.projects.Project;
import com.ufps.gidisoft.entities.formats.projects.ProjectUser;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.repositories.formats.ProjectUserRepository;
import com.ufps.gidisoft.responses.format.ProjectDto;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectUserService {

    /*
     * Repositories
     */
    private final ProjectUserRepository projectUserRepository;

    /*
     * Services
     */
    private final UserService userService;

    @Transactional
    public void createProjectUser(Project project, User user) {
        this.projectUserRepository.save(new ProjectUser(project, user));
    }

    public List<ProjectDto> findAllProjectsByUserId(User user) {
        List<ProjectDto> projects = new java.util.ArrayList<>();
        this.projectUserRepository.findByUser(user).forEach(projectUser ->
                projects.add(new ProjectDto(projectUser.getProject(),
                        this.findUsersByProject(projectUser.getProject().getId())))
        );
        return projects;
    }

    public List<String> findUsersByProject(Long projectId){
        List<String> users = new java.util.ArrayList<>();
        this.projectUserRepository.findByProjectId(projectId).forEach(projectUser ->
                users.add(projectUser.getUser().getName())
        );
        return users;
    }

    public void deleteByProjectId(Long projectId){
        this.projectUserRepository.deleteAllByProjectId(projectId);
    }

    public List<Long> getProjectIdsUserCanEdit(User user) {
        List<Long> projectIds = new java.util.ArrayList<>();
        this.projectUserRepository.findByUser(user).forEach(projectUser ->
                projectIds.add(projectUser.getProject().getId())
        );
        return projectIds;
    }

}
