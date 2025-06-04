package com.ufps.gidisoft.controllers.formats;

import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.requests.formats.FormatRequest;
import com.ufps.gidisoft.requests.formats.FormatsFilter;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.services.academic_periods.AcademicPeriodsService;
import com.ufps.gidisoft.services.faculties.FacultyService;
import com.ufps.gidisoft.services.formats.directions.DirectionService;
import com.ufps.gidisoft.services.formats.events.EventService;
import com.ufps.gidisoft.services.formats.general.FormatService;
import com.ufps.gidisoft.services.formats.others.OtherActivityService;
import com.ufps.gidisoft.services.formats.products.ProductService;
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
    private final EventService eventService;
    private final OtherActivityService otherActivityService;
    private final ProductService productService;

    private static final String USERCODE = "usercode";
    private static final String CREATE_ERROR = "createError";
    private static final String FORMATS = "formats";
    private static final String REDIRECT_FORMATS = "redirect:/formats/";
    private static final String MESSAGE = "message";
    private static final String DASHBOARD = "dashboard";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_FORMAT_LIST = "redirect:/formats/list";
    private static final String FORMAT_NOT_FOUND = "Formato no encontrado.";
    private static final String FORMAT_REQUEST = "formatRequest";
    private static final String DIRECTOR = "director";
    private static final String ADMIN = "admin";
    private static final String REDIRECT_ERROR = "error/403";
    private static final String TEACHERS = "teachersSelect";
    private static final String YEARS = "years";
    private static final String GROUPS = "groups";


    // <-------- GET METHODS -------->

    @GetMapping(value = "/{id}")
    public String getFormats(Model model, HttpServletRequest request, RedirectAttributes att, @PathVariable Long id,
                             @RequestParam(defaultValue = "general") String tab) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else {
            try {
                User user = userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString());
                model.addAttribute(FORMAT_REQUEST, this.formatService.findFormatById(id));
                model.addAttribute("user", new UsersDto(user));
                model.addAttribute("projects", this.projectService.findByFormatId(id));
                model.addAttribute("directions", this.directionService.findByFormatId(id));
                model.addAttribute("events", this.eventService.findByFormatId(id));
                model.addAttribute("othersActivities", this.otherActivityService.findByFormatId(id));
                model.addAttribute("groupedProducts", this.productService.findByFormatIdGrouped(id));
                model.addAttribute(YEARS, this.academicPeriodsService.findAllAcademicPeriods());
                model.addAttribute("faculties", this.facultyService.findAllFaculties());
                model.addAttribute(GROUPS, this.investigationGroupService.findAllInvestigationGroups());
                model.addAttribute("users", this.userService.findAllUsers());
                model.addAttribute("editableProjectsIds", this.projectUserService.getProjectIdsUserCanEdit(user));
                model.addAttribute(DIRECTOR, this.userService.findAdminUser());
                model.addAttribute("tab", tab);
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, FORMAT_NOT_FOUND);
            }
        }
        return FORMATS;
    }

    @GetMapping(value = "/list")
    public String formats(Model model, HttpServletRequest request, RedirectAttributes att,
                          @RequestParam(required = false) Long teacher,
                          @RequestParam(required = false) Long group,
                          @RequestParam(required = false) Long academicPeriod,
                          @RequestParam(required = false) Long status) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else {
            try {
                User user = userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString());
                model.addAttribute("user", new UsersDto(user));
                model.addAttribute(FORMATS, this.formatService.findAllFormats(new FormatsFilter(teacher, group,
                        academicPeriod, status)));
                model.addAttribute(TEACHERS, this.userService.findAllUsers());
                model.addAttribute(GROUPS, this.investigationGroupService.findAllInvestigationGroups());
                model.addAttribute(YEARS, this.academicPeriodsService.findAllAcademicPeriods());
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
                    model.addAttribute(YEARS, this.academicPeriodsService.findAllAcademicPeriods());
                    model.addAttribute(DIRECTOR, this.userService.findAdminUser());
                    model.addAttribute("faculties", this.facultyService.findAllFaculties());
                    model.addAttribute(GROUPS, this.investigationGroupService.findAllInvestigationGroups());
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
                    model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                            .getAttribute(USERCODE).toString())));
                    att.addFlashAttribute(MESSAGE, "Formato guardado con éxito.");
                    if(formatRequest.getFormatId() != null){
                        this.formatService.updateFormat(formatRequest);
                        return REDIRECT_FORMATS + formatRequest.getFormatId();
                    } else {
                        this.formatService.createFormat(formatRequest);
                        return REDIRECT_FORMAT_LIST;
                    }
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
                    att.addFlashAttribute(MESSAGE, "Formato publicado con éxito.");
                } catch (Exception e) {
                    att.addFlashAttribute(CREATE_ERROR, e.getMessage());
                }
            }
        }
        return REDIRECT_FORMAT_LIST;
    }
}
