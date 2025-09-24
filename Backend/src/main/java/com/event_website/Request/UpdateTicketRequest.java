package com.event_website.Request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        name = "UpdateTicketRequest",
        description = "Request payload to update fields of an existing ticket"
)
public class UpdateTicketRequest {

  @Schema(description = "ID of the slot ticket type capacity", example = "5", nullable = true)
  private Integer slotTicketTypeCapacityId;

  @Schema(description = "ID of the assigned seat (if applicable)", example = "12", nullable = true)
  private Integer seatId;

  @Schema(description = "Custom ticket code", example = "TKT-XXXX", nullable = true)
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
