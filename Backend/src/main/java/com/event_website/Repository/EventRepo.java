package com.event_website.Repository;

import com.event_website.Entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {

    Page<Event> findByNameContainingIgnoreCaseOrArNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrArDescriptionContainingIgnoreCase(
            String name, String arName, String description, String arDescription, Pageable pageable
    );

    Page<Event> findByCategoryIdIn(List<Integer> categoryIds, Pageable pageable);

    Page<Event> findByCategoryIdInAndNameContainingIgnoreCaseOrCategoryIdInAndArNameContainingIgnoreCaseOrCategoryIdInAndDescriptionContainingIgnoreCaseOrCategoryIdInAndArDescriptionContainingIgnoreCase(
            List<Integer> cat1, String name,
            List<Integer> cat2, String arName,
            List<Integer> cat3, String description,
            List<Integer> cat4, String arDescription,
            Pageable pageable
    );

    long count();

}
