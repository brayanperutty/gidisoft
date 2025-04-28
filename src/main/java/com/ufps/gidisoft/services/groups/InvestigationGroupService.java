package com.ufps.gidisoft.services.groups;

import com.ufps.gidisoft.entities.groups.InvestigationGroup;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.repositories.groups.InvestigationGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestigationGroupService {

    /*
     * Repositories
     */
    private final InvestigationGroupRepository investigationGroupRepository;

    public List<InvestigationGroup> findAllInvestigationGroups() {
        return this.investigationGroupRepository.findAll();
    }

    public InvestigationGroup findById(Long id) {
        return this.investigationGroupRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException(ExceptionCodeEnum.GROUP01.getMessage()));
    }
}
