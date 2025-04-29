package com.ufps.gidisoft.services.formats.projects;

import com.ufps.gidisoft.entities.formats.projects.ProjectStatus;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.repositories.formats.projects.ProjectStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectStatusService {

    /*
     * Repositories
     */
    private final ProjectStatusRepository projectStatusRepository;

    public void createProyectStatus(String status) {
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setName(status);
        this.projectStatusRepository.save(projectStatus);
    }

    public boolean existsProyectStatus(String status) {
        return projectStatusRepository.existsByName(status);
    }

    public ProjectStatus findById(Long id) {
        return this.projectStatusRepository.findById(id).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.PROJSTS01.getMessage()));
    }
}
