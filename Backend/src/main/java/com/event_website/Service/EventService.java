package com.event_website.Service;

import com.event_website.Dto.EventDtoDetalis;
import com.event_website.Entity.Event;
import com.event_website.Entity.TicketType;
import com.event_website.Repository.EventRepo;
import com.event_website.Repository.TicketTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;
    private final SlotTicketTypeCapacityService slotCapacityService;

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
                .map(event -> {
                    List<BigDecimal> prices = slotCapacityService.getTicketTypesByEventId(event.getId())
                            .stream()
                            .map(TicketType::getPrice)
                            .collect(Collectors.toList());

                    String locationName = event.getLocation() != null ? event.getLocation().getName() : null;

                    return new EventDtoDetalis(
                            event.getId(),
                            event.getName(),
                            event.getDescription(),
                            event.getPhotoUrl(),
                            event.getCategory() != null ? event.getCategory().getName() : null,
                            prices,
                            locationName
                    );
                });
    }

    public EventDtoDetalis getEventDetails(Integer eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<TicketType> ticketTypes = slotCapacityService.getTicketTypesByEventId(eventId);

        List<BigDecimal> prices = ticketTypes.stream()
                .map(TicketType::getPrice)
                .collect(Collectors.toList());

        String locationName = event.getLocation() != null ? event.getLocation().getName() : null;

        return new EventDtoDetalis(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getPhotoUrl(),
                event.getCategory() != null ? event.getCategory().getName() : null,
                prices,
                locationName
        );
    }

    public Page<EventDtoDetalis> getAllEventDetails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return eventRepo.findAll(pageable)
                .map(event -> {
                    List<BigDecimal> prices = slotCapacityService.getTicketTypesByEventId(event.getId())
                            .stream()
                            .map(TicketType::getPrice)
                            .collect(Collectors.toList());

                    String locationName = event.getLocation() != null ? event.getLocation().getName() : null;

                    return new EventDtoDetalis(
                            event.getId(),
                            event.getName(),
                            event.getDescription(),
                            event.getPhotoUrl(),
                            event.getCategory() != null ? event.getCategory().getName() : null,
                            prices,
                            locationName
                    );
                });
    }
}
