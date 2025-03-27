package com.ufps.gidisoft.controllers;

import com.ufps.gidisoft.entities.User;
import com.ufps.gidisoft.requests.user.UserCredentialsRequest;
import com.ufps.gidisoft.requests.user.UserRequest;
import com.ufps.gidisoft.responses.users.UserResponse;
import com.ufps.gidisoft.responses.utils.DefaultResponse;
import com.ufps.gidisoft.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    // <-------- GET METHODS -------->

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public DefaultResponse<UserResponse> getUser(@PathVariable Long id) {
        return new DefaultResponse<>(this.userService.getUserById(id));
    }

    // <-------- POST METHODS -------->

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public DefaultResponse<UserResponse> createUser(@Valid @RequestBody UserRequest user) {
        return new DefaultResponse<>(this.userService.createDraftUser(user));
    }

    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public DefaultResponse<UserResponse> login(@Valid @RequestBody UserCredentialsRequest user) {
        return new DefaultResponse<>(this.userService.loginUser(user));
    }

    // <-------- PATCH METHODS -------->

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DefaultResponse<UserResponse> activateUser(@PathVariable Long id) {
        return new DefaultResponse<>(this.userService.activeUser(id));
    }
}
