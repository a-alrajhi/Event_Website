package com.event_website.Service;

import com.event_website.Entity.Event;
import com.event_website.Repository.EventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;


    public Event save(Event event) {
        return eventRepo.save(event);
    }


    public Optional<Event> findEventbyId(Integer id) {
        return eventRepo.findById(id);
    }


    public void deleteById(Integer id) {
        eventRepo.deleteById(id);
    }


    public Page<Event> findAllEvent(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventRepo.findAll(pageable);
    }

    public Page<Event> searchEvents(String keyword, List<Integer> categoryIds, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (keyword != null && (categoryIds == null || categoryIds.isEmpty())) {
            return eventRepo.findByNameContainingIgnoreCaseOrArNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrArDescriptionContainingIgnoreCase(
                    keyword, keyword, keyword, keyword, pageable
            );
        }

        if ((keyword == null || keyword.isEmpty()) && categoryIds != null && !categoryIds.isEmpty()) {
            return eventRepo.findByCategoryIdIn(categoryIds, pageable);
        }

        if (keyword != null && categoryIds != null && !categoryIds.isEmpty()) {
            return eventRepo.findByCategoryIdInAndNameContainingIgnoreCaseOrCategoryIdInAndArNameContainingIgnoreCaseOrCategoryIdInAndDescriptionContainingIgnoreCaseOrCategoryIdInAndArDescriptionContainingIgnoreCase(
                    categoryIds, keyword,
                    categoryIds, keyword,
                    categoryIds, keyword,
                    categoryIds, keyword,
                    pageable
            );
        }

        return eventRepo.findAll(pageable);
    }
}
