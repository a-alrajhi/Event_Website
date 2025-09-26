package com.event_website.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Request object for creating a new ticket type.
 *
 * <p>Contains the required fields: name, price, and hasAssignedSeating.
 *
 * <p>Used in TicketTypeController when handling POST requests.
 *
 * @author Abdulrahman Al Rajhi
 * @since 10-09-2025
 * @version 1.0
 */
@Getter
@Setter
public class CreateTicketTypeRequest {

  @NotBlank
  @Schema(description = "Name of the ticket type", example = "VIP")
  private String name;

  @NotNull
  @Positive
  @Schema(description = "Price of the ticket type", example = "99.99")
  private BigDecimal price;

  @NotNull
  @Schema(description = "Indicates if assigned seating is required for this ticket type", example = "false")
  private Boolean hasAssignedSeating;

  @Override
  public String toString() {
    return "CreateTicketTypeRequest{" +
            "name='" + name + '\'' +
            ", price=" + price +
            ", hasAssignedSeating=" + hasAssignedSeating +
            '}';
  }
}
