package com.ufps.gidisoft.services.formats.directions;

import com.ufps.gidisoft.entities.formats.directions.Direction;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.repositories.formats.DirectionRepository;
import com.ufps.gidisoft.requests.formats.DirectionRequest;
import com.ufps.gidisoft.responses.format.DirectionDto;
import com.ufps.gidisoft.services.formats.general.FormatServiceSec;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectionService {

    /*
     * Repositorires
     */
    private final DirectionRepository directionRepository;

    /*
     * Services
     */
    private final UserService userService;
    private final FormatServiceSec formatServiceSec;

    @Transactional
    public void createDirection(DirectionRequest directionRequest, User user) {
        Direction direction = new Direction();
        direction.setName(directionRequest.getName());
        direction.setDirector(this.userService.getUserById(directionRequest.getDirector()));
        direction.setCodirector(this.userService.getUserById(directionRequest.getCodirector()));
        direction.setCompliancePercentage(directionRequest.getCompliancePercentage());
        direction.setFormat(this.formatServiceSec.findByIdToRelations(directionRequest.getFormatId()));
        direction.setCreatedBy(this.userService.getUserById(user.getId()));
        this.directionRepository.save(direction);
    }

    public List<DirectionDto> findByFormatId(Long formatId, User user) {
        List<DirectionDto> directionDtos = new ArrayList<>();
        List<Direction> directions = this.directionRepository.findByFormatIdAndCreatedBy(formatId, user);
        List<Direction> otherDirections = this.directionRepository.findByFormatId(formatId).stream()
                .filter(direction -> !direction.getCreatedBy().equals(user))
                .toList();
        directionDtos.addAll(directions.stream().map(DirectionDto::new).toList());
        directionDtos.addAll(otherDirections.stream().map(DirectionDto::new).toList());
        directionDtos.sort(Comparator.comparing(DirectionDto::getId));
        return directionDtos;
    }
}
