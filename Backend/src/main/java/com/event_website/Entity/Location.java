package com.event_website.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "additional_info", length = Integer.MAX_VALUE)
    private String additionalInfo;

    // One-to-many relationship with Event
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Event> events;  // The List will hold events related to the location

}
