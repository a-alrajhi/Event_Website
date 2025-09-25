package com.event_website.Controller;

import com.event_website.Dto.BookmarkDTO;
import com.event_website.Dto.ErrorDTO;
import com.event_website.Entity.User;
import com.event_website.Service.BookmarkService;
import com.event_website.Utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yazeed
 * This controller handles all the bookmark-related API requests,
 * including creating, retrieving, and deleting bookmarks.
 */
@RestController
@RequestMapping("/bookmarks")
@Tag(name = "Bookmarks", description = "API for managing user bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private SecurityUtil securityUtil;

    @Operation(
            summary = "Create a new bookmark",
            description = "Creates a bookmark for a user and event.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Bookmark created successfully",
                            content = @Content(schema = @Schema(implementation = BookmarkDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid bookmark data",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<BookmarkDTO> createBookmark(@RequestBody BookmarkDTO bookmarkDTO) {
        try {
            User user = securityUtil.getCurrentUser();
            int userId = bookmarkDTO.getUserId() != null ? bookmarkDTO.getUserId() : user.getId();
            bookmarkDTO.setUserId(userId);
            var createdBookmark = bookmarkService.createBookmark(bookmarkDTO);

            BookmarkDTO responseDTO = new BookmarkDTO(
                    createdBookmark.getId(),
                    createdBookmark.getEvent().getId(),
                    createdBookmark.getUser().getId()
            );

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Your global exception handler should handle this, but documented here for Swagger
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Get all bookmarks",
            description = "Retrieves all bookmarks for all users.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Bookmarks retrieved successfully",
                            content = @Content(schema = @Schema(implementation = BookmarkDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<BookmarkDTO>> getAllBookmarks() {
        List<BookmarkDTO> bookmarks = bookmarkService.getAllBookmarks();
        return new ResponseEntity<>(bookmarks, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a bookmark by ID",
            description = "Deletes a bookmark with the given ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Bookmark deleted successfully",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid bookmark ID or bookmark not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookmark(@PathVariable Integer id) {
        try {
            bookmarkService.deleteBookmarkById(id);
            return new ResponseEntity<>("Bookmark deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            // Again, global handler will normally catch this
            return new ResponseEntity<>("Failed to delete Bookmark", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBookmark(@RequestBody BookmarkDTO bookmarkDTO) {
        User user = securityUtil.getCurrentUser();
        bookmarkService.deleteBookmarkByUserAndEvent(user.getId(), bookmarkDTO.getEventId());
        return new ResponseEntity<>("Bookmark deleted successfully", HttpStatus.OK);
    }
}
