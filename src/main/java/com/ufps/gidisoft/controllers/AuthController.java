package com.ufps.gidisoft.controllers;

import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.requests.users.UserCredentialsRequest;
import com.ufps.gidisoft.responses.utils.DefaultResponse;
import com.ufps.gidisoft.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public DefaultResponse<User> login(@RequestBody UserCredentialsRequest user) {
        return new DefaultResponse<>(this.userService.loginUser(user));
    }
}
