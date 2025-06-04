package com.ufps.gidisoft.repositories.formats.products;

import com.ufps.gidisoft.entities.formats.products.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}
