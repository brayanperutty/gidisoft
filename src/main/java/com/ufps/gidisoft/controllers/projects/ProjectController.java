package com.ufps.gidisoft.controllers.projects;

import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.requests.formats.ProjectRequest;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.services.formats.projects.ProjectService;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    private static final String REDIRECT_ERROR = "error/403";
    private static final String REDIRECT_FORMAT = "redirect:/formats/";


    @PostMapping(value = "")
    public String addProject(Model model, HttpServletRequest request, @ModelAttribute ProjectRequest projectRequest,
                             RedirectAttributes att) {
        if (request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        } else {
            try {
                model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString())));
                if (projectRequest.getId() != null) {
                    this.projectService.updateProject(projectRequest, userService.getUserByUsercode(request.getSession()
                            .getAttribute(USERCODE).toString()));
                } else {
                    this.projectService.
                            createProject(projectRequest, userService.getUserByUsercode(request.getSession()
                                    .getAttribute(USERCODE).toString()));
                }
                att.addFlashAttribute(MESSAGE, "Proyecto guardado con éxito.");
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, e.getMessage());
            }
        }
        return REDIRECT_FORMAT + projectRequest.getFormatId();
    }

    @GetMapping(value = "/{id}/delete")
    public String deleteProject(Model model, HttpServletRequest request, @PathVariable Long id,
                                RedirectAttributes att, @RequestParam Long formatId) {
        if (request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        } else {
            User user = userService.getUserByUsercode(request.getSession()
                    .getAttribute(USERCODE).toString());
            if (!this.projectService.validateProjectWithUser(id, user)) {
                return REDIRECT_ERROR;
            } else {
                try {
                    this.projectService.deleteById(id);
                    model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                            .getAttribute(USERCODE).toString())));
                    att.addFlashAttribute(MESSAGE, "Proyecto eliminado con éxito.");
                } catch (Exception e) {
                    att.addFlashAttribute(CREATE_ERROR, e.getMessage());
                }
            }
        }
        return REDIRECT_FORMAT + formatId;
    }

    @PostMapping(value = "/{id}/permissions")
    public String createRelations(@PathVariable Long id, @RequestParam List<Long> users, RedirectAttributes att,
                                  HttpServletRequest request, @RequestParam Long formatId) {
        if (request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        } else {
            try {
                this.projectService.createRelationsWithUsers(users, id);
                att.addFlashAttribute(MESSAGE, "Proyecto compartido con éxito.");
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, e.getMessage());
            }
        }
        return REDIRECT_FORMAT + formatId;
    }
}
