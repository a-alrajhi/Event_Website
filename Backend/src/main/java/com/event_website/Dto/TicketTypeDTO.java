package com.event_website.Dto;

import com.event_website.Entity.TicketType;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class TicketTypeDTO {
  private Integer id;
  private String name;
  private BigDecimal price;
  private Integer capacity;
  private Boolean hasAssignedSeating;

  public static TicketTypeDTO fromEntity(TicketType ticketType) {
    if (ticketType == null) return null;

    TicketTypeDTO dto = new TicketTypeDTO();
    BeanUtils.copyProperties(ticketType, dto);
    return dto;
  }
}
