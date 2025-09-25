package com.event_website.Service;

import com.event_website.Dto.EventDto;
import com.event_website.Dto.LocationDTO;
import com.event_website.Entity.Location;
import com.event_website.Repository.LocationRepo;
import com.event_website.Repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepo locationRepository;
    private final EventRepo eventRepo;

    @Autowired
    public LocationService(LocationRepo locationRepository, EventRepo eventRepo) {
        this.locationRepository = locationRepository;
        this.eventRepo = eventRepo;
    }

    // Create a new location
    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = new Location();
        location.setName(locationDTO.getName());
        location.setAddress(locationDTO.getAddress());
        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());
        location.setAdditionalInfo(locationDTO.getAdditionalInfo());

        // Save the new location to the database
        Location savedLocation = locationRepository.save(location);

        // Return saved location as a DTO with its associated events
        List<EventDto> events = eventRepo.findByLocation_Id(savedLocation.getId()).stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());

        return new LocationDTO(
                savedLocation.getId(),
                savedLocation.getName(),
                savedLocation.getAddress(),
                savedLocation.getLatitude(),
                savedLocation.getLongitude(),
                savedLocation.getAdditionalInfo(),
                events
        );
    }

    public LocationDTO updateLocation(Integer id, LocationDTO locationDTO) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));

        location.setName(locationDTO.getName());
        location.setAddress(locationDTO.getAddress());
        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());
        location.setAdditionalInfo(locationDTO.getAdditionalInfo());

        // Save the updated location to the database
        Location updatedLocation = locationRepository.save(location);

        // Return the updated location as a DTO with its associated events
        List<EventDto> events = eventRepo.findByLocation_Id(updatedLocation.getId()).stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());

        return new LocationDTO(
                updatedLocation.getId(),
                updatedLocation.getName(),
                updatedLocation.getAddress(),
                updatedLocation.getLatitude(),
                updatedLocation.getLongitude(),
                updatedLocation.getAdditionalInfo(),
                events
        );
    }

    // Delete a location
    public void deleteLocation(Integer id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));

        // Delete the location from the database
        locationRepository.delete(location);
    }

    // Get all locations with pagination
    public List<LocationDTO> getAllLocations(int page, int size) {
        //List<Location> locations = locationRepository.findAll(PageRequest.of(page, size)).getContent();
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(location -> {
                    // Fetch events for each location
                    List<EventDto> events = eventRepo.findByLocation_Id(location.getId()).stream()
                            .map(EventDto::fromEntity)
                            .collect(Collectors.toList());
                    return new LocationDTO(
                            location.getId(),
                            location.getName(),
                            location.getAddress(),
                            location.getLatitude(),
                            location.getLongitude(),
                            location.getAdditionalInfo(),
                            events
                    );
                })
                .collect(Collectors.toList());
    }

    // Get location by ID with its associated events
    public LocationDTO getLocationById(Integer id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));

        // Fetch events for the specific location
        List<EventDto> events = eventRepo.findByLocation_Id(id).stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());

        return new LocationDTO(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getAdditionalInfo(),
                events
        );
    }

    public List<EventDto> getEventsByLocationId(Integer locationId) {
        return eventRepo.findByLocation_Id(locationId).stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }
}
