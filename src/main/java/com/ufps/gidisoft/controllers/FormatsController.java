package com.ufps.gidisoft.controllers;

import com.ufps.gidisoft.requests.formats.FormatRequest;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.services.academic_periods.AcademicPeriodsService;
import com.ufps.gidisoft.services.formats.FormatService;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
                model.addAttribute("years", this.academicPeriodsService.findAllAcademicPeriods());
                model.addAttribute("director", this.userService.findAdminUser());
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, "Formato no encontrado.");
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
                System.out.println(formatRequest);
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
