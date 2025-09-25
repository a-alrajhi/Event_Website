package com.event_website.Controller;

import com.event_website.Dto.TicketTypeDTO;
import com.event_website.Dto.ErrorDTO;
import com.event_website.Entity.Slot;
import com.event_website.Entity.SlotTicketTypeCapacity;
import com.event_website.Entity.TicketType;
import com.event_website.Request.CreateTicketTypeRequest;
import com.event_website.Request.UpdateTicketTypeRequest;
import com.event_website.Service.SlotService;
import com.event_website.Service.SlotTicketTypeCapacityService;
import com.event_website.Service.TicketTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ticket-type")
@Tag(name = "Ticket Types", description = "APIs for managing ticket types (create, update, retrieve, delete)")
public class TicketTypeController {

  @Autowired
  TicketTypeService ttService;

  @Autowired
  SlotTicketTypeCapacityService sTicketCapService;

  @Autowired
  SlotService slotService;

  @Operation(
          summary = "Get ticket types for an event",
          description = "Retrieves all ticket types associated with all slots under a specific event.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Ticket types retrieved",
                          content = @Content(schema = @Schema(implementation = TicketTypeDTO.class)))
          }
  )
  @GetMapping("/event/{id}")
  public ResponseEntity<List<TicketTypeDTO>> getAllTicketTypesForEvent(@PathVariable Integer id) {
    List<Slot> eventSlots = slotService.findByEventId(id);
    List<Integer> slotsIds = eventSlots.stream().map(Slot::getId).toList();

    List<SlotTicketTypeCapacity> slots = sTicketCapService.getBySlotIds(slotsIds);
    List<TicketTypeDTO> filteredTicketTypes = slots.stream()
            .map(SlotTicketTypeCapacity::getTicketType)
            .distinct()
            .map(TicketTypeDTO::fromEntity)
            .toList();

    return ResponseEntity.ok(filteredTicketTypes);
  }

  @Operation(
          summary = "Get all ticket types",
          description = "Fetches all ticket types in the system.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Ticket types retrieved successfully",
                          content = @Content(schema = @Schema(implementation = TicketTypeDTO.class)))
          }
  )
  @GetMapping("/all")
  public ResponseEntity<List<TicketTypeDTO>> getAll() {
    List<TicketType> tt = ttService.getAllTicketTypes();
    List<TicketTypeDTO> ttDTO = tt.stream().map(TicketTypeDTO::fromEntity).toList();
    return ResponseEntity.ok(ttDTO);
  }

  @Operation(
          summary = "Get ticket type by ID",
          description = "Fetches a ticket type using its unique ID.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Ticket type found",
                          content = @Content(schema = @Schema(implementation = TicketTypeDTO.class))),
                  @ApiResponse(responseCode = "404", description = "Ticket type not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
          }
  )
  @GetMapping("/{id}")
  public ResponseEntity<TicketTypeDTO> findById(@PathVariable Integer id) {
    TicketType tt = ttService.getTicketTypeById(id);
    return ResponseEntity.ok(TicketTypeDTO.fromEntity(tt));
  }

  @Operation(
          summary = "Search ticket type by name",
          description = "Finds a ticket type using its name (exact match).",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Ticket type found",
                          content = @Content(schema = @Schema(implementation = TicketTypeDTO.class))),
                  @ApiResponse(responseCode = "404", description = "Ticket type not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
          }
  )
  @GetMapping("/search")
  public ResponseEntity<TicketTypeDTO> findByType(@RequestParam String type) {
    Optional<TicketType> tt = ttService.findTicketTypeByName(type);
    return tt.map(ticketType -> ResponseEntity.ok(TicketTypeDTO.fromEntity(ticketType)))
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(
          summary = "Create a ticket type",
          description = "Creates a new ticket type with the given name and price.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Ticket type created successfully",
                          content = @Content(schema = @Schema(implementation = TicketTypeDTO.class))),
                  @ApiResponse(responseCode = "400", description = "Invalid input",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
          }
  )
  @PostMapping("/create")
  public ResponseEntity<TicketTypeDTO> createTicketType(@RequestBody CreateTicketTypeRequest req) {
    TicketType tt = ttService.createTicketType(req);
    return ResponseEntity.ok(TicketTypeDTO.fromEntity(tt));
  }

  @Operation(
          summary = "Update a ticket type",
          description = "Updates an existing ticket type with new data.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Ticket type updated successfully",
                          content = @Content(schema = @Schema(implementation = TicketTypeDTO.class))),
                  @ApiResponse(responseCode = "404", description = "Ticket type not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
          }
  )
  @PutMapping("/update/{id}")
  public ResponseEntity<TicketTypeDTO> updateTicketType(
          @PathVariable Integer id,
          @RequestBody UpdateTicketTypeRequest req) {
    TicketType tt = ttService.updateTicketType(id, req);
    return ResponseEntity.ok(TicketTypeDTO.fromEntity(tt));
  }

  @Operation(
          summary = "Delete a ticket type",
          description = "Deletes a ticket type based on its ID.",
          responses = {
                  @ApiResponse(responseCode = "204", description = "Ticket type deleted successfully"),
                  @ApiResponse(responseCode = "404", description = "Ticket type not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
          }
  )
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteTicketType(@PathVariable Integer id) {
    ttService.deleteTicketType(id);
    return ResponseEntity.noContent().build();
  }
}
