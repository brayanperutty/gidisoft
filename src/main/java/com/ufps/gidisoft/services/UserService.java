package com.ufps.gidisoft.services;

import com.ufps.gidisoft.config.jwt.JwtService;
import com.ufps.gidisoft.entities.User;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.enums.users.UserStatusEnum;
import com.ufps.gidisoft.exceptions.BadRequestException;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.repositories.UserRepository;
import com.ufps.gidisoft.requests.user.UserCredentialsRequest;
import com.ufps.gidisoft.requests.user.UserRequest;
import com.ufps.gidisoft.responses.users.UserResponse;
import com.ufps.gidisoft.utils.SecurePasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final SecurePasswordGenerator securePasswordGenerator;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public UserResponse loginUser(UserCredentialsRequest user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        User userDetails = userRepository.findByUsercode(user.getUsername()).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.USER01.getMessage()));
        return new UserResponse(jwtService.getToken(userDetails), userDetails);
    }

    public UserResponse getUserById(Long id) {
        return new UserResponse(userRepository.findById(id).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.USER01.getMessage())));
    }

    public UserResponse createDraftUser(UserRequest userRequest) {
        User user = new User();
        user.setUsercode(userRequest.getUsercode());
        user.setPassword(securePasswordGenerator.generatePassword());
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(roleService.getRole(userRequest.getRoleId()));
        user.setUserStatus(UserStatusEnum.DRAFT.getId());
        return new UserResponse(userRepository.save(user));
    }

    public UserResponse activeUser(Long idUser) {
        User user = userRepository.findById(idUser).orElseThrow(()
                -> new NotFoundException(ExceptionCodeEnum.USER01.getMessage()));
        user.setUserStatus(UserStatusEnum.ACTIVE.getId());
        return new UserResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
