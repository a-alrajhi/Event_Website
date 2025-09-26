package com.event_website.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating a new ticket.
 *
 * <p>Contains the data required to create a ticket, including the ticket type ID, an optional seat
 * ID (if assigned seating is used), the user ID, and an optional custom ticket code.
 *
 * @since 09-09-2025
 * @version 1.0
 */
@Getter
@Setter
@Schema(description = "Request payload to create a new ticket")
public class CreateTicketRequest {

  @NotNull
  @Schema(description = "ID of the slot-ticket-type capacity", example = "123")
  private Integer slotTicketTypeCapacityId;

  @Schema(description = "ID of the seat if assigned seating is used", example = "45")
  private Integer seatId; // Optional: only for assigned seating

  @NotNull
  @Schema(description = "ID of the user for whom the ticket is created", example = "789")
  private Integer userId;

  @Schema(description = "Optional custom ticket code", example = "TKT-XXXX")
  private String ticketCode;

  @Override
  public String toString() {
    return "CreateTicketRequest{" +
            "slotTicketTypeCapacityId=" + slotTicketTypeCapacityId +
            ", seatId=" + seatId +
            ", userId=" + userId +
            ", ticketCode='" + ticketCode + '\'' +
            '}';
  }
}
