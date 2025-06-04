package com.ufps.gidisoft.repositories.formats.products;

import com.ufps.gidisoft.entities.formats.products.Product;
import com.ufps.gidisoft.entities.formats.products.ProductUser;
import com.ufps.gidisoft.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductUserRepository extends JpaRepository<ProductUser, Long> {
    void deleteByProductId(Long productId);

    boolean existsByProductAndUser(Product product, User user);

    List<ProductUser> findByProductId(Long productId);
}
