package com.ufps.gidisoft.controllers;

import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.requests.formats.FormatRequest;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.services.academic_periods.AcademicPeriodsService;
import com.ufps.gidisoft.services.formats.FormatService;
import com.ufps.gidisoft.services.formats.ProjectService;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/formats")
public class FormatsController {

    /*
     * Services
     * */
    private final UserService userService;
    private final FormatService formatService;
    private final AcademicPeriodsService academicPeriodsService;
    private final ProjectService projectService;

    private static final String USERCODE = "usercode";
    private static final String CREATE_ERROR = "createError";
    private static final String FORMATS = "formats";
    private static final String MESSAGE = "message";
    private static final String DASHBOARD = "dashboard";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_FORMAT_LIST = "redirect:/formats/list";
    private static final String FORMAT_NOT_FOUND = "Formato no encontrado.";
    private static final String FORMAT_REQUEST = "formatRequest";
    private static final String DIRECTOR = "director";


    // <-------- GET METHODS -------->

    @GetMapping(value = "/{id}")
    public String getFormats(Model model, HttpServletRequest request, RedirectAttributes att, @PathVariable Long id) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else {
            try {
                User user = userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString());
                model.addAttribute(FORMAT_REQUEST, this.formatService.findFormatById(id));
                model.addAttribute("user", new UsersDto(user));
                model.addAttribute("projects", this.projectService.findByFormatId(id, user));
                model.addAttribute("years", this.academicPeriodsService.findAllAcademicPeriods());
                model.addAttribute(DIRECTOR, this.userService.findAdminUser());
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, FORMAT_NOT_FOUND);
            }
        }
        return FORMATS;
    }

    @GetMapping(value = "/list")
    public String formats(Model model, HttpServletRequest request, RedirectAttributes att) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else {
            try {
                User user = userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString());
                model.addAttribute("user", new UsersDto(user));
                model.addAttribute("users", this.userService.findAllUsers());
                model.addAttribute(FORMATS, this.formatService.findAllFormatsByPermission(user));
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, FORMAT_NOT_FOUND);
            }
        }
        return DASHBOARD;
    }

    @GetMapping(value = "/create")
    public String createFormat(Model model, HttpServletRequest request, RedirectAttributes att) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else {
            try {
                model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString())));
                model.addAttribute("years", this.academicPeriodsService.findAllAcademicPeriods());
                model.addAttribute(DIRECTOR, this.userService.findAdminUser());
                if (!model.containsAttribute(FORMAT_REQUEST)) {
                    model.addAttribute(FORMAT_REQUEST, new FormatRequest());
                }
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, FORMAT_NOT_FOUND);
            }
        }
        return FORMATS;
    }

    @PostMapping(value = "")
    public String saveFormat(Model model, HttpServletRequest request, RedirectAttributes att,
                             @ModelAttribute FormatRequest formatRequest) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else {
            try {
                this.formatService.createFormat(formatRequest);
                model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString())));
                att.addFlashAttribute(MESSAGE, "Formato creado con éxito.");
                return REDIRECT_FORMAT_LIST;
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, e.getMessage());
                att.addFlashAttribute(FORMAT_REQUEST, formatRequest);
                return "redirect:/formats/create";
            }
        }
    }

    @PostMapping(value = "/{id}/permissions")
    public String createRelations(@PathVariable Long id, @RequestParam List<Long> users, RedirectAttributes att,
                                  HttpServletRequest request) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else {
            try {
                this.formatService.createRelationsWithUsers(users, id);
                att.addFlashAttribute(MESSAGE, "Relaciones creadas con éxito.");
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, e.getMessage());
            }
        }
        return REDIRECT_FORMAT_LIST;
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteFormat(HttpServletRequest request, RedirectAttributes att, @PathVariable Long id) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else {
            try {
                this.formatService.deleteById(id);
                att.addFlashAttribute(MESSAGE, "Formato eliminado con éxito.");
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, e.getMessage());
            }
        }
        return REDIRECT_FORMAT_LIST;
    }
}
