package com.ufps.gidisoft.controllers;

import com.ufps.gidisoft.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping(value = "")
    public String login(HttpServletRequest httpServletRequest) {
        userService.invalidateSession(httpServletRequest);
        return "login";
    }
}
