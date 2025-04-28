package com.ufps.gidisoft.repositories.faculties;

import com.ufps.gidisoft.entities.faculties.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
