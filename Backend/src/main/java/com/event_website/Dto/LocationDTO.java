package com.event_website.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDTO {

    private Integer id;

    @NotNull(message = "Location name is required")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    private Double latitude;
    private Double longitude;

    @Size(max = 10000, message = "Additional info is too long")
    private String additionalInfo;

    // Constructor for creating LocationDTO from a Location entity
    public LocationDTO(Integer id, String name, String address, Double latitude, Double longitude, String additionalInfo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.additionalInfo = additionalInfo;
    }

    // Default constructor for JSON deserialization
    public LocationDTO() {}
}
