package com.event_website.Request;

import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for updating an existing ticket.
 *
 * <p>Contains optional fields to update a ticket's type, assigned seat, or ticket code. All fields
 * are nullable, allowing partial updates.
 *
 * @since 09-09-2025
 * @version 1.0
 * @author Abdulrahman Al Rajhi
 */
@Getter
@Setter
public class UpdateTicketRequest {
  private Integer slotTicketTypeCapacityId;
  private Integer seatId; // Optional: only for assigned seating
  private String ticketCode;

  @Override
  public String toString() {
    return "UpdateTicketRequest{"
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
