package com.ufps.gidisoft.repositories.groups;

import com.ufps.gidisoft.entities.groups.InvestigationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestigationGroupRepository extends JpaRepository<InvestigationGroup, Long> {
}
