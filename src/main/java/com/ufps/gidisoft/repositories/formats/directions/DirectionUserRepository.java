package com.ufps.gidisoft.repositories.formats.directions;

import com.ufps.gidisoft.entities.formats.directions.Direction;
import com.ufps.gidisoft.entities.formats.directions.DirectionUser;
import com.ufps.gidisoft.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionUserRepository extends JpaRepository<DirectionUser, Long> {
    boolean existsByDirectionAndUser(Direction direction, User user);

    void deleteAllByDirectionId(Long directionId);
}
