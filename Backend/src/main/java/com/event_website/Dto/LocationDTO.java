package com.event_website.Dto;

import com.event_website.Entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO class to represent location data, including validation rules
 * and Swagger schema annotations for documentation.
 *
 * Author: Yazeed
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Data Transfer Object for Location details")
public class LocationDTO {

    @Schema(description = "Unique identifier of the location", example = "1")
    private Integer id;

    @Schema(description = "Name of the location", example = "King Fahad Stadium")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Schema(description = "Physical address of the location", example = "Riyadh, Saudi Arabia")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @Schema(description = "Latitude coordinate of the location", example = "24.774265")
    private Double latitude;

    @Schema(description = "Longitude coordinate of the location", example = "46.738586")
    private Double longitude;

    @Schema(description = "Optional additional information about the location", example = "Main gate is near parking lot A")
    @Size(max = 10000, message = "Additional info is too long")
    private String additionalInfo;
    @Schema(description = "List of events associated with this location")
    private List<EventDto> events;  // Added list of events associated with the location

    // Constructor for creating LocationDTO from a Location entity
    public LocationDTO(Integer id, String name, String address, Double latitude, Double longitude, String additionalInfo, List<EventDto> events) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.additionalInfo = additionalInfo;
        this.events = events;
    }

    public Location toEntity() {
        Location location = new Location();
        location.setId(this.id);
        location.setName(this.name);
        location.setAddress(this.address);
        location.setLatitude(this.latitude);
        location.setLongitude(this.longitude);
        location.setAdditionalInfo(this.additionalInfo);
        return location;
    }
}
