package com.event_website.Dto;

import com.event_website.Entity.Ticket;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * Data Transfer Object (DTO) for the Ticket entity.
 *
 * <p>Represents ticket data sent to the frontend. Contains ticket details along with related
 * SlotTicketTypeCapacity and Seat information.
 *
 * <p>This DTO is used only in the controller layer. All entity-to-DTO mapping is done using the
 * static fromEntity method.
 *
 * Fields: id, ticketCode, checkedIn, slotTicketTypeCapacityDTO, seat, userId
 *
 * @author Abdulrahman
 * @since 09-09-2025
 * @version 1.0
 */
@Getter
@Setter
@Schema(description = "Data Transfer Object representing a Ticket with associated slot ticket type capacity and seat details")
public class TicketDTO {

  @Schema(description = "Unique identifier of the ticket", example = "123")
  private Integer id;

  @Schema(description = "Unique code of the ticket", example = "TKT-ABC123")
  private String ticketCode;

  @Schema(description = "Indicates whether the ticket has been checked in", example = "false")
  private Boolean checkedIn;

  @Schema(description = "Details about the slot ticket type capacity associated with the ticket")
  private SlotTicketTypeCapacityDTO slotTicketTypeCapacityDTO;

  @Schema(description = "Seat information associated with the ticket")
  private SeatDTO seat;

  @Schema(description = "ID of the user who owns the ticket", example = "42")
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
