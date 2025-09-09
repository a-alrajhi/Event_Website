package com.event_website.Dto;

import com.event_website.Entity.Seat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class SeatDTO {
  private Integer id;
  private String section;
  private String row;
  private String number;
  private Boolean isReserved;

  public static SeatDTO fromEntity(Seat seat) {
    if (seat == null) return null;

    SeatDTO dto = new SeatDTO();
    BeanUtils.copyProperties(seat, dto);
    return dto;
  }
}
