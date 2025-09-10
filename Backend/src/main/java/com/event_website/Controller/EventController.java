package com.event_website.Controller;

import com.event_website.Dto.EventDto;
import com.event_website.Entity.Category;
import com.event_website.Entity.Event;
import com.event_website.Repository.CategoryRepo;
import com.event_website.Service.EventService;
import com.event_website.Logging.LogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final CategoryRepo categoryRepo;

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

    @LogRequest(description = "Get event by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById(@PathVariable Integer id){
        return eventService.findEventbyId(id)
                .map(EventDto::fromEntity)
                .map(ResponseEntity::ok)
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
}
