package com.event_website.Controller;

import com.event_website.Dto.SlotTicketTypeCapacityDTO;
import com.event_website.Dto.ErrorDTO;
import com.event_website.Entity.SlotTicketTypeCapacity;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Service.SlotTicketTypeCapacityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capacities")
@Tag(name = "Slot-TicketType Capacities", description = "Manage seat capacities for ticket types within slots")
public class SlotTicketTypeCapacityController {

    @Autowired
    private SlotTicketTypeCapacityService capacityService;

    @Operation(
            summary = "Create capacity for a slot and ticket type",
            description = "Sets the number of available seats for a specific ticket type within a specific slot.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Capacity created successfully",
                            content = @Content(schema = @Schema(implementation = SlotTicketTypeCapacityDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Slot or Ticket Type not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @PostMapping
    public ResponseEntity<SlotTicketTypeCapacityDTO> createCapacity(
            @RequestParam Integer slotId,
            @RequestParam Integer ticketTypeId,
            @RequestParam Integer capacity) {

        SlotTicketTypeCapacity saved = capacityService.createCapacity(slotId, ticketTypeId, capacity);
        return ResponseEntity.ok(SlotTicketTypeCapacityDTO.fromEntity(saved));
    }

    @Operation(
            summary = "Get all capacities by slot",
            description = "Returns all ticket type capacities for the specified slot ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Capacities retrieved successfully",
                            content = @Content(schema = @Schema(implementation = SlotTicketTypeCapacityDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Slot not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @GetMapping("/slot/{slotId}")
    public ResponseEntity<List<SlotTicketTypeCapacityDTO>> getBySlot(@PathVariable Integer slotId) {
        List<SlotTicketTypeCapacityDTO> result = capacityService.getBySlotId(slotId).stream()
                .map(SlotTicketTypeCapacityDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Get capacity for a specific slot and ticket type",
            description = "Returns the capacity for a specific ticket type within a given slot.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Capacity retrieved successfully",
                            content = @Content(schema = @Schema(implementation = SlotTicketTypeCapacityDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Capacity not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @GetMapping
    public ResponseEntity<SlotTicketTypeCapacityDTO> getBySlotAndTicketType(
            @RequestParam Integer slotId,
            @RequestParam Integer ticketTypeId) {

        SlotTicketTypeCapacity capacity = capacityService
                .getBySlotAndTicketType(slotId, ticketTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Capacity not found"));

        return ResponseEntity.ok(SlotTicketTypeCapacityDTO.fromEntity(capacity));
    }
}
