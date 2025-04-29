package com.ufps.gidisoft.services.formats.directions;

import com.ufps.gidisoft.entities.formats.directions.Direction;
import com.ufps.gidisoft.entities.formats.directions.DirectionUser;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.repositories.formats.directions.DirectionUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectionUserService {

    /*
     * Repositories
     */
    private final DirectionUserRepository directionUserRepository;

    public void createDirectionUser(Direction direction, User user){
        this.directionUserRepository.save(new DirectionUser(direction, user));
    }

    public boolean validateDirectionUser(Direction direction, User user){
        return this.directionUserRepository.existsByDirectionAndUser(direction, user);
    }

    @Transactional
    public void deleteByDirectionId(Long directionId){
        this.directionUserRepository.deleteAllByDirectionId(directionId);
    }
}
