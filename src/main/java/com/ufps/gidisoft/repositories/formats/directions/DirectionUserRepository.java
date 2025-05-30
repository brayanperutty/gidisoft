package com.ufps.gidisoft.repositories.formats.directions;

import com.ufps.gidisoft.entities.formats.directions.Direction;
import com.ufps.gidisoft.entities.formats.directions.DirectionUser;
import com.ufps.gidisoft.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectionUserRepository extends JpaRepository<DirectionUser, Long> {
    boolean existsByDirectionAndUser(Direction direction, User user);

    void deleteAllByDirectionId(Long directionId);

    List<DirectionUser> findByDirectionId(Long directionId);

    void deleteByDirectionIdAndUserId(Long directionId, Long userId);
}
