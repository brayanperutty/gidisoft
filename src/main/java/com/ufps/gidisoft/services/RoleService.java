package com.ufps.gidisoft.services;

import com.ufps.gidisoft.entities.Role;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.exceptions.NotFoundException;
import com.ufps.gidisoft.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRole(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new NotFoundException(ExceptionCodeEnum.ROLE01.getMessage()));
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
