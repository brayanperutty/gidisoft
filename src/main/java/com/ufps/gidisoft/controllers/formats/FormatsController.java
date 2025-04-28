package com.ufps.gidisoft.controllers.formats;

import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.requests.formats.FormatRequest;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.services.academic_periods.AcademicPeriodsService;
import com.ufps.gidisoft.services.faculties.FacultyService;
import com.ufps.gidisoft.services.formats.directions.DirectionService;
import com.ufps.gidisoft.services.formats.general.FormatService;
import com.ufps.gidisoft.services.formats.projects.ProjectService;
import com.ufps.gidisoft.services.formats.projects.ProjectUserService;
import com.ufps.gidisoft.services.groups.InvestigationGroupService;
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
    private final ProjectUserService projectUserService;
    private final DirectionService directionService;
    private final FacultyService facultyService;
    private final InvestigationGroupService investigationGroupService;

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
    private static final String ADMIN = "admin";
    private static final String REDIRECT_ERROR = "error/403";


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
                model.addAttribute("projects", this.projectService.findByFormatId(id));
                model.addAttribute("directions", this.directionService.findByFormatId(id, user));
                model.addAttribute("years", this.academicPeriodsService.findAllAcademicPeriods());
                model.addAttribute("faculties", this.facultyService.findAllFaculties());
                model.addAttribute("groups", this.investigationGroupService.findAllInvestigationGroups());
                model.addAttribute("users", this.userService.findAllUsers(user));
                List<Long> editableProjectsIds = this.projectUserService.getProjectIdsUserCanEdit(user);
                model.addAttribute("editableProjectsIds", editableProjectsIds);
                model.addAttribute(DIRECTOR, this.userService.findAdminUser());
                System.out.println(model.getAttribute("users"));
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
                model.addAttribute(FORMATS, this.formatService.findAllFormats());
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
            User user = userService.getUserByUsercode(request.getSession()
                    .getAttribute(USERCODE).toString());
            if(!user.getRole().getType().equals(ADMIN)){
                return REDIRECT_ERROR;
            }else {
                try {
                    model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                            .getAttribute(USERCODE).toString())));
                    model.addAttribute("years", this.academicPeriodsService.findAllAcademicPeriods());
                    model.addAttribute(DIRECTOR, this.userService.findAdminUser());
                    model.addAttribute("faculties", this.facultyService.findAllFaculties());
                    model.addAttribute("groups", this.investigationGroupService.findAllInvestigationGroups());
                    if (!model.containsAttribute(FORMAT_REQUEST)) {
                        model.addAttribute(FORMAT_REQUEST, new FormatRequest());
                    }
                } catch (Exception e) {
                    att.addFlashAttribute(CREATE_ERROR, FORMAT_NOT_FOUND);
                }
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
            User user = userService.getUserByUsercode(request.getSession()
                    .getAttribute(USERCODE).toString());
            if(!user.getRole().getType().equals(ADMIN)){
                return REDIRECT_ERROR;
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
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteFormat(HttpServletRequest request, RedirectAttributes att, @PathVariable Long id) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else {
            User user = userService.getUserByUsercode(request.getSession()
                    .getAttribute(USERCODE).toString());
            if(!user.getRole().getType().equals(ADMIN)){
                return REDIRECT_ERROR;
            }else{
                try {
                    this.formatService.deleteById(id);
                    att.addFlashAttribute(MESSAGE, "Formato eliminado con éxito.");
                } catch (Exception e) {
                    att.addFlashAttribute(CREATE_ERROR, e.getMessage());
                }
            }
        }
        return REDIRECT_FORMAT_LIST;
    }

    @GetMapping(value = "/{id}/publish")
    public String publishProject(HttpServletRequest request, @PathVariable Long id,
                                 RedirectAttributes att){
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        } else {
            User user = userService.getUserByUsercode(request.getSession()
                    .getAttribute(USERCODE).toString());
            if (!user.getRole().getType().equals(ADMIN)) {
                return REDIRECT_ERROR;
            } else {
                try {
                    this.formatService.publishFormat(id);
                    att.addFlashAttribute(MESSAGE, "Proyecto publicado con éxito.");
                } catch (Exception e) {
                    att.addFlashAttribute(CREATE_ERROR, e.getMessage());
                }
            }
        }
        return REDIRECT_FORMAT_LIST;
    }
}
