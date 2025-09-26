package com.event_website.Controller;

import com.event_website.Dto.EventDto;
import com.event_website.Dto.LocationDTO;
import com.event_website.Dto.ErrorDTO;
import com.event_website.Service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/locations")
@Tag(name = "Locations", description = "APIs for managing event locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(
            summary = "Create a new location",
            description = "Creates a new location and returns the created LocationDTO.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Location created successfully",
                            content = @Content(schema = @Schema(implementation = LocationDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(
            @Valid @RequestBody LocationDTO locationDTO) {
        LocationDTO responseDTO = locationService.createLocation(locationDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            summary = "Get all locations",
            description = "Returns a list of all existing locations.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Locations retrieved successfully",
                            content = @Content(schema = @Schema(implementation = LocationDTO.class)))
            }
    )
    @GetMapping("/All")
    public ResponseEntity<List<LocationDTO>> getAllLocations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Pass the pagination parameters to the service
        List<LocationDTO> locations = locationService.getAllLocations(page, size);
        return ResponseEntity.ok(locations);
    }

    @Operation(
            summary = "Get a location by ID",
            description = "Fetch a specific location by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Location found",
                            content = @Content(schema = @Schema(implementation = LocationDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Location not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(
            @Parameter(description = "ID of the location to retrieve") @PathVariable Integer id) {
        try {
            LocationDTO location = locationService.getLocationById(id);
            return ResponseEntity.ok(location);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with ID: " + id, ex);
        }
    }

    @Operation(
            summary = "Update a location",
            description = "Updates the information of an existing location by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Location updated successfully",
                            content = @Content(schema = @Schema(implementation = LocationDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Location not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> updateLocation(
            @Parameter(description = "ID of the location to update") @PathVariable Integer id,
            @Valid @RequestBody LocationDTO locationDTO) {
        try {
            LocationDTO updatedLocation = locationService.updateLocation(id, locationDTO);
            return ResponseEntity.ok(updatedLocation);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with ID: " + id, ex);
        }
    }

    @Operation(
            summary = "Delete a location",
            description = "Deletes the location identified by the given ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Location deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Location not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(
            @Parameter(description = "ID of the location to delete") @PathVariable Integer id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with ID: " + id, ex);
        }
    }

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
