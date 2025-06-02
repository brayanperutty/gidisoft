package com.ufps.gidisoft.services.formats.others;

import com.ufps.gidisoft.entities.formats.events.Event;
import com.ufps.gidisoft.entities.formats.others.OtherActivity;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.repositories.formats.others.OtherActivityRepository;
import com.ufps.gidisoft.requests.formats.EventRequest;
import com.ufps.gidisoft.requests.formats.OtherActivityRequest;
import com.ufps.gidisoft.responses.format.OtherActivityDto;
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

@Service
@RequiredArgsConstructor
public class OtherActivityService {

    /*
     * Repositories
     */
    private final OtherActivityRepository otherActivityRepository;

    /*
     * Services
     */
    private final FormatServiceSec formatServiceSec;
    private final CloudinaryService cloudinaryService;
    private final OtherActivityUserService otherActivityUserService;
    private final UserService userService;

    public OtherActivity findById(Long id){
        return this.otherActivityRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException(ExceptionCodeEnum.OTHER01.getMessage()));
    }

    @Transactional
    public void createOtherActivity(OtherActivityRequest otherActivityRequest, User user) throws IOException {
        OtherActivity otherActivity = new OtherActivity();
        otherActivity.setName(otherActivityRequest.getName());
        otherActivity.setType(otherActivityRequest.getType());
        otherActivity.setCompliancePercentage(otherActivityRequest.getCompliancePercentage());
        otherActivity.setCreatedBy(user);
        otherActivity.setFormat(this.formatServiceSec.findByIdToRelations(otherActivityRequest.getFormatId()));
        this.getFilesNameList(otherActivityRequest, otherActivity);
        this.otherActivityRepository.save(otherActivity);
        this.otherActivityUserService.createOtherActivityUser(otherActivity, user);
    }

    @Transactional
    public void updateOtherActivity(OtherActivityRequest otherActivityRequest, User user) throws IOException {
        OtherActivity otherActivity = this.findById(otherActivityRequest.getId());
        if (this.otherActivityUserService.validateExistOtherActivityAndUser(otherActivity, user)) {
            otherActivity.setName(otherActivityRequest.getName());
            otherActivity.setType(otherActivityRequest.getType());
            otherActivity.setCompliancePercentage(otherActivityRequest.getCompliancePercentage());
            getFilesNameList(otherActivityRequest, otherActivity);
            this.otherActivityRepository.save(otherActivity);
        }else throw new IllegalArgumentException(ExceptionCodeEnum.OTHER02.getMessage());
    }

    private void getFilesNameList(OtherActivityRequest otherActivityRequest, OtherActivity otherActivity) throws IOException {
        if (otherActivityRequest.getFiles() != null && !otherActivityRequest.getFiles().isEmpty()) {
            List<String> files = new ArrayList<>();
            if (otherActivity.getFiles() != null && !otherActivity.getFiles().isEmpty()) {
                files = otherActivity.getFiles();
            }
            for (MultipartFile file : otherActivityRequest.getFiles()) {
                files.add(cloudinaryService.upload(file, "projects"));
            }
            otherActivity.setFiles(files);
        }
    }

    public List<OtherActivityDto> findByFormatId(Long formatId){
        List<OtherActivityDto> otherActivities = new ArrayList<>();
        for (OtherActivity otherActivity : this.otherActivityRepository.findByFormatId(formatId)) {
            otherActivities.add(new OtherActivityDto(otherActivity, this.otherActivityUserService.findUserByOtherActivity(otherActivity.getId())));
        }
        otherActivities.sort(Comparator.comparing(OtherActivityDto::getId));
        return otherActivities;
    }

    @Transactional
    public void deleteById(Long id) throws Exception {
        this.otherActivityUserService.deleteByOtherActivityId(id);
        OtherActivity otherActivity = this.findById(id);
        if (otherActivity.getFiles() != null && !otherActivity.getFiles().isEmpty()) {
            for (String file : otherActivity.getFiles()) {
                this.cloudinaryService.getImage(file);
            }
        }
        this.otherActivityRepository.deleteById(id);
    }

    @Transactional
    public void deleteEvidence(Long otherActivityId, String url) throws Exception {
        OtherActivity otherActivity = this.findById(otherActivityId);
        this.cloudinaryService.getImage(url);

        List<String> files = otherActivity.getFiles();
        files.removeIf(file -> file.trim().equalsIgnoreCase(url.trim()));
        if (files.isEmpty()) otherActivity.setFiles(null);
        else otherActivity.setFiles(files);

        this.otherActivityRepository.save(otherActivity);
    }

    public boolean validateOtherActivityWithUser(Long otherActivityId, User user) {
        return this.otherActivityUserService.validateExistOtherActivityAndUser(this.findById(otherActivityId), user);
    }

    @Transactional
    public void createRelationWithUsers(List<Long> users, Long otherActivityId){
        users.forEach(user -> {
            OtherActivity otherActivity = this.findById(otherActivityId);
            if(!this.validateOtherActivityWithUser(otherActivityId, this.userService.getUserById(user))){
                this.otherActivityUserService.createOtherActivityUser(otherActivity, this.userService.getUserById(user));
            }
        });
    }
}
