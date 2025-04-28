package com.ufps.gidisoft.repositories.formats;

import com.ufps.gidisoft.entities.formats.general.ManagerUserFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerUserFormatRepository extends JpaRepository<ManagerUserFormat, Long> {
    ManagerUserFormat findByFormatId(Long formatId);

    boolean existsByFormatId(Long formatId);

    void deleteByFormatId(Long formatId);
}
