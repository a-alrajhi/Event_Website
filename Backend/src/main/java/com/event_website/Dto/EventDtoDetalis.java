package com.event_website.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EventDtoDetalis {

    private Integer id;
    private String name;
    private String description;
    private String photoUrl;
    private String categoryName;
    private List<BigDecimal> prices;  // Add list of ticket prices
    private String locationName;      // Add location name if needed

    // Constructor to ensure compatibility with EventService
    public EventDtoDetalis(Integer id, String name, String description, String photoUrl, String categoryName, List<BigDecimal> prices, String locationName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.categoryName = categoryName;
        this.prices = prices;
        this.locationName = locationName;
    }

    // Default constructor
    public EventDtoDetalis() {}
}