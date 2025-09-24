package com.event_website.Dto;

import com.event_website.Entity.TicketType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * Data Transfer Object (DTO) for the TicketType entity.
 *
 * <p>Used to expose ticket type data safely to clients, hiding internal entity structure.
 *
 * <p>Fields: - id - name - price - hasAssignedSeating
 *
 * <p>Includes a static converter method fromEntity(TicketType).
 *
 * @author Abdulrahman Al Rajhi
 * @since 10-09-2025
 * @version 1.0
 */
@Getter
@Setter
@Schema(description = "Data Transfer Object representing the ticket type details")
public class TicketTypeDTO {

  @Schema(description = "Unique identifier of the ticket type", example = "1")
  private Integer id;

  @Schema(description = "Name of the ticket type", example = "VIP")
  private String name;

  @Schema(description = "Price of the ticket type", example = "99.99")
  private BigDecimal price;

  @Schema(description = "Indicates if the ticket type has assigned seating", example = "true")
  private Boolean hasAssignedSeating;

  public static TicketTypeDTO fromEntity(TicketType ticketType) {
    if (ticketType == null) return null;

    TicketTypeDTO dto = new TicketTypeDTO();
    BeanUtils.copyProperties(ticketType, dto);
    return dto;
  }
}
