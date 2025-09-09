package com.event_website.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ar_name")
    private String arName;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "ar_description", length = Integer.MAX_VALUE)
    private String arDescription;

    @Column(name = "photo_url")
    private String photoUrl;

    @ColumnDefault("false")
    @Column(name = "has_assigned_seating")
    private Boolean hasAssignedSeating;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "location_id")
    private Location location;

}