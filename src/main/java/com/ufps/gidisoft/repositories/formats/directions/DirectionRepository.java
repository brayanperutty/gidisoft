package com.ufps.gidisoft.repositories.formats.directions;

import com.ufps.gidisoft.entities.formats.directions.Direction;
import com.ufps.gidisoft.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {

    List<Direction> findByFormatIdAndCreatedBy(Long formatId, User createdBy);

    List<Direction> findByFormatId(Long formatId);
}
