package com.event_website.Controller;

import com.event_website.Dto.EventDtoDetalis;
import com.event_website.Dto.TicketDTO;
import com.event_website.Dto.TicketWithSameTypeDTO;
import com.event_website.Dto.UserDTO;
import com.event_website.Dto.ErrorDTO;
import com.event_website.Entity.User;
import com.event_website.Logging.LogRequest;
import com.event_website.Request.UpdateUserRequest;
import com.event_website.Service.BookmarkService;
import com.event_website.Service.EventService;
import com.event_website.Service.TicketService;
import com.event_website.Service.UserService;
import com.event_website.Utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Omar Alomair
 *
 * This route is used by the normal (logged in) user to manage their profile.
 */

@RestController
@RequestMapping("/profile")
@Tag(name = "User Profile", description = "Endpoints for logged-in users to manage their profile")
public class ProfileController {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private UserService userService;
    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private EventService eventService;
    @Autowired
    private TicketService ticketService;

    @LogRequest(description = "Getting profile")
    @Operation(
            summary = "Get logged-in user's profile",
            description = "Returns the profile of the currently authenticated user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User profile retrieved successfully",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - user not logged in",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @GetMapping("/")
    public ResponseEntity<UserDTO> getProfile() throws Exception {
        User user = securityUtil.getCurrentUser();
        UserDTO userDTO = UserDTO.fromEntity(user);
        List<EventDtoDetalis> bookmarks = bookmarkService.getAllBookmarksByUser(user.getId())
                .stream()
                .map(bookmark -> eventService.getEventDetails(bookmark.getEventId())).toList();

        userDTO.setBookmarks(bookmarks);
      List<TicketWithSameTypeDTO> groupedTickets = ticketService.getUserGroupedTickets(user.getId());
      userDTO.setGroupedTickets(groupedTickets);

      //        List<TicketDTO> tickets = ticketService.getUserTickets(user.getId()).stream().map(TicketDTO::fromEntity).toList();
      //        userDTO.setTickets(tickets);

      return ResponseEntity.ok(userDTO);
    }

    @LogRequest(description = "Updating profile")
    @Operation(
            summary = "Update logged-in user's profile",
            description = "Updates the profile details of the currently authenticated user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User profile updated successfully",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input or bad request",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - user not logged in",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @PutMapping("/")
    public ResponseEntity<UserDTO> update(@RequestBody UpdateUserRequest updateUserRequest) throws Exception {
        User user = securityUtil.getCurrentUser();
        User updatedUser = UpdateUserRequest.mergeUserUpdates(user, updateUserRequest);
        UserDTO userDTO = UserDTO.fromEntity(userService.update(updatedUser));
        return ResponseEntity.ok(userDTO);
    }
}
