package com.ufps.gidisoft.controllers.directions;

import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.requests.formats.DirectionRequest;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.services.formats.directions.DirectionService;
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
@RequestMapping(value = "/directions")
public class DirectionController {

    private final DirectionService directionService;
    private final UserService userService;

    private static final String USERCODE = "usercode";
    private static final String CREATE_ERROR = "createError";
    private static final String MESSAGE = "message";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_FORMAT = "redirect:/formats/";
    private static final String REDIRECT_ERROR = "error/403";

    //<---------- GET METHODS ------------->

    @GetMapping(value = "/{id}/delete")
    public String deleteProject(Model model, HttpServletRequest request, @PathVariable Long id,
                                RedirectAttributes att, @RequestParam Long formatId) {
        if (request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        } else {
            User user = userService.getUserByUsercode(request.getSession()
                    .getAttribute(USERCODE).toString());
            if (!this.directionService.validateDirectionWithUser(id, user)) {
                return REDIRECT_ERROR;
            } else {
                try {
                    this.directionService.deleteById(id);
                    att.addFlashAttribute(MESSAGE, "Participación eliminada con éxito.");
                } catch (Exception e) {
                    att.addFlashAttribute(CREATE_ERROR, e.getMessage());
                }
            }
        }
        return REDIRECT_FORMAT + formatId;
    }

    @GetMapping(value = "/{id}/delete-evidence")
    public String deleteEvidence(Model model, HttpServletRequest request, @PathVariable Long id,
                                 RedirectAttributes att, @RequestParam String url, @RequestParam Long formatId) {
        if (request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        } else {
            User user = userService.getUserByUsercode(request.getSession()
                    .getAttribute(USERCODE).toString());
            if (!this.directionService.validateDirectionWithUser(id, user)) {
                return REDIRECT_ERROR;
            } else {
                try {
                    this.directionService.deleteEvidence(id, url);
                    model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                            .getAttribute(USERCODE).toString())));
                    att.addFlashAttribute(MESSAGE, "Evidencia eliminada con éxito.");
                } catch (Exception e) {
                    att.addFlashAttribute(CREATE_ERROR, e.getMessage());
                }
            }
        }
        return REDIRECT_FORMAT + formatId;
    }

    //<---------- POST METHODS ------------->

    @PostMapping(value = "")
    public String addDirection(Model model, HttpServletRequest request, @ModelAttribute DirectionRequest directionRequest,
                             RedirectAttributes att) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        } else {
            try {
                model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString())));
                if(directionRequest.getId() != null) {
                    this.directionService.updateDirection(directionRequest, userService.getUserByUsercode(request.getSession()
                            .getAttribute(USERCODE).toString()));
                } else {
                    this.directionService.
                            createDirection(directionRequest, userService.getUserByUsercode(request.getSession()
                                    .getAttribute(USERCODE).toString()));
                }
                att.addFlashAttribute(MESSAGE, "Participación guardada con éxito.");
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, e.getMessage());
            }
        }
        return REDIRECT_FORMAT + directionRequest.getFormatId();
    }



    @PostMapping(value = "/{id}/permissions")
    public String createRelations(@PathVariable Long id, @RequestParam List<Long> users, RedirectAttributes att,
                                  HttpServletRequest request, @RequestParam Long formatId) {
        if (request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        } else {
            try {
                this.directionService.createRelationsWithUsers(users, id);
                att.addFlashAttribute(MESSAGE, "Participación compartida con éxito.");
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, e.getMessage());
            }
        }
        return REDIRECT_FORMAT + formatId;
    }
}
