package com.event_website.Repository;

import com.event_website.Dto.EventStatDTO;
import com.event_website.Entity.Bookmark;
import com.event_website.Entity.Event;
import com.event_website.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepo extends JpaRepository<Bookmark, Integer> {
    boolean existsByEventAndUser(Event event, User user);

    void deleteById(Integer id);

    @Query("""
        SELECT new com.event_website.Dto.EventStatDTO(
            e.id, e.name, COUNT(b.id)
        )
        FROM Event e
        LEFT JOIN Bookmark b ON b.event.id = e.id
        GROUP BY e.id
        ORDER BY COUNT(b.id) DESC
    """)
    List<EventStatDTO> findTopBookmarkedEvents(Pageable pageable);

}
