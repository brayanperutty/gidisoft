package com.ufps.gidisoft.services.formats.products;

import com.ufps.gidisoft.entities.formats.products.ProductType;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.repositories.formats.products.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductTypeService {

    /*
     * Repositories
     */
    private final ProductTypeRepository productTypeRepository;

    public ProductType findProductTypeById(Long id) {
        return this.productTypeRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException(ExceptionCodeEnum.PROJ01.getMessage()));
    }
}
