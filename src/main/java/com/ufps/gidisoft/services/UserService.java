package com.ufps.gidisoft.services;

import com.ufps.gidisoft.entities.User;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.enums.roles.RolesEnum;
import com.ufps.gidisoft.enums.users.UserStatusEnum;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.repositories.UserRepository;
import com.ufps.gidisoft.requests.user.UserCredentialsRequest;
import com.ufps.gidisoft.requests.user.UserRequest;
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

    @Value("${DEFEAT_PASSWORD}")
    private String defeatPassword;


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

    public User activeUser(Long idUser) {
        User user = userRepository.findById(idUser).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.USER01.getMessage()));
        user.setUserStatus(UserStatusEnum.ACTIVE.getId());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UsersDto> findAllUsers() {
        return userRepository.findAll().stream().filter(
                u -> !u.getRole().getId().equals(RolesEnum.ADMIN.getId())).map(UsersDto::new)
                .sorted(Comparator.comparing(UsersDto::getUsercode))
                .toList();
    }

    public void invalidateSession(HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getSession().getAttribute("user") != null) {
            httpServletRequest.getSession().removeAttribute("user");
        }
    }
}
