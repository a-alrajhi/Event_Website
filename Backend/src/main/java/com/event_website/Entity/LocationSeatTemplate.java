package com.event_website.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "location_seat_templates")
public class LocationSeatTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "section")
    private String section;

    @Column(name = "\"row\"")
    private String row;

    @Column(name = "seat_number")
    private String seatNumber;

}