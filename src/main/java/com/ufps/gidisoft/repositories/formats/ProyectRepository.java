package com.ufps.gidisoft.repositories.formats;

import com.ufps.gidisoft.entities.formats.Proyect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectRepository extends JpaRepository<Proyect, Long> {
}
