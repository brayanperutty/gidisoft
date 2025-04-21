package com.ufps.gidisoft.controllers;

import com.ufps.gidisoft.requests.formats.FormatRequest;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.services.AcademicPeriodsService;
import com.ufps.gidisoft.services.FormatService;
import com.ufps.gidisoft.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    private static final String USERCODE = "usercode";
    private static final String CREATE_ERROR = "createError";
    private static final String FORMATS = "formats";
    private static final String MESSAGE = "message";
    private static final String DASHBOARD = "dashboard";
    private static final String REDIRECT_LOGIN = "redirect:/login";


    // <-------- GET METHODS -------->

    @GetMapping(value = "/list")
    public String formats(Model model, HttpServletRequest request, RedirectAttributes att) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else {
            try {
                model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString())));
                model.addAttribute(FORMATS, formatService.findAllFormats());
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, "Formato no encontrado.");
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
                model.addAttribute("years", this.academicPeriodsService.getAllYearsAcademicPeriods());
                model.addAttribute("periods", this.academicPeriodsService.getAllPeriodssAcademicPeriods());
                model.addAttribute("director", this.userService.findAdminUser());
                model.addAttribute("teachers", this.userService.findAllUsers());
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, "Formato no encontrado.");
            }
        }
        return FORMATS;
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String saveFormat(Model model, HttpServletRequest request, RedirectAttributes att,
                             @RequestBody FormatRequest formatRequest) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else {
            try {
                this.formatService.createFormat(formatRequest);
                model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString())));
                att.addFlashAttribute(MESSAGE, "Formato creado con éxito.");
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, "Formato no encontrado.");
            }
        }
        return "redirect:/formats/list";
    }
}
