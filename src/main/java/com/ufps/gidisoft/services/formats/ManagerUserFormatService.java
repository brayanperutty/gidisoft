package com.ufps.gidisoft.services.formats;

import com.ufps.gidisoft.repositories.formats.ManagerUserFormatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerUserFormatService {

    /*
     * Repositories
     */
    private final ManagerUserFormatRepository managerUserFormatRepository;

    public void createManagerUserFormat(String createdBy, String reviewBy, String approvedBy, Long formatId) {

    }
}
