package com.ufps.gidisoft.seeders;

import com.ufps.gidisoft.entities.User;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.enums.roles.RolesEnum;
import com.ufps.gidisoft.enums.users.UserStatusEnum;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.repositories.RoleRepository;
import com.ufps.gidisoft.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SeederUser {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void seed(){
        getUser(userRepository, roleRepository, passwordEncoder);
    }

    private static void getUser(UserRepository userRepository,
                                RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        if(!userRepository.existsUserByUsercode("0001")){
            User user = new User();
            user.setUsercode("0001");
            user.setPassword(passwordEncoder.encode("admin123."));
            user.setEmail("admin@ufps.com");
            user.setName("Administrador");
            user.setPhoneNumber("123456789");
            user.setRole(roleRepository.findById(RolesEnum.ADMIN.getId()).orElseThrow(()
                    -> new NotFoundException(ExceptionCodeEnum.ROLE01.getMessage())));
            user.setUserStatus(UserStatusEnum.ACTIVE.getId());
            userRepository.save(user);
        }
    }
}
