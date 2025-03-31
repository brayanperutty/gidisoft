package com.ufps.gidisoft.controllers;

import com.ufps.gidisoft.entities.User;
import com.ufps.gidisoft.exceptions.BadRequestException;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.requests.user.UserCredentialsRequest;
import com.ufps.gidisoft.requests.user.UserRequest;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.services.RoleService;
import com.ufps.gidisoft.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.AuthenticationException;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    /*
    * Services
    * */
    private final UserService userService;
    private final RoleService roleService;

    private static final String DASHBOARD = "dashboard";
    private static final String USERCODE = "usercode";

    // <-------- GET METHODS -------->

    @GetMapping(value = "/dashboard")
    public String dashboard(Model model, HttpServletRequest request, RedirectAttributes att) {
        try {
            model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                    .getAttribute(USERCODE).toString())));
        }catch (NotFoundException e){
            att.addFlashAttribute("userError", "Usuario no encontrado.");
        }
        return DASHBOARD;
    }

    @GetMapping("/teachers")
    public String getAllTeachers(Model model, HttpServletRequest request, RedirectAttributes att) {
        try{
            model.addAttribute("teachers", userService.findAllUsers());
            model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                    .getAttribute(USERCODE).toString())));
            model.addAttribute("roles", roleService.findAllRoles());
            return DASHBOARD;
        }catch (BadRequestException e){
            att.addFlashAttribute("unauthorizeUser", "No tienes permisos para esta acción.");
            return "redirect:/login";
        }
    }

    // <-------- POST METHODS -------->

    @PostMapping(value = "/sign-in")
    public String login(@Valid @ModelAttribute UserCredentialsRequest user,
                        HttpServletRequest request,
                        RedirectAttributes att) {
        try {
            User userLogged = this.userService.loginUser(user);
            request.getSession().setAttribute(USERCODE, userLogged.getUsercode());
            return "redirect:/users/dashboard";
        } catch (AuthenticationException | NotFoundException e) {
            att.addFlashAttribute("loginError", "Credenciales incorrectas.");
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/save")
    public String createUser(Model model, HttpServletRequest request, RedirectAttributes att,
                             @Valid @ModelAttribute UserRequest userRequest) {
        try{
            userService.createDraftUser(userRequest);
            model.addAttribute("teachers", userService.findAllUsers());
            model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                    .getAttribute(USERCODE).toString())));
            model.addAttribute("roles", roleService.findAllRoles());
        }catch (BadRequestException e){
            att.addFlashAttribute("createError", "Ocurrió un error al crear el nuevo docente.");
        }

        return DASHBOARD;
    }
}
