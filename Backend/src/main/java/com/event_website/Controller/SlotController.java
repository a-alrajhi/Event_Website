package com.event_website.Controller;

import com.event_website.Dto.EventDto;
import com.event_website.Dto.SlotDTO;
import com.event_website.Dto.ErrorDTO;
import com.event_website.Entity.Event;
import com.event_website.Entity.Slot;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Logging.LogRequest;
import com.event_website.Service.EventService;
import com.event_website.Service.SlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/slots")
@Tag(name = "Slots", description = "Endpoints for managing time slots for events")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @Autowired
    private EventService eventService;

    @Operation(
            summary = "Get all slots for an event",
            description = "Returns all slots for the specified event by event ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Slots retrieved successfully",
                            content = @Content(schema = @Schema(implementation = SlotDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @LogRequest(description = "Getting all slots for event")
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<SlotDTO>> getSlotsByEvent(@PathVariable Integer eventId) {
        return ResponseEntity.ok(
                slotService.findByEventId(eventId).stream().map(SlotDTO::fromEntity).toList()
        );
    }

    @Operation(
            summary = "Get slots by date",
            description = "Returns all slots available on a given date.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Slots retrieved successfully",
                            content = @Content(schema = @Schema(implementation = SlotDTO.class)))
            }
    )
    @LogRequest(description = "Getting all slots at date")
    @GetMapping("/by-date")
    public ResponseEntity<List<SlotDTO>> getSlotsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(
                slotService.findByDate(date).stream().map(SlotDTO::fromEntity).toList()
        );
    }

    @Operation(
            summary = "Get all slots (paginated)",
            description = "Returns a paginated list of all slots.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Slots retrieved successfully",
                            content = @Content(schema = @Schema(implementation = SlotDTO.class)))
            }
    )
    @LogRequest(description = "Getting all slots")
    @GetMapping("/all")
    public ResponseEntity<List<SlotDTO>> getAllSlots(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "0", required = false) int size) {
        return ResponseEntity.ok(
                slotService.findAll(page, size).stream().map(SlotDTO::fromEntity).toList()
        );
    }

    @Operation(
            summary = "Get slot by ID",
            description = "Returns a slot for a given ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Slot found",
                            content = @Content(schema = @Schema(implementation = SlotDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Slot not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @LogRequest(description = "Getting slot with id")
    @GetMapping("/by-id/{id}")
    public ResponseEntity<SlotDTO> getSlotById(@PathVariable Integer id) {
        SlotDTO slotDTO = SlotDTO.fromEntity(
                slotService.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Slot not found with id " + id))
        );
        return ResponseEntity.ok(slotDTO);
    }

    @Operation(
            summary = "Get all events by date",
            description = "Returns all events that have at least one slot on a given date.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Events retrieved successfully",
                            content = @Content(schema = @Schema(implementation = EventDto.class)))
            }
    )
    @LogRequest(description = "Getting all events at date")
    @GetMapping("/events-by-date")
    public ResponseEntity<List<EventDto>> getEventsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(
                slotService.findEventsByDate(date).stream().map(EventDto::fromEntity).toList()
        );
    }

    @Operation(
            summary = "Create a new slot",
            description = "Creates a new slot and associates it with an event.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Slot created successfully",
                            content = @Content(schema = @Schema(implementation = SlotDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @LogRequest(description = "Creating Slot")
    @PostMapping("/")
    public ResponseEntity<SlotDTO> createSlot(@RequestBody SlotDTO slotRequest) {
        Slot slot = createSlotFromDTO(slotRequest);
        return ResponseEntity.ok(SlotDTO.fromEntity(slotService.save(slot)));
    }

    @Operation(
            summary = "Delete slot by ID",
            description = "Deletes the slot with the given ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Slot deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Slot not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @LogRequest(description = "Deleting Slot")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Integer id) {
        slotService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Helper method to build Slot entity from DTO
    public Slot createSlotFromDTO(SlotDTO dto) {
        Event event = eventService.findEventbyId(dto.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + dto.getEventId()));
        Slot slot = new Slot();
        slot.setEvent(event);
        slot.setDate(dto.getDate());
        slot.setStartTime(dto.getStartTime());
        slot.setEndTime(dto.getEndTime());
        return slot;
    }
}
