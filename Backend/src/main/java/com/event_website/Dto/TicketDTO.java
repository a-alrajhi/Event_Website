package com.event_website.Dto;

import com.event_website.Entity.Ticket;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * Data Transfer Object (DTO) for the Ticket entity.
 *
 * <p>Represents ticket data sent to the frontend. Contains ticket details along with related
 * TicketType and Seat information.
 *
 * <p>This DTO is used only in the controller layer. All entity-to-DTO mapping is done using the
 * static fromEntity method.
 *
 * <p>Fields: - id - ticketCode - checkedIn - ticketType (TicketTypeDTO) - seat (SeatDTO)
 *
 * @author Abdulrahman Al Rajhi
 * @since 09-09-2025
 * @version 1.0
 */
@Getter
@Setter
public class TicketDTO {
  private Integer id;
  private String ticketCode;
  private Boolean checkedIn;
  private SlotTicketTypeCapacityDTO slotTicketTypeCapacityDTO;
  private SeatDTO seat;
  private Integer userId;

  public static TicketDTO fromEntity(Ticket ticket) {
    if (ticket == null) return null;
    TicketDTO ticketDTO = new TicketDTO();
    BeanUtils.copyProperties(ticket, ticketDTO);

    if (ticket.getSlotTicketTypeCapacity() != null) {
      SlotTicketTypeCapacityDTO slotTicketTypeCapacityDTO = SlotTicketTypeCapacityDTO.fromEntity(ticket.getSlotTicketTypeCapacity());
      ticketDTO.setSlotTicketTypeCapacityDTO(slotTicketTypeCapacityDTO);
    }

    if (ticket.getSeat() != null) {
      SeatDTO seatDTO = SeatDTO.fromEntity(ticket.getSeat());
      ticketDTO.setSeat(seatDTO);
    }
    ticketDTO.setUserId(ticket.getUser().getId());
    return ticketDTO;
  }
}
