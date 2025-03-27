package com.ufps.gidisoft.seeders;

import com.ufps.gidisoft.entities.Role;
import com.ufps.gidisoft.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SeederRole {

    private final RoleRepository roleRepository;

    public void seed() {
        getRole(roleRepository);
    }

    private static void getRole(RoleRepository roleRepository) {
        if (!roleRepository.existsRoleByType("admin") &&
                !roleRepository.existsRoleByType("teacher") &&
                !roleRepository.existsRoleByType("director")) {
            Role adminRole = new Role(null, "Administrador", "admin");
            Role teacherRole = new Role(null, "Docente investigador", "teacher");
            Role directorRole = new Role(null, "Director de grupo", "director");

            roleRepository.save(adminRole);
            roleRepository.save(teacherRole);
            roleRepository.save(directorRole);
        }
    }
}
