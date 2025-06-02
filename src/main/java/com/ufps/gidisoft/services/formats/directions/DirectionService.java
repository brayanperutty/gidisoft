package com.ufps.gidisoft.services.formats.directions;

import com.ufps.gidisoft.entities.formats.directions.Direction;
import com.ufps.gidisoft.entities.formats.projects.Project;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.enums.roles.RolesEnum;
import com.ufps.gidisoft.repositories.formats.directions.DirectionRepository;
import com.ufps.gidisoft.requests.formats.DirectionRequest;
import com.ufps.gidisoft.responses.format.DirectionDto;
import com.ufps.gidisoft.services.cloudinary.CloudinaryService;
import com.ufps.gidisoft.services.formats.general.FormatServiceSec;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
    private final DirectionUserService directionUserService;
    private final CloudinaryService cloudinaryService;

    public Direction findById(Long id) {
        return directionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(ExceptionCodeEnum.DIR01.getMessage()));
    }

    @Transactional
    public void createDirection(DirectionRequest directionRequest, User user) throws IOException {
        Direction direction = new Direction();
        direction.setName(directionRequest.getName());
        direction.setDirector(this.userService.getUserById(directionRequest.getDirector()));
        direction.setCodirector(this.userService.getUserById(directionRequest.getCodirector()));
        direction.setCompliancePercentage(directionRequest.getCompliancePercentage());
        direction.setFormat(this.formatServiceSec.findByIdToRelations(directionRequest.getFormatId()));
        direction.setCreatedBy(this.userService.getUserById(user.getId()));
        getFilesNameList(directionRequest, direction);
        this.directionRepository.save(direction);
        if (!Objects.equals(directionRequest.getDirector(), user.getId())) {
            this.directionUserService.createDirectionUser(direction, user);
        }
        this.directionUserService.createDirectionUser(direction, this.userService.getUserById(directionRequest.getDirector()));
        this.directionUserService.createDirectionUser(direction, this.userService.getUserById(directionRequest.getCodirector()));
    }

    @Transactional
    public void updateDirection(DirectionRequest directionRequest, User user) throws IOException {
        Direction direction = this.findById(directionRequest.getId());
        if(this.directionUserService.validateDirectionUser(direction, user) ||
                user.getRole().getId().equals(RolesEnum.ADMIN.getId())){
            direction.setName(directionRequest.getName());
            direction.setCompliancePercentage(directionRequest.getCompliancePercentage());
            getFilesNameList(directionRequest, direction);
            this.validateSameDirectorAndCodirector(directionRequest, direction);
            direction.setDirector(this.userService.getUserById(directionRequest.getDirector()));
            direction.setCodirector(this.userService.getUserById(directionRequest.getCodirector()));
            this.directionRepository.save(direction);
        }
    }

     @Transactional
    public void validateSameDirectorAndCodirector(DirectionRequest directionRequest, Direction direction) {
        if(!direction.getDirector().getId().equals(directionRequest.getDirector())){
            this.directionUserService.deleteByDirectionIdAndUserId(direction.getId(), direction.getDirector().getId());
            this.directionUserService.createDirectionUser(direction, this.userService.getUserById(directionRequest.getDirector()));
        }

        if(!direction.getCodirector().getId().equals(directionRequest.getCodirector())){
            this.directionUserService.deleteByDirectionIdAndUserId(direction.getId(), direction.getCodirector().getId());
            this.directionUserService.createDirectionUser(direction, this.userService.getUserById(directionRequest.getCodirector()));
        }
    }

    public void getFilesNameList(DirectionRequest directionRequest, Direction direction) throws IOException {
        if(directionRequest.getFiles() != null && !directionRequest.getFiles().isEmpty()) {
            List<String> fileNames = new ArrayList<>();
            if (direction.getFiles() != null && !direction.getFiles().isEmpty()) {
                fileNames = direction.getFiles();
            }

            for (MultipartFile file : directionRequest.getFiles()) {
                fileNames.add(cloudinaryService.upload(file, "directions"));
            }
            direction.setFiles(fileNames);
        }
    }

    public List<DirectionDto> findByFormatId(Long formatId) {
        List<DirectionDto> directionDtos = new ArrayList<>();
        for (Direction direction : this.directionRepository.findByFormatId(formatId)) {
            directionDtos.add(new DirectionDto(direction, this.directionUserService.findUsersByDirection(direction.getId())));
        }
        directionDtos.sort(Comparator.comparing(DirectionDto::getId));
        return directionDtos;
    }

    public boolean validateDirectionWithUser(Long directionId, User user) {
        return this.directionUserService.validateDirectionUser(this.directionRepository.findById(directionId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionCodeEnum.DIR01.getMessage())), user);
    }

    @Transactional
    public void deleteById(Long directionId) throws Exception {
        Direction direction = this.findById(directionId);
        if (direction.getFiles() != null && !direction.getFiles().isEmpty()) {
            for (String file : direction.getFiles()) {
                this.cloudinaryService.getImage(file);
            }
        }
        this.directionUserService.deleteByDirectionId(directionId);
        this.directionRepository.deleteById(directionId);
    }

    public void createRelationsWithUsers(List<Long> users, Long directionId) {
        users.forEach(user -> {
            Direction direction = this.directionRepository.findById(directionId).orElseThrow(()
                    -> new IllegalArgumentException(ExceptionCodeEnum.PROJ01.getMessage()));
            this.directionUserService.createDirectionUser(direction, this.userService.getUserById(user));
        });
    }

    @Transactional
    public void deleteEvidence(Long projectId, String url) throws Exception {
        Direction project = this.findById(projectId);

        this.cloudinaryService.getImage(url);

        List<String> files = project.getFiles();
        files.removeIf(fileUrl -> fileUrl.trim().equalsIgnoreCase(url.trim()));
        if (files.isEmpty()) project.setFiles(null);
        else project.setFiles(files);

        this.directionRepository.save(project);
    }
}
