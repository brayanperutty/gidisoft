package com.ufps.gidisoft.repositories.formats;

import com.ufps.gidisoft.entities.formats.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectRepository extends JpaRepository<Project, Long> {
}
