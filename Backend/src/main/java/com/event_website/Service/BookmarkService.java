package com.event_website.Service;
/**
 * @author Yazeed
 * Service class that handles business logic related to bookmarks,
 * including creation, retrieval, and deletion of bookmarks.
 */
import com.event_website.Entity.Bookmark;
import com.event_website.Dto.BookmarkDTO;
import com.event_website.Repository.BookmarkRepo;
import com.event_website.Repository.EventRepo;
import com.event_website.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepo bookmarkRepository;

    @Autowired
    private EventRepo eventRepository;

    @Autowired
    private UserRepo userRepository;

    // Create a new Bookmark
    public Bookmark createBookmark(BookmarkDTO bookmarkDTO) {
        // Check if the Event and User exist
        var event = eventRepository.findById(bookmarkDTO.getEventId()).orElseThrow(() -> new RuntimeException("Event not found"));
        var user = userRepository.findById(bookmarkDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the bookmark already exists
        if (bookmarkRepository.existsByEventAndUser(event, user)) {
            throw new RuntimeException("Bookmark already exists for this user and event");
        }

        // Create a new Bookmark entity
        Bookmark bookmark = new Bookmark();
        bookmark.setEvent(event);
        bookmark.setUser(user);

        // Save the bookmark to the database
        return bookmarkRepository.save(bookmark);
    }

    public List<BookmarkDTO> getAllBookmarks() {
        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        return bookmarks.stream()
                .map(bookmark -> new BookmarkDTO(
                        bookmark.getId(),
                        bookmark.getEvent().getId(),
                        bookmark.getUser().getId()))
                .collect(Collectors.toList());
    }

    public void deleteBookmarkById(Integer bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }

}
