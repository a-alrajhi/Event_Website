package com.event_website.Request;

import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating a new ticket.
 *
 * <p>Contains the data required to create a ticket, including the ticket type ID, an optional seat
 * ID (if assigned seating is used), and an optional custom ticket code.
 *
 * @since 09-09-2025
 * @version 1.0
 * @author Abdulrahman Al Rajhi
 */
@Getter
@Setter
public class CreateTicketRequest {
  private Integer slotTicketTypeCapacityId;
  private Integer seatId; // Optional: only for assigned seating
  private String ticketCode;

  @Override
  public String toString() {
    return "CreateTicketRequest{"
        + "slotTicketTypeCapacityId="
        + slotTicketTypeCapacityId
        + ", seatId="
        + seatId
        + ", ticketCode='"
        + ticketCode
        + '\''
        + '}';
  }
}
