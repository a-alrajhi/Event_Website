package com.event_website.Dto;

import com.event_website.Entity.TicketType;
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
public class TicketTypeDTO {
  private Integer id;
  private String name;
  private BigDecimal price;
  private Boolean hasAssignedSeating;

  public static TicketTypeDTO fromEntity(TicketType ticketType) {
    if (ticketType == null) return null;

    TicketTypeDTO dto = new TicketTypeDTO();
    BeanUtils.copyProperties(ticketType, dto);
    return dto;
  }
}
