package com.event_website.Service;

import com.event_website.Dto.DetailedTicketWithSameTypeDTO;
import com.event_website.Dto.TicketDTO;
import com.event_website.Entity.*;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Repository.SlotTicketTypeCapacityRepo;
import com.event_website.Repository.TicketRepo;
import com.event_website.Repository.UserRepo;
import com.event_website.Request.BatchTicketRequest;
import com.event_website.Utils.TicketCodeGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatchTicketService {

  @Autowired private UserRepo userRepo;
  @Autowired private SlotTicketTypeCapacityRepo slotTicketTypeCapacityRepo;
  @Autowired private TicketRepo ticketRepo;
  @Autowired private EmailService emailService;
  @Autowired private TicketService ticketService;

  /**
   * Generating tickets from the batchTicketRequests 1 - N This uses passed fields from the BTR
   * which are UserID, SlotID, TTid, Quantity
   *
   * @param batchTicketRequests 1 - N reqs
   * @return a list of new Ticket DTO (generated tickets)
   */
  @Transactional
  public List<TicketDTO> generateTickets(List<BatchTicketRequest> batchTicketRequests) {
    List<TicketDTO> createdTickets = new ArrayList<>();
    List<Ticket> createdTicketsList = new ArrayList<>();

    // for all reqs, get how many tickets and generate them
    for (BatchTicketRequest req : batchTicketRequests) {
      // make sure user exists
      User user =
          userRepo
              .findById(req.getUserId())
              .orElseThrow(() -> new RuntimeException("User not found"));

      // ensuring slotId && ticket type exist
      SlotTicketTypeCapacity capacity =
          slotTicketTypeCapacityRepo
              .findBySlot_IdAndTicketType_Id(req.getSlotId(), req.getTicketTypeId())
              .orElseThrow(
                  () -> new ResourceNotFoundException("Slot Id or  Ticket Type Id not found"));

      long issued = ticketRepo.countTicketsBySlotAndType(req.getSlotId(), req.getTicketTypeId());
      int remaining = capacity.getCapacity() - (int) issued;

      if (remaining < req.getQuantity()) {
        throw new RuntimeException("Not enough tickets available");
      }

      // should be all good here

      // generating new tickets
      for (int i = 0; i < req.getQuantity(); i++) {
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setSlotTicketTypeCapacity(capacity);
        ticket.setTicketCode(TicketCodeGenerator.generateTicketCode());
        ticket.setCheckedIn(false);

        ticket = ticketRepo.save(ticket);
        createdTicketsList.add(ticket);
        createdTickets.add(TicketDTO.fromEntity(ticket));
      }

//      try {
//        emailService.sendTicketEmail(user.getId(), generateGroup(createdTicketsList));
//      } catch (Exception e) {
//        // Log or handle email failure, but don’t interrupt ticket generation
//        System.err.println("Failed to send email to user " + user.getEmail() + ": " + e.getMessage());
//      }
    }
    return createdTickets;
  }

  public List<DetailedTicketWithSameTypeDTO> generateGroup(List<Ticket> tickets) {
    Map<String, List<Ticket>> groupedTickets = tickets.stream()
            .collect(Collectors.groupingBy(t -> {
              int ticketTypeId = t.getSlotTicketTypeCapacity().getTicketType().getId();
              int slotId = t.getSlotTicketTypeCapacity().getSlot().getId();
              return ticketTypeId + "-" + slotId; // Composite key as String
            }));

    return groupedTickets.values().stream().map(ts -> {
      Ticket t = ts.getFirst();
      SlotTicketTypeCapacity slotTicketTypeCapacity = t.getSlotTicketTypeCapacity();
      Slot slot = slotTicketTypeCapacity.getSlot();
      TicketType tt = slotTicketTypeCapacity.getTicketType();
      Event event = slot.getEvent();
      return DetailedTicketWithSameTypeDTO.fromEntity(ts, event, slot, tt);
    }).toList();
  }
}
