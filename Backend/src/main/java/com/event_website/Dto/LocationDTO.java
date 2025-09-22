package com.event_website.Dto;
/**
 * @author Yazeed
 * DTO class to represent location data, including validation rules
 * for location attributes.
 */
import com.event_website.Entity.Location;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LocationDTO {

    private Integer id;

    //@NotNull(message = "Location name is required")
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
