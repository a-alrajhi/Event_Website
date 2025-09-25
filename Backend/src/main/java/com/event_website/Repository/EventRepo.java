package com.event_website.Repository;

import com.event_website.Entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {

    // Search for events by name, Arabic name, description, or Arabic description with pagination
    Page<Event> findByNameContainingIgnoreCaseOrArNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrArDescriptionContainingIgnoreCase(
            String name, String arName, String description, String arDescription, Pageable pageable
    );

    // Fetch events by category IDs with pagination
    Page<Event> findByCategoryIdIn(List<Integer> categoryIds, Pageable pageable);

    // Search events by category IDs and other fields with pagination
    Page<Event> findByCategoryIdInAndNameContainingIgnoreCaseOrCategoryIdInAndArNameContainingIgnoreCaseOrCategoryIdInAndDescriptionContainingIgnoreCaseOrCategoryIdInAndArDescriptionContainingIgnoreCase(
            List<Integer> cat1, String name,
            List<Integer> cat2, String arName,
            List<Integer> cat3, String description,
            List<Integer> cat4, String arDescription,
            Pageable pageable
    );

    // Count total number of events
    long count();

    // Fetch events by category ID without pagination
    List<Event> findByCategoryId(Integer categoryId);

    // Fetch events by location ID (add this method)
    List<Event> findByLocation_Id(Integer locationId);

    // Fetch events by location ID with pagination (optional, depending on your use case)
    Page<Event> findByLocation_Id(Integer locationId, Pageable pageable);
}
