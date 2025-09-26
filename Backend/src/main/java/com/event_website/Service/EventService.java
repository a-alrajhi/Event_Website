package com.event_website.Service;

import com.event_website.Dto.EventDtoDetalis;
import com.event_website.Entity.Event;
import com.event_website.Entity.Slot;
import com.event_website.Entity.TicketType;
import com.event_website.Repository.EventRepo;
import com.event_website.Repository.TicketTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;
    private final SlotTicketTypeCapacityService slotCapacityService;
    private final SlotService slotService;
    private final TicketService ticketService;

    public Event save(Event event) {
        return eventRepo.save(event);
    }

    public Optional<Event> findEventbyId(Integer id) {
        return eventRepo.findById(id);
    }

    public void deleteById(Integer id) {
        eventRepo.deleteById(id);
    }

    public Page<Event> findAllEvent(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventRepo.findAll(pageable);
    }

    public Page<Event> searchEvents(String keyword, List<Integer> categoryIds, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (keyword != null && (categoryIds == null || categoryIds.isEmpty())) {
            return eventRepo.findByNameContainingIgnoreCaseOrArNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrArDescriptionContainingIgnoreCase(
                    keyword, keyword, keyword, keyword, pageable
            );
        }

        if ((keyword == null || keyword.isEmpty()) && categoryIds != null && !categoryIds.isEmpty()) {
            return eventRepo.findByCategoryIdIn(categoryIds, pageable);
        }

        if (keyword != null && categoryIds != null && !categoryIds.isEmpty()) {
            return eventRepo.findByCategoryIdInAndNameContainingIgnoreCaseOrCategoryIdInAndArNameContainingIgnoreCaseOrCategoryIdInAndDescriptionContainingIgnoreCaseOrCategoryIdInAndArDescriptionContainingIgnoreCase(
                    categoryIds, keyword,
                    categoryIds, keyword,
                    categoryIds, keyword,
                    categoryIds, keyword,
                    pageable
            );
        }

        return eventRepo.findAll(pageable);
    }

    // Get events by location ID
    public Page<EventDtoDetalis> getEventsByLocationId(Integer locationId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return eventRepo.findByLocation_Id(locationId, pageable)
                .map(this::getEventDetails);
    }

    public EventDtoDetalis getEventDetails(Integer eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return getEventDetails(event);
    }

    public Page<EventDtoDetalis> getAllEventDetails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return eventRepo.findAll(pageable)
                .map(this::getEventDetails);
    }

    public EventDtoDetalis getEventDetails(Event event) {
        EventDtoDetalis eventDtoDetalis = new EventDtoDetalis();

        List<TicketType> ticketTypes = slotCapacityService.getTicketTypesByEventId(event.getId());
        Integer attendees = ticketService.getEventTickets(event.getId()).size();
        List<Slot> AllSlots = slotService.findByEventId(event.getId());
        Slot firstSlot = AllSlots.stream().min(Comparator.comparing(Slot::getDate)).orElse(null);
        LocalTime startTimeLoc = firstSlot != null ? firstSlot.getStartTime() : null;
        String startTime = startTimeLoc != null ? startTimeLoc.format(DateTimeFormatter.ofPattern("HH:mm")) : null;
        String locationName = event.getLocation() != null ? event.getLocation().getName() : null;
        eventDtoDetalis.setId(event.getId());
        eventDtoDetalis.setName(event.getName());
        eventDtoDetalis.setDescription(event.getDescription());
        eventDtoDetalis.setPhotoUrl(event.getPhotoUrl());
        eventDtoDetalis.setDate(firstSlot != null ? firstSlot.getDate() : null);
        eventDtoDetalis.setDates(AllSlots.stream().map(Slot::getDate).toList());
        eventDtoDetalis.setTime(startTime);
        eventDtoDetalis.setLocationName(locationName);
        eventDtoDetalis.setCategoryName(event.getCategory() != null ? event.getCategory().getName() : null);
        eventDtoDetalis.setPrices(ticketTypes.stream().map(TicketType::getPrice).collect(Collectors.toList()));
        eventDtoDetalis.setAttendees(attendees);
        eventDtoDetalis.setRemaining( firstSlot != null && !ticketTypes.isEmpty() ?
                slotCapacityService.getRemainingCapacity(firstSlot.getId(), ticketTypes.getFirst().getId()) : null);
        return eventDtoDetalis;
    }
}
