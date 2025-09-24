package com.event_website.Controller;

import com.event_website.Dto.*;
import com.event_website.Request.CompositeCreateEvent;
import com.event_website.Request.CompositeCreateCapacity;
import com.event_website.Service.CompositeEventService;
import com.event_website.Service.EventService;
import com.event_website.Repository.CategoryRepo;
import com.event_website.Dto.EventDtoDetalis;
import com.event_website.Dto.DetailedEventDto;
import com.event_website.Dto.ErrorDTO;
import com.event_website.Entity.Event;
import com.event_website.Entity.Category;
import com.event_website.Logging.LogRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Event")
@RequiredArgsConstructor
@Tag(name = "Events", description = "APIs for creating, updating, fetching, and deleting events and composite event operations")
public class EventController {

    private final EventService eventService;
    private final CategoryRepo categoryRepo;
    private final CompositeEventService compositeEventService;

    @Operation(
            summary = "Create a new event",
            description = "Creates a simple event with the given data. Category is optional if categoryId is set.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event created successfully",
                            content = @Content(schema = @Schema(implementation = EventDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request (e.g., category not found or invalid body)",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<EventDto> create(
            @Parameter(description = "Event payload") @RequestBody EventDto dto
    ) {
        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }
        Event saved = eventService.save(dto.toEntity(category));
        return ResponseEntity.ok(EventDto.fromEntity(saved));
    }

    @Operation(
            summary = "Update an existing event by ID",
            description = "Updates properties of an event. Provides partial or full updates based on supplied EventDto.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event updated successfully",
                            content = @Content(schema = @Schema(implementation = EventDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request (e.g., invalid payload)",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(
            @Parameter(description = "ID of the event to update") @PathVariable Integer id,
            @Parameter(description = "Updated event data") @RequestBody EventDto dto
    ) {
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

    @Operation(
            summary = "Get all events (paginated)",
            description = "Returns a paginated list of events.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Events page retrieved successfully",
                            content = @Content(schema = @Schema(implementation = EventDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request (invalid paging params)",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<Object> getAll(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size
    ) {
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

    @Operation(
            summary = "Search events by keyword and categories (paginated)",
            description = "Searches events optionally by keyword and category IDs, with paging.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Search results retrieved successfully",
                            content = @Content(schema = @Schema(implementation = EventDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request (invalid query parameters)",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @GetMapping("/search")
    public ResponseEntity<Object> searchEvents(
            @Parameter(description = "Search keyword for event name or description") @RequestParam(required = false) String keyword,
            @Parameter(description = "List of category IDs to filter by") @RequestParam(required = false) List<Integer> categoryIds,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size
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

    @Operation(
            summary = "Delete an event by ID",
            description = "Deletes the event identified by the given ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Event deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the event to delete") @PathVariable Integer id
    ) {
        if (eventService.findEventbyId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Create composite event",
            description = "Creates an event with slots and ticket type capacities in one request.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Composite event created successfully",
                            content = @Content(schema = @Schema(implementation = CreateCompositeEventDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request (invalid data)",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @PostMapping("/create-composite")
    public ResponseEntity<CreateCompositeEventDTO> createComposite(
            @Parameter(description = "Composite event creation request payload") @RequestBody CompositeCreateEvent request
    ) {
        CreateCompositeEventDTO dto = compositeEventService.createCompositeEvent(request);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Get a composite event by ID",
            description = "Fetches the composite event with its slots and ticket type capacities by ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Composite event retrieved successfully",
                            content = @Content(schema = @Schema(implementation = CompositeCreateEvent.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Composite event not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @GetMapping("/get-composite/{id}")
    public ResponseEntity<CompositeCreateEvent> getComposite(
            @Parameter(description = "ID of composite event to retrieve") @PathVariable int id
    ) {
        CompositeCreateEvent dto = compositeEventService.getComposite(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Update composite event",
            description = "Updates a composite event identified by ID, including its slots and capacities.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Composite event updated successfully",
                            content = @Content(schema = @Schema(implementation = CompositeCreateEvent.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request (invalid payload or missing data)",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Composite event not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @PutMapping("/update-composite/{id}")
    public ResponseEntity<CompositeCreateEvent> updateComposite(
            @Parameter(description = "ID of composite event to update") @PathVariable int id,
            @Parameter(description = "Updated composite event data") @RequestBody CompositeCreateEvent request
    ) {
        CompositeCreateEvent dto = compositeEventService.updateCompositeEvent(id, request);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Get all composite events",
            description = "Fetches all composite events.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Composite events retrieved successfully",
                            content = @Content(schema = @Schema(implementation = DetailedEventDto.class))
                    )
            }
    )
    @GetMapping("/get-composite")
    public ResponseEntity<List<DetailedEventDto>> getAllComposite() {
        List<DetailedEventDto> dto = compositeEventService.getAllComposite();
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Get event details by ID",
            description = "Fetch detailed information about an event, including ticket prices, category, etc.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event details retrieved successfully",
                            content = @Content(schema = @Schema(implementation = EventDtoDetalis.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @GetMapping("/{id}/details")
    public ResponseEntity<EventDtoDetalis> getEventDetails(
            @Parameter(description = "ID of the event to fetch details for") @PathVariable Integer id
    ) {
        EventDtoDetalis eventDetails = eventService.getEventDetails(id);
        return ResponseEntity.ok(eventDetails);
    }

    @Operation(
            summary = "Get all event details (paginated)",
            description = "Returns detailed event information for all events, paginated.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Detailed event data retrieved successfully",
                            content = @Content(schema = @Schema(implementation = EventDtoDetalis.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request (invalid parameters)",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @GetMapping("/details")
    public ResponseEntity<Page<EventDtoDetalis>> getAllEventDetails(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Size of page") @RequestParam(defaultValue = "10") int size
    ) {
        Page<EventDtoDetalis> events = eventService.getAllEventDetails(page, size);
        return ResponseEntity.ok(events);
    }

    @Operation(
            summary = "Get event by ID",
            description = "Fetches an event summary by its ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event summary retrieved successfully",
                            content = @Content(schema = @Schema(implementation = EventDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById(
            @Parameter(description = "ID of the event to retrieve") @PathVariable Integer id
    ){
        return eventService.findEventbyId(id)
                .map(EventDto::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
