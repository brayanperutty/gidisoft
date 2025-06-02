package com.ufps.gidisoft.repositories.formats.others;

import com.ufps.gidisoft.entities.formats.others.OtherActivity;
import com.ufps.gidisoft.entities.formats.others.OtherActivityUser;
import com.ufps.gidisoft.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtherActivityUserRepository extends JpaRepository<OtherActivityUser, Long> {
    void deleteByOtherActivityId(Long otherActivityId);

    boolean existsByOtherActivityAndUser(OtherActivity otherActivity, User user);

    List<OtherActivityUser> findByOtherActivityId(Long otherActivityId);
}
