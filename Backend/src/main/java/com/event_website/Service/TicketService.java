package com.event_website.Service;

import com.event_website.Dto.TicketWithSameTypeDTO;
import com.event_website.Entity.SlotTicketTypeCapacity;
import com.event_website.Entity.Ticket;
import com.event_website.Entity.User;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Repository.*;
import com.event_website.Request.CreateTicketRequest;
import com.event_website.Request.UpdateTicketRequest;
import com.event_website.Utils.TicketCodeGenerator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
 * @author Omar Alomair
 * @since 09-09-2025
 * @version 1.0
 */
@Service
public class TicketService {

  @Autowired private TicketTypeRepo ticketTypeRepo;

  @Autowired private TicketRepo ticketRepo;

  @Autowired private SeatRepo seatRepo;

  @Autowired private UserRepo userRepo;

  @Autowired private SlotTicketTypeCapacityRepo slotTicketTypeCapacityRepo;

  public List<TicketWithSameTypeDTO> getUserGroupedTickets(Integer userId) {
    userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    List<Ticket> tickets = ticketRepo.findByUserId(userId);

    Map<String, List<Ticket>> groupedTicket = // getting all ticket with same type
        tickets.stream()
            .collect(
                Collectors.groupingBy(
                    t -> t.getSlotTicketTypeCapacity().getTicketType().getName()));

    return groupedTicket.values().stream().map(TicketWithSameTypeDTO::fromEntity).toList();
  }

  public List<Ticket> getAllTickets() {
    return ticketRepo.findAll();
  }

  public Ticket createTicket(CreateTicketRequest req) {
    // getting (fetch) the ticket type
    SlotTicketTypeCapacity slotTicketTypeCapacity =
        slotTicketTypeCapacityRepo
            .findById(req.getSlotTicketTypeCapacityId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Association between slot and ticketType not found"));

    Ticket ticket = new Ticket();
    ticket.setSlotTicketTypeCapacity(slotTicketTypeCapacity);
    String code =
        (ticket.getTicketCode() != null)
            ? ticket.getTicketCode()
            : TicketCodeGenerator.generateTicketCode();
    ticket.setTicketCode(code);
    ticket.setCheckedIn(false); // default
    User user =
        userRepo
            .findById(req.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    ticket.setUser(user);

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
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));

    if (req.getSlotTicketTypeCapacityId() != null) {
      ticket.setSlotTicketTypeCapacity(
          slotTicketTypeCapacityRepo
              .findById(req.getSlotTicketTypeCapacityId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Association between slot and ticketType not found")));
    }

    if (req.getTicketCode() != null) {
      ticket.setTicketCode(req.getTicketCode());
    }
    if (req.getSeatId() != null) {
      ticket.setSeat(
          seatRepo
              .findById(req.getSeatId())
              .orElseThrow(() -> new ResourceNotFoundException("Seat not found")));
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
            .orElseThrow(() -> new ResourceNotFoundException("Ticket doesn't exist"));
    if (ticket.getCheckedIn() != null && ticket.getCheckedIn()) {
      throw new RuntimeException("Ticket is already checked in");
    }
    ticket.setCheckedIn(true);
    return ticketRepo.save(ticket);
  }

  public List<Ticket> getUserTickets(Integer id) {
    userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    return ticketRepo.findByUserId(id);
  }
}
