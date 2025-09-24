package com.event_website.Controller;

import com.event_website.Dto.TicketDTO;
import com.event_website.Dto.ErrorDTO;
import com.event_website.Entity.Ticket;
import com.event_website.Request.CreateTicketRequest;
import com.event_website.Request.UpdateTicketRequest;
import com.event_website.Service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tickets.
 *
 * <p>Base path: /ticket
 *
 * <p>Endpoints:
 * - GET /all: Get all tickets
 * - GET /{id}: Get ticket by ID
 * - POST /create-ticket: Create a new ticket
 * - PUT /update-ticket/{id}: Update a ticket
 * - DELETE /{id}: Delete a ticket
 * - POST /check-in/{code}: Check in a ticket by code
 *
 * @author Abdulrahman Al Rajhi
 * @since 09-09-2025
 * @version 1.0
 */
@RestController
@RequestMapping("/ticket")
@Tag(name = "Ticket", description = "Ticket purchase, retrieval, update and check-in endpoints")
public class TicketController {

  @Autowired
  private TicketService ticketService;

  @Operation(
          summary = "Get all tickets",
          description = "Retrieves all tickets from the system.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Tickets retrieved successfully",
                          content = @Content(schema = @Schema(implementation = TicketDTO.class)))
          }
  )
  @GetMapping("/all")
  public ResponseEntity<List<TicketDTO>> getAllTickets() {
    List<Ticket> tickets = ticketService.getAllTickets();
    List<TicketDTO> ticketDTOS = tickets.stream().map(TicketDTO::fromEntity).toList();
    return ResponseEntity.ok(ticketDTOS);
  }

  @Operation(
          summary = "Get ticket by ID",
          description = "Retrieves a ticket based on its unique ID.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Ticket found",
                          content = @Content(schema = @Schema(implementation = TicketDTO.class))),
                  @ApiResponse(responseCode = "404", description = "Ticket not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
          }
  )
  @GetMapping("/{id}")
  public ResponseEntity<TicketDTO> getTicketById(@PathVariable Integer id) {
    Ticket ticket = ticketService.getTicketById(id);
    return ResponseEntity.ok(TicketDTO.fromEntity(ticket));
  }

  @Operation(
          summary = "Create a new ticket",
          description = "Creates a ticket based on slot, ticket type, and user.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Ticket created successfully",
                          content = @Content(schema = @Schema(implementation = TicketDTO.class))),
                  @ApiResponse(responseCode = "400", description = "Invalid input or business rule violation",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
          }
  )
  @PostMapping("/create-ticket")
  public ResponseEntity<TicketDTO> createTicket(@RequestBody CreateTicketRequest req) {
    Ticket ticket = ticketService.createTicket(req);
    return ResponseEntity.ok(TicketDTO.fromEntity(ticket));
  }

  @Operation(
          summary = "Update a ticket",
          description = "Updates an existing ticket's information.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Ticket updated successfully",
                          content = @Content(schema = @Schema(implementation = TicketDTO.class))),
                  @ApiResponse(responseCode = "404", description = "Ticket not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
          }
  )
  @PutMapping("/update-ticket/{id}")
  public ResponseEntity<TicketDTO> updateTicket(
          @PathVariable Integer id,
          @Valid @RequestBody UpdateTicketRequest req) {
    Ticket ticket = ticketService.updateTicket(id, req);
    return ResponseEntity.ok(TicketDTO.fromEntity(ticket));
  }

  @Operation(
          summary = "Delete a ticket",
          description = "Deletes a ticket by its ID.",
          responses = {
                  @ApiResponse(responseCode = "204", description = "Ticket deleted successfully"),
                  @ApiResponse(responseCode = "404", description = "Ticket not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
          }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<TicketDTO> deleteTicket(@PathVariable Integer id) {
    ticketService.deleteTicket(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(
          summary = "Check-in a ticket by code",
          description = "Marks the ticket as checked-in using its unique code.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Ticket checked in successfully",
                          content = @Content(schema = @Schema(implementation = TicketDTO.class))),
                  @ApiResponse(responseCode = "404", description = "Ticket not found or already checked-in",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
          }
  )
  @PostMapping("/check-in/{code}")
  public ResponseEntity<TicketDTO> checkIn(@PathVariable String code) {
    Ticket ticket = ticketService.checkInTicketByCode(code);
    return ResponseEntity.ok(TicketDTO.fromEntity(ticket));
  }
}
