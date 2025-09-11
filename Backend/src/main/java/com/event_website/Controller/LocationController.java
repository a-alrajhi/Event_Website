package com.event_website.Controller;
/**
 * @author Yazeed
 * This controller manages location-related API requests,
 * including creating, retrieving, updating, and deleting locations.
 */
import com.event_website.Dto.LocationDTO;
import com.event_website.Service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    // Constructor injection
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // Create a new location
    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@Valid @RequestBody LocationDTO locationDTO) {
        LocationDTO responseDTO = locationService.createLocation(locationDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // Get all locations
    @GetMapping("/All")
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        List<LocationDTO> locations = locationService.getAllLocations();
        System.out.println("Locations: " + locations);

        return ResponseEntity.ok(locations);
    }

    // Get location by ID
    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable Integer id) {
        try {
            LocationDTO location = locationService.getLocationById(id);
            return ResponseEntity.ok(location);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with ID: " + id, ex);
        }
    }

    // Update location
    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable Integer id, @Valid @RequestBody LocationDTO locationDTO) {
        try {
            LocationDTO updatedLocation = locationService.updateLocation(id, locationDTO);
            return ResponseEntity.ok(updatedLocation);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with ID: " + id, ex);
        }
    }

    // Delete location
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Integer id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with ID: " + id, ex);
        }
    }

    // Global Exception handler for other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong: " + e.getMessage());
    }
}
