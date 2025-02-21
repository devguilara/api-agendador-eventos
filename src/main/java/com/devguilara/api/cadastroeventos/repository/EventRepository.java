package com.devguilara.api.cadastroeventos.repository;

import com.devguilara.api.cadastroeventos.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
    public Event findByPrettyName(String prettyName);
}
