package com.event_website.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Request object for updating an existing ticket type.
 *
 * <p>Supports partial updates: name, price, and hasAssignedSeating can be individually modified.
 *
 * <p>Used in TicketTypeController when handling PUT requests.
 *
 * @author Abdulrahman Al Rajhi
 * @since 10-09-2025
 * @version 1.0
 */
@Getter
@Setter
@Schema(
        name = "UpdateTicketTypeRequest",
        description = "Request payload to update fields of an existing ticket type"
)
public class UpdateTicketTypeRequest {

  @Schema(description = "Name of the ticket type", example = "VIP Pass", nullable = true)
  private String name;

  @Schema(description = "Price of the ticket type", example = "99.99", nullable = true)
  private BigDecimal price;

  @Schema(description = "Indicates if the ticket type has assigned seating", example = "true", nullable = true)
  private Boolean hasAssignedSeating;

  @Override
  public String toString() {
    return "UpdateTicketTypeRequest{"
            + "name='"
            + name
            + '\''
            + ", price="
            + price
            + ", hasAssignedSeating="
            + hasAssignedSeating
            + '}';
  }
}
