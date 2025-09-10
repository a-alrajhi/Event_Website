package com.event_website.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "slots")
public class Slot {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id")
  private Event event;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Column(name = "start_time", nullable = false)
  private LocalTime startTime;

  @Column(name = "end_time")
  private LocalTime endTime;
}
