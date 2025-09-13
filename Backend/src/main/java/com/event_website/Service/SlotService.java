package com.event_website.Service;

import com.event_website.Entity.Event;
import com.event_website.Entity.Slot;
import com.event_website.Entity.TicketType;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Repository.EventRepo;
import com.event_website.Repository.SlotRepo;
import com.event_website.Repository.TicketTypeRepo;
import com.event_website.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SlotService {
    @Autowired
    private SlotRepo slotRepo;
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private TicketTypeRepo ticketTypeRepo;

    public static final int DATE_DAYS_LIMIT = 14; // Edit this to allow for future events to appear

    public List<Slot> findByEventId(Integer id){ // returns all slots for a specific event
        if(eventRepo.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Event not found");
        }
        return slotRepo.findByEvent_Id(id);
    }

    public List<Slot> findByDate(LocalDate date){ // returns all slots for a specific date (assuming less than limit)
        long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), date);
        if (daysBetween > DATE_DAYS_LIMIT || daysBetween < 0) {
            return new ArrayList<>();
        }
        return slotRepo.findByDate(date);
    }

    public List<Slot> findAll(int page, int pageSize){ // all, with pagination support
        if(pageSize < 1 || page < 0){
            return slotRepo.findAll();
        }
        Pageable pageRequest = PageRequest.of(page, pageSize);
        return slotRepo.findAll(pageRequest).getContent();
    }

    public Optional<Slot> findById(Integer id){
        return slotRepo.findById(id);
    }

    public List<Event> findEventsByDate(LocalDate date){ // returns all events for a specific date
        List<Slot> slots = this.findByDate(date);
        List<Integer> eventIds = slots.stream().map(s -> s.getEvent().getId()).distinct().toList();
        return eventRepo.findAllById(eventIds);
    }

    public Slot save(Slot slot){
        return slotRepo.save(slot);
    }

    public void delete(int id){
        slotRepo.deleteById(id);
    }
}
