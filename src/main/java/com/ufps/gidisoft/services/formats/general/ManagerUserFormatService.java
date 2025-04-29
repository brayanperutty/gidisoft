package com.ufps.gidisoft.services.formats.general;

import com.ufps.gidisoft.entities.formats.general.Format;
import com.ufps.gidisoft.entities.formats.general.ManagerUserFormat;
import com.ufps.gidisoft.repositories.formats.general.ManagerUserFormatRepository;
import com.ufps.gidisoft.requests.formats.ManagerUserFormatRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerUserFormatService {

    /*
     * Repositories
     */
    private final ManagerUserFormatRepository managerUserFormatRepository;

    public void createManagerUserFormat(ManagerUserFormatRequest managerUserFormatRequest, Format format) {
        ManagerUserFormat managerUserFormat = new ManagerUserFormat();
        managerUserFormat.setCreatedBy(managerUserFormatRequest.getCreatedBy());
        managerUserFormat.setReviewBy(managerUserFormatRequest.getReviewBy());
        managerUserFormat.setApproveBy(managerUserFormatRequest.getApproveBy());
        managerUserFormat.setFormat(format);
        this.managerUserFormatRepository.save(managerUserFormat);
    }

    public ManagerUserFormat findByFormatId(Long formatId) {
        return this.managerUserFormatRepository.findByFormatId(formatId);
    }

    public boolean existsManagerUserFormatByFormatId(Long formatId) {
        return this.managerUserFormatRepository.existsByFormatId(formatId);
    }

    @Transactional
    public void deleteByFormatId(Long formatId){
        this.managerUserFormatRepository.deleteByFormatId(formatId);
    }
}
