package com.ufps.gidisoft.services;

import com.ufps.gidisoft.entities.User;
import com.ufps.gidisoft.entities.reset_token.PasswordResetToken;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.exceptions.BadRequestException;
import com.ufps.gidisoft.repositories.reset_token.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public void createPasswordResetToken(String token, User user) {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpirationDate(LocalDateTime.now().plusMinutes(30));
        passwordResetTokenRepository.save(resetToken);
    }

    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException(ExceptionCodeEnum.TOKEN02));
    }

    public void deletePasswordResetToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    public PasswordResetToken getPasswordResetTokenByUser(User user) {
        return passwordResetTokenRepository.findPasswordResetTokenByUser(user);
    }

    public boolean existsPasswordResetTokenByUser(User user) {
        return passwordResetTokenRepository.existsByUser(user);
    }

    public void updateToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.save(passwordResetToken);
    }
}
