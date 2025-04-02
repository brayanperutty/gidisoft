package com.ufps.gidisoft.controllers;

import com.ufps.gidisoft.entities.Role;
import com.ufps.gidisoft.responses.utils.DefaultResponse;
import com.ufps.gidisoft.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public DefaultResponse<Role> getRole(@PathVariable Long id) {
        return new DefaultResponse<>(this.roleService.getRole(id));
    }
}
