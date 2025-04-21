package com.ufps.gidisoft.repositories.formats;

import com.ufps.gidisoft.entities.formats.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormatRepository extends JpaRepository<Format, Long> {
}
