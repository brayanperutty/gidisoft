package com.ufps.gidisoft.repositories.formats.projects;

import com.ufps.gidisoft.entities.formats.projects.Project;
import com.ufps.gidisoft.entities.formats.projects.ProjectUser;
import com.ufps.gidisoft.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {
    List<ProjectUser> findByUser(User user);

    void deleteAllByProjectId(Long projectId);

    List<ProjectUser> findByProjectId(Long projectId);

    boolean existsByProjectAndUser(Project project, User user);
}
