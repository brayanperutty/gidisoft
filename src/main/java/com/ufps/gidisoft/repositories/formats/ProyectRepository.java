package com.ufps.gidisoft.repositories.formats;

import com.ufps.gidisoft.entities.formats.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectRepository extends JpaRepository<Project, Long> {
    List<Project> findByFormatId(Long formatId);
}
