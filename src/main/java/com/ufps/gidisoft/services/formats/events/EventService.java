package com.ufps.gidisoft.services.formats.events;

import com.ufps.gidisoft.entities.formats.events.Event;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.enums.roles.RolesEnum;
import com.ufps.gidisoft.repositories.formats.events.EventRepository;
import com.ufps.gidisoft.requests.formats.EventRequest;
import com.ufps.gidisoft.responses.format.EventDto;
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
public class EventService {

    /*
     * Repositories
     */
    private final EventRepository eventRepository;

    /*
     * Services
     */
    private final UserService userService;
    private final FormatServiceSec formatServiceSec;
    private final CloudinaryService cloudinaryService;
    private final EventUserService eventUserService;

    public Event findById(Long id) {
        return this.eventRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException(ExceptionCodeEnum.EVENT01.getMessage()));
    }

    @Transactional
    public void createEvent(EventRequest eventRequest, User user) throws IOException {
        Event event = new Event();
        event.setName(eventRequest.getName());
        event.setCreatedAt(eventRequest.getCreatedAt());
        event.setCompliancePercentage(eventRequest.getCompliancePercentage());
        event.setFormat(this.formatServiceSec.findByIdToRelations(eventRequest.getFormatId()));
        event.setCreatedBy(user);
        this.getFilesNameList(eventRequest, event);
        this.eventRepository.save(event);
        this.eventUserService.createEventUser(event, user);
    }

    @Transactional
    public void updateEvent(EventRequest eventRequest, User user) throws IOException {
        Event event = findById(eventRequest.getId());
        if (this.eventUserService.validateExistEventAndUser(event, user) ||
                user.getRole().getId().equals(RolesEnum.ADMIN.getId())) {
            event.setName(eventRequest.getName());
            event.setCreatedAt(eventRequest.getCreatedAt());
            event.setCompliancePercentage(eventRequest.getCompliancePercentage());
            getFilesNameList(eventRequest, event);
            this.eventRepository.save(event);
        } else throw new IllegalArgumentException(ExceptionCodeEnum.EVENT02.getMessage());
    }

    private void getFilesNameList(EventRequest eventRequest, Event event) throws IOException {
        if (eventRequest.getFiles() != null && !eventRequest.getFiles().isEmpty()) {
            List<String> files = new ArrayList<>();
            if (event.getFiles() != null && !event.getFiles().isEmpty()) {
                files = event.getFiles();
            }
            for (MultipartFile file : eventRequest.getFiles()) {
                files.add(cloudinaryService.upload(file, "projects"));
            }
            event.setFiles(files);
        }
    }

    public List<EventDto> findByFormatId(Long formatId) {
        List<EventDto> eventDtos = new ArrayList<>();
        for (Event event : this.eventRepository.findByFormatId(formatId)) {
            eventDtos.add(new EventDto(event, this.eventUserService.findUsersByEvent(event.getId())));
        }
        eventDtos.sort(Comparator.comparing(EventDto::getId));
        return eventDtos;
    }

    @Transactional
    public void deleteById(Long id) throws Exception {
        this.eventUserService.deleteByEvent(id);
        Event event = this.findById(id);
        if (event.getFiles() != null && !event.getFiles().isEmpty()) {
            for (String file : event.getFiles()) {
                this.cloudinaryService.getImage(file);
            }
        }
        this.eventRepository.deleteById(id);
    }

    public void deleteEvidence(Long eventId, String url) throws Exception {
        Event event = this.findById(eventId);
        this.cloudinaryService.getImage(url);

        List<String> files = event.getFiles();
        files.removeIf(file -> file.trim().equalsIgnoreCase(url.trim()));
        if (files.isEmpty()) event.setFiles(null);
        else event.setFiles(files);

        this.eventRepository.save(event);
    }

    public boolean validateEventWithUser(Long eventId, User user) {
        return this.eventUserService.validateExistEventAndUser(this.findById(eventId), user);
    }

    @Transactional
    public void createRelationWithUsers(List<Long> users, Long eventId) {
        users.forEach(user -> {
            Event event = this.findById(eventId);
            if (!this.validateEventWithUser(eventId, this.userService.getUserById(user))) {
                this.eventUserService.createEventUser(event, this.userService.getUserById(user));
            }
        });
    }
}
