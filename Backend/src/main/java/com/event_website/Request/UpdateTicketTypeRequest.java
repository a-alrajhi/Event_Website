package com.event_website.Request;

import com.event_website.Entity.Slot;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Request object for updating an existing ticket type.
 *
 * <p>Supports partial updates: name, slot, and price can be individually modified.
 *
 * <p>Used in TicketTypeController when handling PUT requests.
 *
 * <p>Future addition (commented): hasAssignedSeating.
 *
 * @author Abdulrahman Al Rajhi
 * @since 10-09-2025
 * @version 1.0
 */
@Getter
@Setter
public class UpdateTicketTypeRequest {
  private String name;
  private BigDecimal price;
  private Boolean hasAssignedSeating;

  @Override
  public String toString() {
    return "UpdateTicketTypeRequest{"
        + "name='"
        + name
        + '\''
        + ", price="
        + price
        + '}';
  }
}
