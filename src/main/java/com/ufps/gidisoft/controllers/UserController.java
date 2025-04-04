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
    private static final String TEACHERS = "teachers";
    private static final String MESSAGE = "message";
    private static final String CREATE_ERROR = "createError";
    private static final String ROLES = "roles";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_USERS = "redirect:/users";

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
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else{
            try{
                model.addAttribute(TEACHERS, userService.findAllUsers());
                model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString())));
                model.addAttribute(ROLES, roleService.findAllRoles());
                return DASHBOARD;
            }catch (BadRequestException e){
                att.addFlashAttribute("unauthorizeUser", "No tienes permisos para esta acción.");
                return REDIRECT_LOGIN;
            }
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "/recovery-password/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, RedirectAttributes att) {
        try {
            userService.sendPasswordResetEmail(email);
            att.addFlashAttribute(MESSAGE, "Correo enviado con éxito.");
        } catch (NotFoundException e) {
            att.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/users/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "/recovery-password/recovery-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token, @RequestParam String password, RedirectAttributes att) {
        try {
            userService.resetPassword(token, password);
            att.addFlashAttribute(MESSAGE, "Contraseña actualizada con éxito.");
            return REDIRECT_LOGIN;
        } catch (BadRequestException e) {
            att.addFlashAttribute("error", e.getMessage());
            return "redirect:/users/reset-password?token=" + token;
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
            return REDIRECT_LOGIN;
        }
    }

    @PostMapping(value = "/save")
    public String createUser(RedirectAttributes att,
                             @Valid @ModelAttribute UserRequest userRequest) {
        try{
            userService.createDraftUser(userRequest);
            att.addFlashAttribute(MESSAGE, "¡Docente registrado con éxito!");
        }catch (BadRequestException e){
            att.addFlashAttribute(CREATE_ERROR, "Ocurrió un error al crear el nuevo docente.");
        }
        return REDIRECT_USERS + "/" + TEACHERS;
    }

    // <-------- PUT METHODS -------->

    @GetMapping(value = "/activate/{usercode}")
    public String activeUser(@PathVariable String usercode, @Valid @ModelAttribute UserRequest userRequest,
                             RedirectAttributes att) {
        try{
            userService.activeUser(usercode);
            att.addFlashAttribute(MESSAGE, "¡Docente activado con éxito!");
        }catch (BadRequestException e){
            att.addFlashAttribute(CREATE_ERROR, "Ocurrió un error al activar el docente.");
        }
        return REDIRECT_USERS + "/" + TEACHERS;
    }

    // <-------- DELETE METHODS -------->

    @GetMapping(value = "/delete/{usercode}")
    public String deleteUser(@PathVariable String usercode, RedirectAttributes att) {
        try{
            userService.deleteUserByUsercode(usercode);
            att.addFlashAttribute(MESSAGE, "¡Docente eliminado con éxito!");
            return REDIRECT_USERS + "/" + TEACHERS;
        }catch (BadRequestException e){
            att.addFlashAttribute(CREATE_ERROR, "Error al eliminar el docente.");
            return REDIRECT_USERS + "/" + TEACHERS;
        }
    }
}
