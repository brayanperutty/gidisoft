package com.ufps.gidisoft.repositories.formats;

import com.ufps.gidisoft.entities.formats.general.FormatUser;
import com.ufps.gidisoft.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormatUserRepository extends JpaRepository<FormatUser, Long> {
    List<FormatUser> findByUser(User user);

    void deleteAllByFormatId(Long formatId);
}
