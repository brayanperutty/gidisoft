package com.ufps.gidisoft.controllers;

import com.ufps.gidisoft.entities.formats.Project;
import com.ufps.gidisoft.requests.formats.ProjectRequest;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.services.formats.ProjectService;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/projects")
@RequiredArgsConstructor
public class ProjectController {

    /*
     * Services
     */
    private final ProjectService projectService;
    private final UserService userService;

    private static final String USERCODE = "usercode";
    private static final String CREATE_ERROR = "createError";
    private static final String MESSAGE = "message";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String FORMAT_REQUEST = "formatRequest";


    @PostMapping(value = "")
    public String addProject(Model model, HttpServletRequest request, @ModelAttribute ProjectRequest projectRequest,
                             RedirectAttributes att) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else {
            try {
                System.out.println(projectRequest);
                Project project = this.projectService.
                        createProject(projectRequest, userService.getUserByUsercode(request.getSession()
                                .getAttribute(USERCODE).toString()));
                model.addAttribute("project", project);
                model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString())));
                att.addFlashAttribute(MESSAGE, "Proyecto creado con éxito.");
                return "redirect:/formats/" + projectRequest.getFormatId();
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, e.getMessage());
                return "redirect:/formats/" + projectRequest.getFormatId();
            }
        }
    }
}
