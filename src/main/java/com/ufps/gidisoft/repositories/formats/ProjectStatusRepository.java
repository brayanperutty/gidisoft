package com.ufps.gidisoft.repositories.formats;

import com.ufps.gidisoft.entities.formats.projects.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {

    boolean existsByName(String name);
}
