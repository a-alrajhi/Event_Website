package com.event_website.Controller;

import com.event_website.Dto.EventDto;
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

    // Get all locations with pagination support
    @GetMapping("/All")
    public ResponseEntity<List<LocationDTO>> getAllLocations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Pass the pagination parameters to the service
        List<LocationDTO> locations = locationService.getAllLocations(page, size);
        return ResponseEntity.ok(locations);
    }

    // Get location by ID, including associated events
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

    // Get location by ID and its associated events
    @GetMapping("/{id}/events")
    public ResponseEntity<List<EventDto>> getEventsByLocationId(@PathVariable Integer id) {
        try {
            List<EventDto> events = locationService.getEventsByLocationId(id); // Fetch the events for the location
            return ResponseEntity.ok(events);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Events not found for location with ID: " + id, ex);
        }
    }
}
