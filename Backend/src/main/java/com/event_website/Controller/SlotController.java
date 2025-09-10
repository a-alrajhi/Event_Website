package com.event_website.Controller;

import com.event_website.Dto.EventDto;
import com.event_website.Dto.SlotDTO;
import com.event_website.Entity.Event;
import com.event_website.Entity.Slot;
import com.event_website.Entity.TicketType;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Repository.EventRepo;
import com.event_website.Repository.SlotRepo;
import com.event_website.Service.EventService;
import com.event_website.Service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/slots")
public class SlotController {
    @Autowired
    private SlotService slotService;
    @Autowired
    private EventService EventService;
    @Autowired
    private EventService eventService;

    /**
     * Get all slots for a specific event
     */
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<SlotDTO>> getSlotsByEvent(@PathVariable Integer eventId) {
        return ResponseEntity.ok(slotService.findByEventId(eventId).stream().map(SlotDTO::fromEntity).toList());
    }

    /**
     * Get all slots for a specific date (within DATE_DAYS_LIMIT)
     */
    @GetMapping("/by-date")
    public ResponseEntity<List<SlotDTO>> getSlotsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(slotService.findByDate(date).stream().map(SlotDTO::fromEntity).toList());
    }

    /**
     * Get all slots with pagination support
     */
    @GetMapping("/all")
    public ResponseEntity<List<SlotDTO>> getAllSlots(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(slotService.findAll(page, size).stream().map(SlotDTO::fromEntity).toList());
    }

    /**
     * Get a slot by its ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<SlotDTO> getSlotById(@PathVariable Integer id) {
        SlotDTO slotDTO = SlotDTO.fromEntity(slotService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Slot not found with id " + id)));
        return ResponseEntity.ok(slotDTO);
    }

    /**
     * Get all events that have slots on a specific date
     */
    @GetMapping("/events-by-date")
    public ResponseEntity<List<EventDto>> getEventsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(slotService.findEventsByDate(date).stream().map(EventDto::fromEntity).toList());
    }

    /**
     * Create a new slot
     */
    @PostMapping
    public ResponseEntity<SlotDTO> createSlot(@RequestBody SlotDTO slotRequest) {
        Slot slot = createSlotFromDTO(slotRequest);
        return ResponseEntity.ok(SlotDTO.fromEntity(slotService.save(slot)));
    }

    /**
     * Delete a slot by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Integer id) {
        slotService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all ticket types for a slot
     */
    @GetMapping("/{slotId}/ticket-types")
    public ResponseEntity<List<TicketType>> getTicketTypesBySlot(@PathVariable Integer slotId) {
        return ResponseEntity.ok(slotService.findAllTicketTypesPerSlot(slotId));
    }

    public Slot createSlotFromDTO(SlotDTO dto) {
        Event event = eventService.findById(dto.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + dto.getEventId()));
        Slot slot = new Slot();
        slot.setEvent(event);
        slot.setDate(dto.getDate());
        slot.setStartTime(dto.getStartTime());
        slot.setEndTime(dto.getEndTime());

        return slot;
    }

}
