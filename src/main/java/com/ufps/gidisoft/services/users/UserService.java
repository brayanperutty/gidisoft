package com.ufps.gidisoft.services.users;

import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.entities.reset_token.PasswordResetToken;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.enums.roles.RolesEnum;
import com.ufps.gidisoft.enums.users.UserStatusEnum;
import com.ufps.gidisoft.exceptions.BadRequestException;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.repositories.users.UserRepository;
import com.ufps.gidisoft.requests.users.UserCredentialsRequest;
import com.ufps.gidisoft.requests.users.UserRequest;
import com.ufps.gidisoft.services.reset_token.PasswordResetTokenService;
import com.ufps.gidisoft.services.roles.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    /*
    Repositories
     */
    private final UserRepository userRepository;

    /*
    * Services
    * */
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
//    private final JavaMailSender mailSender;
    private final PasswordResetTokenService passwordResetTokenService;

    @Value("${DEFEAT_PASSWORD}")
    private String defeatPassword;

//    @Value("${spring.mail.username}")
//    private String email;


    @Transactional
    public User loginUser(UserCredentialsRequest user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return userRepository.findByUsercode(user.getUsername()).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.USER01.getMessage()));
    }

    public User getUserByUsercode(String usercode) {
        return userRepository.findByUsercode(usercode).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.USER01.getMessage()));
    }

    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.USER01.getMessage()));
    }

    @Transactional
    public void createDraftUser(UserRequest userRequest) {
        User user = new User();
        user.setUsercode(userRequest.getUsercode());
        user.setPassword(passwordEncoder.encode(defeatPassword));
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(roleService.getRole(userRequest.getRoleId()));
        user.setUserStatus(UserStatusEnum.DRAFT.getId());
        userRepository.save(user);
    }

    @Transactional
    public void activeUser(String usercode) {
        User user = this.getUserByUsercode(usercode);
        user.setUserStatus(UserStatusEnum.ACTIVE.getId());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUserByUsercode(String usercode) {
        userRepository.deleteUserByUsercode(usercode);
    }

    public List<UsersDto> findAllUsers(User user) {
        return userRepository.findAll().stream().filter(
                u -> !u.getId().equals(user.getId())).map(UsersDto::new)
                .sorted(Comparator.comparing(UsersDto::getUsercode))
                .toList();
    }

    public UsersDto findAdminUser() {
        return new UsersDto(userRepository.findByRole(this.roleService.getRole(RolesEnum.ADMIN.getId())));
    }

    public void invalidateSession(HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getSession().getAttribute("user") != null) {
            httpServletRequest.getSession().removeAttribute("user");
        }
    }

//    @Transactional
//    public void sendPasswordResetEmail(String email) {
//        User user = userRepository.findByEmail(email).orElseThrow(()
//                -> new NotFoundException(ExceptionCodeEnum.USER01.getMessage()));
//
//        String token = UUID.randomUUID().toString();
//        if(passwordResetTokenService.existsPasswordResetTokenByUser(user)) {
//            PasswordResetToken passwordResetToken = passwordResetTokenService.getPasswordResetTokenByUser(user);
//            passwordResetToken.setToken(token);
//            passwordResetTokenService.updateToken(passwordResetToken);
//        } else {
//            passwordResetTokenService.createPasswordResetToken(token, user);
//        }
//        String resetLink = "http://localhost:8080/users/reset-password?token=" + token;
//        sendEmail(user.getEmail(), "Haz clic en el siguiente enlace para restablecer tu contraseña: " + resetLink);
//    }

//    private void sendEmail(String to, String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(email);
//        message.setTo(to);
//        message.setSubject("Recuperación de contraseña");
//        message.setText(text);
//        mailSender.send(message);
//    }

    @Transactional
    public void resetPassword(String token, String newPassword) {

        PasswordResetToken resetToken = passwordResetTokenService.getPasswordResetToken(token);

        if (resetToken.isExpired()) {
            throw new BadRequestException(ExceptionCodeEnum.TOKEN01);
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenService.deletePasswordResetToken(resetToken);
    }
}
