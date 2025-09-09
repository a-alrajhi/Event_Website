package com.event_website.Service;

import com.event_website.Entity.Ticket;
import com.event_website.Entity.TicketType;
import com.event_website.Repository.SeatRepo;
import com.event_website.Repository.TicketRepo;
import com.event_website.Repository.TicketTypeRepo;
import com.event_website.Request.CreateTicketRequest;
import com.event_website.Request.UpdateTicketRequest;
import com.event_website.Utils.TicketCodeGenerator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing ticket operations.
 *
 * <p>Handles business logic for creating, retrieving, updating, deleting, and checking in tickets.
 *
 * <p>Used by the TicketController. Works only with entities (no DTOs).
 *
 * <p>Methods: - getAllTickets() - getTicketById() - createTicket() - updateTicket() -
 * deleteTicket() - checkInTicketByCode()
 *
 * @author Abdulrahman Al Rajhi
 * @since 09-09-2025
 * @version 1.0
 */
@Service
public class TicketService {

  @Autowired private TicketTypeRepo ticketTypeRepo;

  @Autowired private TicketRepo ticketRepo;

  @Autowired private SeatRepo seatRepo;

  public List<Ticket> getAllTickets() {
    return ticketRepo.findAll();
  }

  public Ticket createTicket(CreateTicketRequest req) {
    // getting (fetch) the ticket type
    TicketType ticketType =
        ticketTypeRepo
            .findById(req.getTicketTypeId())
            .orElseThrow(() -> new RuntimeException("TicketType not found"));

    Ticket ticket = new Ticket();
    ticket.setTicketType(ticketType);
    String code =
        (ticket.getTicketCode() != null)
            ? ticket.getTicketCode()
            : TicketCodeGenerator.generateTicketCode();
    ticket.setTicketCode(code);
    ticket.setCheckedIn(false); // default

    Ticket savedTicket = ticketRepo.save(ticket);
    return savedTicket;
  }

  public Ticket getTicketById(Integer id) {
    return ticketRepo.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
  }

  public Ticket updateTicket(Integer id, UpdateTicketRequest req) {
    Ticket ticket =
        ticketRepo
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

    if (req.getTicketTypeId() != null) {
      ticket.setTicketType(
          ticketTypeRepo
              .findById(req.getTicketTypeId())
              .orElseThrow(() -> new RuntimeException("TicketType not found")));
    }

    if (req.getTicketCode() != null) {
      ticket.setTicketCode(req.getTicketCode());
    }
    if (req.getSeatId() != null) {
      ticket.setSeat(
          seatRepo
              .findById(req.getSeatId())
              .orElseThrow(() -> new RuntimeException("Seat not found")));
    }

    return ticketRepo.save(ticket);
  }

  public void deleteTicket(Integer id) {
    ticketRepo.deleteById(id);
  }

  public Ticket checkInTicketByCode(String code) {
    Ticket ticket =
        ticketRepo
            .findByTicketCode(code)
            .orElseThrow(() -> new RuntimeException("Ticket doesn't exist"));
    if (ticket.getCheckedIn() != null && ticket.getCheckedIn()) {
      throw new RuntimeException("Ticket is already checked in");
    }
    ticket.setCheckedIn(true);
    return ticketRepo.save(ticket);
  }
}
