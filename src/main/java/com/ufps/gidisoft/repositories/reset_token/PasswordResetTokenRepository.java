package com.ufps.gidisoft.repositories.reset_token;

import com.ufps.gidisoft.entities.User;
import com.ufps.gidisoft.entities.reset_token.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    PasswordResetToken findPasswordResetTokenByUser(User user);

    boolean existsByUser(User user);
}
