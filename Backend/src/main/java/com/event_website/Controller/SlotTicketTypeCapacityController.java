package com.event_website.Controller;

import com.event_website.Dto.SlotTicketTypeCapacityDTO;
import com.event_website.Entity.SlotTicketTypeCapacity;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Service.SlotTicketTypeCapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capacities")
public class SlotTicketTypeCapacityController {

    @Autowired
    private SlotTicketTypeCapacityService capacityService;

    @PostMapping
    public ResponseEntity<SlotTicketTypeCapacityDTO> createCapacity(@RequestParam Integer slotId,
                                                                    @RequestParam Integer ticketTypeId,
                                                                    @RequestParam Integer capacity) {
        SlotTicketTypeCapacity saved = capacityService.createCapacity(slotId, ticketTypeId, capacity);
        return ResponseEntity.ok(SlotTicketTypeCapacityDTO.fromEntity(saved));
    }

    @GetMapping("/slot/{slotId}")
    public ResponseEntity<List<SlotTicketTypeCapacityDTO>> getBySlot(@PathVariable Integer slotId) {
        List<SlotTicketTypeCapacityDTO> result = capacityService.getBySlotId(slotId).stream()
                .map(SlotTicketTypeCapacityDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<SlotTicketTypeCapacityDTO> getBySlotAndTicketType(@RequestParam Integer slotId,
                                                                            @RequestParam Integer ticketTypeId) {
        SlotTicketTypeCapacity capacity = capacityService.getBySlotAndTicketType(slotId, ticketTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Capacity not found"));
        return ResponseEntity.ok(SlotTicketTypeCapacityDTO.fromEntity(capacity));
    }
}

