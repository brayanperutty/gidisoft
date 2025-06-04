package com.ufps.gidisoft.services.formats.products;

import com.ufps.gidisoft.entities.formats.products.Product;
import com.ufps.gidisoft.entities.formats.products.ProductUser;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.repositories.formats.products.ProductUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductUserService {


    /*
     * Repositories
     */
    private final ProductUserRepository productUserRepository;

    @Transactional
    public void createProductUser(Product product, User user) {
        this.productUserRepository.save(new ProductUser(product, user));
    }

    @Transactional
    public void deleteByProduct(Long productId) {
        this.productUserRepository.deleteByProductId(productId);
    }

    public boolean validateExistProductAndUser(Product product, User user) {
        return this.productUserRepository.existsByProductAndUser(product, user);
    }

    public List<String> findUsersByProduct(Long productId) {
        List<String> users = new ArrayList<>();
        this.productUserRepository.findByProductId(productId).forEach(productUser ->
                users.add(productUser.getUser().getUsername()));
        return users;
    }
}
