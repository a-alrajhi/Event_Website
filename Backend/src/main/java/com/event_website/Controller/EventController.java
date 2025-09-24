package com.event_website.Controller;

import com.event_website.Dto.*;
import com.event_website.Dto.EventDtoDetalis;
import com.event_website.Entity.*;
import com.event_website.Repository.CategoryRepo;
import com.event_website.Request.CompositeCreateCapacity;
import com.event_website.Request.CompositeCreateEvent;
import com.event_website.Request.CreateCategoryRequest;
import com.event_website.Service.*;
import com.event_website.Logging.LogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final CategoryRepo categoryRepo;
    private final CompositeEventService compositeEventService;

    @LogRequest(description = "Create new event")
    @PostMapping
    public ResponseEntity<EventDto> create(@RequestBody EventDto dto) {
        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }
        Event saved = eventService.save(dto.toEntity(category));
        return ResponseEntity.ok(EventDto.fromEntity(saved));
    }

    @LogRequest(description = "Update event by ID")
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(
            @PathVariable Integer id,
            @RequestBody EventDto dto) {

        return eventService.findEventbyId(id)
                .map(existing -> {
                    Category category = null;
                    if (dto.getCategoryId() != null) {
                        category = categoryRepo.findById(dto.getCategoryId())
                                .orElseThrow(() -> new RuntimeException("Category not found"));
                    }

                    existing.setName(dto.getName());
                    existing.setArName(dto.getArName());
                    existing.setDescription(dto.getDescription());
                    existing.setArDescription(dto.getArDescription());
                    existing.setPhotoUrl(dto.getPhotoUrl());
                    existing.setHasAssignedSeating(dto.getHasAssignedSeating());
                    existing.setCategory(category);

                    Event updated = eventService.save(existing);
                    return ResponseEntity.ok(EventDto.fromEntity(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @LogRequest(description = "Get all events with pagination")
    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<EventDto> eventsPage = eventService.findAllEvent(page, size)
                .map(EventDto::fromEntity);

        var response = new java.util.HashMap<String, Object>();
        response.put("content", eventsPage.getContent());
        response.put("page", eventsPage.getNumber());
        response.put("size", eventsPage.getSize());
        response.put("totalElements", eventsPage.getTotalElements());
        response.put("totalPages", eventsPage.getTotalPages());
        response.put("last", eventsPage.isLast());

        return ResponseEntity.ok(response);
    }

    @LogRequest(description = "Search events by keyword and categories")
    @GetMapping("/search")
    public ResponseEntity<Object> searchEvents(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) List<Integer> categoryIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<EventDto> eventsPage = eventService.searchEvents(keyword, categoryIds, page, size)
                .map(EventDto::fromEntity);

        var response = new java.util.HashMap<String, Object>();
        response.put("content", eventsPage.getContent());
        response.put("page", eventsPage.getNumber());
        response.put("size", eventsPage.getSize());
        response.put("totalElements", eventsPage.getTotalElements());
        response.put("totalPages", eventsPage.getTotalPages());
        response.put("last", eventsPage.isLast());

        return ResponseEntity.ok(response);
    }

    @LogRequest(description = "Delete event by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (eventService.findEventbyId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @LogRequest(description = "Create Composite Event")
    @PostMapping("/create-composite")
    public ResponseEntity<CreateCompositeEventDTO> createComposite(@RequestBody CompositeCreateEvent request) {
        CreateCompositeEventDTO dto = compositeEventService.createCompositeEvent(request);
        return ResponseEntity.ok(dto);
    }

    @LogRequest(description = "Load Composite Event")
    @GetMapping("/get-composite/{id}")
    public ResponseEntity<CompositeCreateEvent> getComposite(@PathVariable int id) {
        CompositeCreateEvent dto = compositeEventService.getComposite(id);
        return ResponseEntity.ok(dto);
    }

    @LogRequest(description = "Update Composite Event")
    @PutMapping("/update-composite/{id}")
    public ResponseEntity<CompositeCreateEvent> updateComposite(
            @PathVariable int id,
            @RequestBody CompositeCreateEvent request
    ) {
        CompositeCreateEvent dto = compositeEventService.updateCompositeEvent(id, request);
        return ResponseEntity.ok(dto);
    }

    @LogRequest(description = "Get all Events composite")
    @GetMapping("/get-composite")
    public ResponseEntity<List<DetailedEventDto>> getAllComposite() {
        List<DetailedEventDto> dto = compositeEventService.getAllComposite();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<EventDtoDetalis> getEventDetails(@PathVariable Integer id) {
        EventDtoDetalis eventDetails = eventService.getEventDetails(id);
        return ResponseEntity.ok(eventDetails);
    }

    @GetMapping("/details")
    public ResponseEntity<Page<EventDtoDetalis>> getAllEventDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<EventDtoDetalis> events = eventService.getAllEventDetails(page, size);
        return ResponseEntity.ok(events);
    }

    @LogRequest(description = "Get event by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById(@PathVariable Integer id){
        return eventService.findEventbyId(id)
                .map(EventDto::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
