package com.event_website.Service;

import com.event_website.Entity.TicketType;
import com.event_website.Repository.TicketTypeRepo;
import com.event_website.Request.CreateTicketTypeRequest;
import com.event_website.Request.UpdateTicketTypeRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing ticket type operations.
 *
 * <p>Handles business logic for creating, retrieving, updating, and deleting ticket types.
 *
 * <p>Used by the TicketTypeController. Works only with entities (no DTOs).
 *
 * <p>Methods: - getAllTicketTypes() - getTicketTypeById() - findTicketTypeByName() -
 * createTicketType() - updateTicketType() - deleteTicketType()
 *
 * @author Abdulrahman Al Rajhi
 * @since 10-09-2025
 * @version 1.0
 */
@Service
public class TicketTypeService {

  @Autowired TicketTypeRepo ticketTypeRepo;

  // GET - Getting all types
  public List<TicketType> getAllTicketTypes() {
    return ticketTypeRepo.findAll();
  }

  //   GET - Get type by id
  public TicketType getTicketTypeById(Integer id) {
    return ticketTypeRepo
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Ticket Type with id " + id + " not found"));
  }

  // GET - Find by name
  public Optional<TicketType> findTicketTypeByName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new RuntimeException("Ticket Type name is null or empty");
    }
    return ticketTypeRepo.findByName(name.trim());
  }

  // POST - Create a ticket type
  public TicketType createTicketType(CreateTicketTypeRequest req) {
    if (req.getName() == null || req.getName().trim().isEmpty()) {
      throw new RuntimeException("Ticket type name cannot be null or empty");
    }
    TicketType t = new TicketType();
    t.setName(req.getName());
    t.setPrice(req.getPrice());
    t.setSlot(req.getSlot());
    t.setHasAssignedSeating(false); // default val

    return ticketTypeRepo.save(t);
  }

  // PUT - Update a ticket type
  public TicketType updateTicketType(Integer id, UpdateTicketTypeRequest req) {
    if (req.getName() == null || req.getName().trim().isEmpty()) {
      throw new RuntimeException("Ticket type name cannot be null or empty");
    }
    TicketType t =
        ticketTypeRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Cannot update ticket type.\nTicket type with id " + id + " not found"));

    if (req.getName() != null) {
      t.setName(req.getName());
    }
    if (req.getPrice() != null) {
      t.setPrice(req.getPrice());
    }
    if (req.getSlot() != null) {
      t.setSlot(req.getSlot());
    }
    return ticketTypeRepo.save(t);
  }

  // DELETE - delete a ticket type
  public void deleteTicketType(Integer id) {
    if (!ticketTypeRepo.existsById(id)) {
      throw new RuntimeException("Ticket Type with id " + id + " not found");
    }
    ticketTypeRepo.deleteById(id);
  }
}
