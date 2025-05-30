package com.ufps.gidisoft.services.formats.events;

import com.ufps.gidisoft.entities.formats.events.Event;
import com.ufps.gidisoft.entities.formats.events.EventUser;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.repositories.formats.events.EventUserReopository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventUserService {

    /*
     * Repositories
     */
    private final EventUserReopository eventUserRepository;

    public void createEventUser(Event event, User user) {
        this.eventUserRepository.save(new EventUser(event, user));
    }

    public void deleteByEvent(Long eventId) {
        this.eventUserRepository.deleteByEventId(eventId);
    }

    public boolean validateExistEventAndUser(Event event, User user) {
        return this.eventUserRepository.existsByEventAndUser(event, user);
    }

    public List<String> findUsersByEvent(Long eventId) {
        List<String> users = new ArrayList<>();
        this.eventUserRepository.findByEventId(eventId).forEach(eventUser ->
            users.add(eventUser.getUser().getName())
        );
        return users;
    }
}
