package com.event_website.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ar_name")
    private String arName;

    @Column(name = "photo")
    private String photo;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "ar_description", length = Integer.MAX_VALUE)
    private String arDescription;

    // One-to-many relationship with Event
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Event> events;  // This list will hold events related to the category
}
