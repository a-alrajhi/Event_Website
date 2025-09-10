package com.event_website.Controller;
/**
 * @author Yazeed
 * This controller handles all the bookmark-related API requests,
 * including creating, retrieving, and deleting bookmarks.
 */
import com.event_website.Dto.BookmarkDTO;
import com.event_website.Service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping("/create")
    public ResponseEntity<BookmarkDTO> createBookmark(@RequestBody BookmarkDTO bookmarkDTO) {
        try {
            var createdBookmark = bookmarkService.createBookmark(bookmarkDTO);

            BookmarkDTO responseDTO = new BookmarkDTO(
                    createdBookmark.getId(),
                    createdBookmark.getEvent().getId(),
                    createdBookmark.getUser().getId()
            );

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookmarkDTO>> getAllBookmarks() {
        List<BookmarkDTO> bookmarks = bookmarkService.getAllBookmarks();
        return new ResponseEntity<>(bookmarks, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookmark(@PathVariable Integer id) {
        try {
            bookmarkService.deleteBookmarkById(id);
            return new ResponseEntity<>("Bookmark deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to delete Bookmark", HttpStatus.BAD_REQUEST);
        }
    }


}

