package com.ufps.gidisoft.repositories.formats;

import com.ufps.gidisoft.entities.formats.projects.Project;
import com.ufps.gidisoft.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByFormatId(Long formatId);

    List<Project> findByFormatIdAndCreatedBy(Long formatId, User createdBy);

    boolean existsProjectByIdAndCreatedBy(Long id, User createdBy);
}
