package com.ufps.gidisoft.services.formats.projects;

import com.ufps.gidisoft.entities.formats.projects.Project;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.repositories.formats.projects.ProjectRepository;
import com.ufps.gidisoft.requests.formats.ProjectRequest;
import com.ufps.gidisoft.responses.format.ProjectDto;
import com.ufps.gidisoft.services.cloudinary.CloudinaryService;
import com.ufps.gidisoft.services.formats.general.FormatServiceSec;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final CloudinaryService cloudinaryService;

    public Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException(ExceptionCodeEnum.PROJ01.getMessage()));
    }

    @Transactional
    public void createProject(ProjectRequest projectRequest, User user) throws IOException {
        Project project = this.projectRepository.save(getNewProject(projectRequest, projectRequest.getFormatId(), user.getId()));
        this.projectUserService.createProjectUser(project, user);
    }

    @Transactional
    public void updateProject(ProjectRequest projectRequest, User user) throws IOException {
        Project project = this.findById(projectRequest.getId());
        if (this.projectUserService.validateExistProjecAndUser(project, user)) {
            project.setName(projectRequest.getName());
            project.setActivities(projectRequest.getActivities());
            project.setCompliancePercentage(projectRequest.getCompliancePercentage());
            getFilesNameList(projectRequest, project);
            this.projectRepository.save(project);
        } else throw new IllegalArgumentException(ExceptionCodeEnum.PROJ02.getMessage());
    }

    @Transactional
    protected Project getNewProject(ProjectRequest projectRequest, Long formatId, Long userId) throws IOException {
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setActivities(projectRequest.getActivities());
        project.setCompliancePercentage(projectRequest.getCompliancePercentage());
        project.setFormat(this.formatServiceSec.findByIdToRelations(formatId));
        project.setCreatedBy(this.userService.getUserById(userId));
        getFilesNameList(projectRequest, project);
        return project;
    }

    private void getFilesNameList(ProjectRequest projectRequest, Project project) throws IOException {
        if (projectRequest.getFiles() != null && !projectRequest.getFiles().isEmpty()) {
            List<String> files = new ArrayList<>();
            if (project.getFiles() != null && !project.getFiles().isEmpty()) {
                files = project.getFiles();
            }
            for (MultipartFile file : projectRequest.getFiles()) {
                files.add(cloudinaryService.upload(file, "projects"));
            }
            project.setFiles(files);
        }
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
    public void deleteById(Long projectId) throws Exception {
        this.projectUserService.deleteByProjectId(projectId);
        Project project = this.findById(projectId);
        if (project.getFiles() != null && !project.getFiles().isEmpty()) {
            for (String file : project.getFiles()) {
                this.cloudinaryService.getImage(file);
            }
        }
        this.projectRepository.deleteById(projectId);
    }

    @Transactional
    public void deleteEvidence(Long projectId, String url) throws Exception {
        Project project = this.findById(projectId);

        this.cloudinaryService.getImage(url);

        List<String> files = project.getFiles();
        files.removeIf(fileUrl -> fileUrl.trim().equalsIgnoreCase(url.trim()));
        if (files.isEmpty()) project.setFiles(null);
        else project.setFiles(files);

        this.projectRepository.save(project);
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
