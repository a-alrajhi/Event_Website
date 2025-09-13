package com.event_website.Controller;

import com.event_website.Dto.TicketTypeDTO;
import com.event_website.Entity.Slot;
import com.event_website.Entity.SlotTicketTypeCapacity;
import com.event_website.Entity.TicketType;
import com.event_website.Request.CreateTicketTypeRequest;
import com.event_website.Request.UpdateTicketTypeRequest;
import com.event_website.Service.SlotService;
import com.event_website.Service.SlotTicketTypeCapacityService;
import com.event_website.Service.TicketTypeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing ticket type endpoints.
 *
 * <p>Provides HTTP APIs to create, retrieve, update, delete, and search ticket types.
 *
 * <p>Communicates with the TicketTypeService and maps between entity and DTO representations.
 *
 * <p>Endpoints: - GET /ticket-type/all → getAll() - GET /ticket-type/{id} → findById() - GET
 * /ticket-type/search?type= → findByType() - POST /ticket-type/create → createTicketType() - PUT
 * /ticket-type/update/{id} → updateTicketType() - DELETE /ticket-type/delete/{id} →
 * deleteTicketType()
 *
 * @author Abdulrahman Al Rajhi
 * @since 10-09-2025
 * @version 1.0
 */
@RestController
@RequestMapping("/ticket-type")
public class TicketTypeController {
  @Autowired TicketTypeService ttService;

  @Autowired SlotTicketTypeCapacityService sTicketCapService;

  @Autowired SlotService slotService;

  @GetMapping("/event/{id}")
  public ResponseEntity<List<TicketTypeDTO>> getAllTicketTypesForEvent(@PathVariable Integer id) {
    // 1. get the slot from the service --> returns SLOTS
    List<Slot> eventSlots = slotService.findByEventId(id);

    // 2. Get all slots id
    List<Integer> slotsIds = eventSlots.stream().map(Slot::getId).toList();

    List<SlotTicketTypeCapacity> slots = sTicketCapService.getBySlotIds(slotsIds);
    List<TicketTypeDTO> filteredTicketTypes =
        slots.stream()
            .map(SlotTicketTypeCapacity::getTicketType)
            .distinct()
            .map(TicketTypeDTO::fromEntity)
            .toList();

    // 3. get the capacity arr and filter
    return ResponseEntity.ok(filteredTicketTypes);
  }

  // GET - Getting all types
  @GetMapping("/all")
  public ResponseEntity<List<TicketTypeDTO>> getAll() {
    List<TicketType> tt = ttService.getAllTicketTypes();
    List<TicketTypeDTO> ttDTO = tt.stream().map(TicketTypeDTO::fromEntity).toList();
    return ResponseEntity.ok(ttDTO);
  }

  //   GET - Get type by id
  @GetMapping("/{id}")
  public ResponseEntity<TicketTypeDTO> findById(@PathVariable Integer id) {
    TicketType tt = ttService.getTicketTypeById(id);
    return ResponseEntity.ok(TicketTypeDTO.fromEntity(tt));
  }

  // GET - Find by type
  @GetMapping("/search")
  public ResponseEntity<TicketTypeDTO> findByType(@RequestParam String type) {
    Optional<TicketType> tt = ttService.findTicketTypeByName(type);
    if (tt.isPresent()) {
      return ResponseEntity.ok(TicketTypeDTO.fromEntity(tt.get()));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // POST - Create a ticket type
  @PostMapping("/create")
  public ResponseEntity<TicketTypeDTO> createTicketType(@RequestBody CreateTicketTypeRequest req) {
    TicketType tt = ttService.createTicketType(req);
    return ResponseEntity.ok(TicketTypeDTO.fromEntity(tt));
  }

  // PUT - Update a ticket type
  @PutMapping("/update/{id}")
  public ResponseEntity<TicketTypeDTO> updateTicketType(
      @PathVariable Integer id, @RequestBody UpdateTicketTypeRequest req) {
    TicketType tt = ttService.updateTicketType(id, req);
    return ResponseEntity.ok(TicketTypeDTO.fromEntity(tt));
  }

  // DELETE - delete a ticket type
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteTicketType(@PathVariable Integer id) {
    ttService.deleteTicketType(id);
    return ResponseEntity.noContent().build();
  }
}
