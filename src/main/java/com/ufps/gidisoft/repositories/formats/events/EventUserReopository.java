package com.ufps.gidisoft.repositories.formats.events;

import com.ufps.gidisoft.entities.formats.events.Event;
import com.ufps.gidisoft.entities.formats.events.EventUser;
import com.ufps.gidisoft.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventUserReopository extends JpaRepository<EventUser, Integer> {
    void deleteByEventId(Long eventId);

    boolean existsByEventAndUser(Event event, User user);

    List<EventUser> findByEventId(Long eventId);
}
