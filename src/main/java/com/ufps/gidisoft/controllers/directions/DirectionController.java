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

    @PostMapping(value = "")
    public String addDirection(Model model, HttpServletRequest request, @ModelAttribute DirectionRequest directionRequest,
                             RedirectAttributes att) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        } else {
            try {
                this.directionService.
                        createDirection(directionRequest, userService.getUserByUsercode(request.getSession()
                                .getAttribute(USERCODE).toString()));
                model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString())));
                att.addFlashAttribute(MESSAGE, "Participación creada con éxito.");
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, e.getMessage());
            }
        }
        return REDIRECT_FORMAT + directionRequest.getFormatId();
    }

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
}
