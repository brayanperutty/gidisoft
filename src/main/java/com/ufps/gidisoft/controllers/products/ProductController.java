package com.ufps.gidisoft.controllers.products;

import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.enums.roles.RolesEnum;
import com.ufps.gidisoft.requests.formats.ProductRequest;
import com.ufps.gidisoft.responses.users.UsersDto;
import com.ufps.gidisoft.services.formats.products.ProductService;
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
@RequestMapping(value = "/products")
public class ProductController {

    /*
     * Services
     */
    private final ProductService productService;
    private final UserService userService;

    private static final String USERCODE = "usercode";
    private static final String CREATE_ERROR = "createError";
    private static final String MESSAGE = "message";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_FORMAT = "redirect:/formats/";
    private static final String REDIRECT_ERROR = "error/403";
    private static final String TAB_REDIRECT = "?tab=products";

    //<---------- GET METHODS ------------->

    @GetMapping(value = "/{id}/delete")
    public String deleteProject(Model model, HttpServletRequest request, @PathVariable Long id,
                                RedirectAttributes att, @RequestParam Long formatId) {
        if (request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        } else {
            User user = userService.getUserByUsercode(request.getSession()
                    .getAttribute(USERCODE).toString());
            if (!this.productService.validateProductWithUser(id, user) &&
                    !user.getRole().getId().equals(RolesEnum.ADMIN.getId())) {
                return REDIRECT_ERROR;
            } else {
                try {
                    this.productService.deleteById(id);
                    att.addFlashAttribute(MESSAGE, "Producto eliminado con éxito.");
                } catch (Exception e) {
                    att.addFlashAttribute(CREATE_ERROR, e.getMessage());
                }
            }
        }
        return REDIRECT_FORMAT + formatId + TAB_REDIRECT;
    }

    @GetMapping(value = "/{id}/delete-evidence")
    public String deleteEvidence(Model model, HttpServletRequest request, @PathVariable Long id,
                                 RedirectAttributes att, @RequestParam String url, @RequestParam Long formatId) {
        if (request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        } else {
            User user = userService.getUserByUsercode(request.getSession()
                    .getAttribute(USERCODE).toString());
            if (!this.productService.validateProductWithUser(id, user)) {
                return REDIRECT_ERROR;
            } else {
                try {
                    this.productService.deleteEvidence(id, url);
                    model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                            .getAttribute(USERCODE).toString())));
                    att.addFlashAttribute(MESSAGE, "Evidencia eliminada con éxito.");
                } catch (Exception e) {
                    att.addFlashAttribute(CREATE_ERROR, e.getMessage());
                }
            }
        }
        return REDIRECT_FORMAT + formatId + TAB_REDIRECT;
    }

    //<---------- POST METHODS ------------->

    @PostMapping(value = "")
    public String addEvent(Model model, HttpServletRequest request, @ModelAttribute ProductRequest productRequest,
                           RedirectAttributes att) {
        if(request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        }else{
            try {
                model.addAttribute("user", new UsersDto(userService.getUserByUsercode(request.getSession()
                        .getAttribute(USERCODE).toString())));
                if(productRequest.getId() != null) {
                    this.productService.updateProduct(productRequest, userService.getUserByUsercode(request.getSession()
                            .getAttribute(USERCODE).toString()));
                }else {
                    this.productService.createProduct(productRequest, userService.getUserByUsercode(request.getSession()
                            .getAttribute(USERCODE).toString()));
                }
                att.addFlashAttribute(MESSAGE, "Producto guardado con éxito.");
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, e.getMessage());
            }
        }
        return REDIRECT_FORMAT + productRequest.getFormatId() + TAB_REDIRECT;
    }

    @PostMapping(value = "/{id}/permissions")
    public String createRelations(@PathVariable Long id, @RequestParam List<Long> users, RedirectAttributes att,
                                  HttpServletRequest request, @RequestParam Long formatId) {
        if (request.getSession().getAttribute(USERCODE) == null) {
            return REDIRECT_LOGIN;
        } else {
            try {
                this.productService.createRelationWithUser(users, id);
                att.addFlashAttribute(MESSAGE, "Producto compartido con éxito.");
            } catch (Exception e) {
                att.addFlashAttribute(CREATE_ERROR, e.getMessage());
            }
        }
        return REDIRECT_FORMAT + formatId + TAB_REDIRECT;
    }
}
