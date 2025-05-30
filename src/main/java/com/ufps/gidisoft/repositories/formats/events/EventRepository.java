package com.ufps.gidisoft.repositories.formats.events;

import com.ufps.gidisoft.entities.formats.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByFormatId(Long formatId);
}
