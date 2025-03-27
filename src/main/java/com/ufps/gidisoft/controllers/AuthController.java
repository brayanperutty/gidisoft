package com.ufps.gidisoft.controllers;

import com.ufps.gidisoft.requests.user.UserCredentialsRequest;
import com.ufps.gidisoft.responses.users.UserResponse;
import com.ufps.gidisoft.responses.utils.DefaultResponse;
import com.ufps.gidisoft.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public DefaultResponse<UserResponse> login(@RequestBody UserCredentialsRequest user) {
        return new DefaultResponse<>(this.userService.loginUser(user));
    }
}
