package com.ufps.gidisoft.repositories;

import com.ufps.gidisoft.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUsercode(String usercode);
    Optional<User> findByUsercode(String usercode);

}
