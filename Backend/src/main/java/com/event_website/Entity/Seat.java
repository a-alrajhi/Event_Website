package com.event_website.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "seats")
public class Seat {
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

    @ColumnDefault("false")
    @Column(name = "is_reserved")
    private Boolean isReserved;

}