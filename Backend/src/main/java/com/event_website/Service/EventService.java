package com.event_website.Service;


import com.event_website.Dto.EventDto;
import com.event_website.Entity.Event;
import com.event_website.Repository.EventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepository;



    public Event save(Event event){
        return eventRepository.save(event);
    }

    public Optional<Event> findEventbyId (Integer id) {
        return eventRepository.findById(id);

    }

    public Page<Event> findAllEvent (int page, int size){
        return eventRepository.findAll(PageRequest.of(page, size));
    }

    public void deleteById(Integer id){
        eventRepository.deleteById(id);
    }


}
