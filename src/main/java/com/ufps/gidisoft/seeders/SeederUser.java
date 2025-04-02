package com.ufps.gidisoft.seeders;

import com.ufps.gidisoft.entities.User;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.enums.roles.RolesEnum;
import com.ufps.gidisoft.enums.users.UserStatusEnum;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.repositories.RoleRepository;
import com.ufps.gidisoft.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SeederUser {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String DEFEAT_PHONE = "123456789";

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Value("${DEFEAT_PASSWORD}")
    private String defeatPassword;


    public void seed(){
        getUser(userRepository, roleRepository, passwordEncoder, adminPassword, defeatPassword);
    }

    private static void getUser(UserRepository userRepository,
                                RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                                String adminPassword, String defeatPassword) {
        if(!userRepository.existsUserByUsercode("0001") ||
        !userRepository.existsUserByUsercode("0002") ||
        !userRepository.existsUserByUsercode("0003") ||
        !userRepository.existsUserByUsercode("0004") ||
        !userRepository.existsUserByUsercode("0005")){

            User userAdmin = new User();
            userAdmin.setUsercode("0001");
            userAdmin.setPassword(passwordEncoder.encode(adminPassword));
            userAdmin.setEmail("admin@ufps.com");
            userAdmin.setName("Judith del Pilar Rodríguez Tenjo");
            userAdmin.setPhoneNumber(DEFEAT_PHONE);
            userAdmin.setRole(roleRepository.findById(RolesEnum.ADMIN.getId()).orElseThrow(()
                    -> new NotFoundException(ExceptionCodeEnum.ROLE01.getMessage())));
            userAdmin.setUserStatus(UserStatusEnum.ACTIVE.getId());

            User user1 = new User();
            user1.setUsercode("0002");
            user1.setPassword(passwordEncoder.encode(defeatPassword));
            user1.setEmail("user1@ufps.com");
            user1.setName("María del Pilar Rojas");
            user1.setPhoneNumber(DEFEAT_PHONE);
            user1.setRole(roleRepository.findById(RolesEnum.TEACHER.getId()).orElseThrow(()
                    -> new NotFoundException(ExceptionCodeEnum.ROLE01.getMessage())));
            user1.setUserStatus(UserStatusEnum.ACTIVE.getId());

            User user2 = new User();
            user2.setUsercode("0003");
            user2.setPassword(passwordEncoder.encode(defeatPassword));
            user2.setEmail("user2@ufps.com");
            user2.setName("Marco Antonio Adarme");
            user2.setPhoneNumber(DEFEAT_PHONE);
            user2.setRole(roleRepository.findById(RolesEnum.TEACHER.getId()).orElseThrow(()
                    -> new NotFoundException(ExceptionCodeEnum.ROLE01.getMessage())));
            user2.setUserStatus(UserStatusEnum.ACTIVE.getId());

            User user3 = new User();
            user3.setUsercode("0004");
            user3.setPassword(passwordEncoder.encode(defeatPassword));
            user3.setEmail("user3@ufps.com");
            user3.setName("Carlos René Angarita");
            user3.setPhoneNumber(DEFEAT_PHONE);
            user3.setRole(roleRepository.findById(RolesEnum.TEACHER.getId()).orElseThrow(()
                    -> new NotFoundException(ExceptionCodeEnum.ROLE01.getMessage())));
            user3.setUserStatus(UserStatusEnum.ACTIVE.getId());

            User user4 = new User();
            user4.setUsercode("0005");
            user4.setPassword(passwordEncoder.encode(defeatPassword));
            user4.setEmail("user4@ufps.com");
            user4.setName("Brayan Alexander Perutty Ramirez");
            user4.setPhoneNumber(DEFEAT_PHONE);
            user4.setRole(roleRepository.findById(RolesEnum.TEACHER.getId()).orElseThrow(()
                    -> new NotFoundException(ExceptionCodeEnum.ROLE01.getMessage())));
            user4.setUserStatus(UserStatusEnum.ACTIVE.getId());

            userRepository.save(userAdmin);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
        }
    }
}
