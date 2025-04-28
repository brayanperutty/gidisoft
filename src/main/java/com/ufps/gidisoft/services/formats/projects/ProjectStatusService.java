package com.ufps.gidisoft.services.formats.projects;

import com.ufps.gidisoft.entities.formats.projects.ProjectStatus;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.repositories.formats.ProyectStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectStatusService {

    /*
     * Repositories
     */
    private final ProyectStatusRepository proyectStatusRepository;

    public void createProyectStatus(String status) {
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setName(status);
        this.proyectStatusRepository.save(projectStatus);
    }

    public boolean existsProyectStatus(String status) {
        return proyectStatusRepository.existsByName(status);
    }

    public ProjectStatus findById(Long id) {
        return this.proyectStatusRepository.findById(id).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.PROJSTS01.getMessage()));
    }
}
