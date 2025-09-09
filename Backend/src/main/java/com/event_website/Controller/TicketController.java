package com.event_website.Controller;

import com.event_website.Dto.TicketDTO;
import com.event_website.Entity.Ticket;
import com.event_website.Request.CreateTicketRequest;
import com.event_website.Request.UpdateTicketRequest;
import com.event_website.Service.TicketService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing tickets.
 *
 * <p>Base path: /ticket
 *
 * <p>Endpoints: - GET /all: Get all tickets - GET /{id}: Get ticket by ID - POST /create-ticket:
 * Create a new ticket - PUT /update-ticket/{id}: Update a ticket - DELETE /{id}: Delete a ticket -
 * POST /check-in/{code}: Check in a ticket by code
 *
 * @author Abdulrahman Al Rajhi
 * @since 09-09-2025
 * @version 1.0
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

  @Autowired private TicketService ticketService;

  @GetMapping("/all")
  public ResponseEntity<List<TicketDTO>> getAllTickets() {
    List<Ticket> tickets = ticketService.getAllTickets();
    List<TicketDTO> ticketDTOS = tickets.stream().map(TicketDTO::fromEntity).toList();
    return ResponseEntity.ok(ticketDTOS);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TicketDTO> getTicketById(@PathVariable Integer id) {
    Ticket ticket = ticketService.getTicketById(id);
    TicketDTO ticketDTO = TicketDTO.fromEntity(ticket);
    return ResponseEntity.ok(ticketDTO);
  }

  @PostMapping("/create-ticket")
  public ResponseEntity<TicketDTO> createTicket(@RequestBody CreateTicketRequest req) {
    Ticket ticket = ticketService.createTicket(req);
    return ResponseEntity.ok(TicketDTO.fromEntity(ticket));
  }

  @PutMapping("/update-ticket/{id}")
  public ResponseEntity<TicketDTO> updateTicket(
      @PathVariable Integer id, @Valid @RequestBody UpdateTicketRequest req) {
    Ticket ticket = ticketService.updateTicket(id, req);
    return ResponseEntity.ok(TicketDTO.fromEntity(ticket));
  }

  @DeleteMapping("/{id}")
  public void deleteTicket(@PathVariable Integer id) {
    ticketService.deleteTicket(id);
  }

  @PostMapping("/check-in/{code}")
  public ResponseEntity<TicketDTO> checkIn(@PathVariable String code) {
    Ticket ticket = ticketService.checkInTicketByCode(code);
    return ResponseEntity.ok(TicketDTO.fromEntity(ticket));
  }
}
