package com.event_website.Request;

import com.event_website.Entity.Slot;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Request object for creating a new ticket type.
 *
 * <p>Contains the required fields: name, slot, and price.
 *
 * <p>Used in TicketTypeController when handling POST requests.
 *
 * <p>Future addition (commented): hasAssignedSeating.
 *
 * @author Abdulrahman Al Rajhi
 * @since 10-09-2025
 * @version 1.0
 */
@Getter
@Setter
public class CreateTicketTypeRequest {
    private String name;
    private BigDecimal price;
    private Boolean hasAssignedSeating;

    @Override
    public String toString() {
        return "CreateTicketTypeRequest{"
                + "name='"
                + name
                + '\''
                + ", price="
                + price
                + '}';
    }
}