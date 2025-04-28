package com.ufps.gidisoft.services.formats.general;

import com.ufps.gidisoft.entities.formats.general.Format;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.repositories.formats.FormatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormatServiceSec {

    /*
     * Repositories
     */
    private final FormatRepository formatRepository;

    public Format findByIdToRelations(Long formatId){
        return this.formatRepository.findById(formatId).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.FORMAT01.getMessage()));
    }
}
