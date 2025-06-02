package com.ufps.gidisoft.repositories.formats.others;

import com.ufps.gidisoft.entities.formats.others.OtherActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtherActivityRepository extends JpaRepository<OtherActivity, Long> {
    List<OtherActivity> findByFormatId(Long formatId);
}
