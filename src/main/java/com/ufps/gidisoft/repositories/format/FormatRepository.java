package com.ufps.gidisoft.repositories.format;

import com.ufps.gidisoft.entities.format.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormatRepository extends JpaRepository<Format, Long> {
}
