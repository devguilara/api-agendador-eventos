package com.devguilara.api.cadastroeventos.service;


import com.devguilara.api.cadastroeventos.model.Event;
import com.devguilara.api.cadastroeventos.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event addEvent(Event event){
        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "-"));
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents(){
        return (List<Event>)eventRepository.findAll();
    }

    public Event getEventByPrettyName(String prettyName){
        return eventRepository.findByPrettyName(prettyName);
    }
}
