package com.ufps.gidisoft.repositories;

import com.ufps.gidisoft.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsRoleByType(String type);
}
