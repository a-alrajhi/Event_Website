package com.event_website.Repository;

import com.event_website.Entity.Bookmark;
import com.event_website.Entity.Event;
import com.event_website.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepo extends JpaRepository<Bookmark, Integer> {
    boolean existsByEventAndUser(Event event, User user);

    void deleteById(Integer id);

}
