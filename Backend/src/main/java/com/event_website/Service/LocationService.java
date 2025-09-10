package com.event_website.Service;

import com.event_website.Dto.LocationDTO;
import com.event_website.Entity.Location;
import com.event_website.Repository.LocationRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepo locationRepository;

    // Constructor injection
    public LocationService(LocationRepo locationRepository) {
        this.locationRepository = locationRepository;
    }

    // Convert Entity -> DTO
    private LocationDTO mapToDTO(Location location) {
        return new LocationDTO(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getAdditionalInfo()
        );
    }

    // Convert DTO -> Entity
    private Location mapToEntity(LocationDTO locationDTO) {
        Location location = new Location();
        location.setName(locationDTO.getName());
        location.setAddress(locationDTO.getAddress());
        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());
        location.setAdditionalInfo(locationDTO.getAdditionalInfo());
        return location;
    }

    // Create a new location
    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = mapToEntity(locationDTO);
        Location savedLocation = locationRepository.save(location);
        return mapToDTO(savedLocation);
    }

    // Get all locations
    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get location by ID
    public LocationDTO getLocationById(Integer id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));
        return mapToDTO(location);
    }

    // Update location
    public LocationDTO updateLocation(Integer id, LocationDTO locationDTO) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));

        existingLocation.setName(locationDTO.getName());
        existingLocation.setAddress(locationDTO.getAddress());
        existingLocation.setLatitude(locationDTO.getLatitude());
        existingLocation.setLongitude(locationDTO.getLongitude());
        existingLocation.setAdditionalInfo(locationDTO.getAdditionalInfo());

        Location updatedLocation = locationRepository.save(existingLocation);
        return mapToDTO(updatedLocation);
    }

    // Delete location
    public void deleteLocation(Integer id) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));
        locationRepository.delete(existingLocation);
    }
}
