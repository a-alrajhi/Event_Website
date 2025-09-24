package com.event_website.Service;

import com.event_website.Dto.TicketDTO;
import com.event_website.Entity.SlotTicketTypeCapacity;
import com.event_website.Entity.Ticket;
import com.event_website.Entity.User;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Repository.SlotTicketTypeCapacityRepo;
import com.event_website.Repository.TicketRepo;
import com.event_website.Repository.UserRepo;
import com.event_website.Request.BatchTicketRequest;
import com.event_website.Utils.TicketCodeGenerator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatchTicketService {

  @Autowired private UserRepo userRepo;
  @Autowired private SlotTicketTypeCapacityRepo slotTicketTypeCapacityRepo;
  @Autowired private TicketRepo ticketRepo;

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

        ticketRepo.save(ticket);
        createdTickets.add(TicketDTO.fromEntity(ticket));
      }
    }
    return createdTickets;
  }
}
