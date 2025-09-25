package com.event_website.Controller;

import com.event_website.Dto.TicketDTO;
import com.event_website.Dto.UserDTO;
import com.event_website.Dto.ErrorDTO;
import com.event_website.Entity.Ticket;
import com.event_website.Entity.User;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Logging.LogRequest;
import com.event_website.Request.RegisterRequest;
import com.event_website.Request.UpdateUserRequest;
import com.event_website.Service.TicketService;
import com.event_website.Service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Admin APIs for managing users and their tickets")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @LogRequest(description = "Finding All Users")
    @Operation(
            summary = "Get all users (Admin only)",
            description = "Returns a paginated list of all registered users.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users retrieved",
                            content = @Content(schema = @Schema(implementation = UserDTO.class)))
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> findAll(@RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer pageLimit) {
        return ResponseEntity.ok(userService.findAll(page, pageLimit).stream()
                .map(UserDTO::fromEntity)
                .toList());
    }

    @LogRequest(description = "Finding User by Id")
    @Operation(
            summary = "Get user by ID",
            description = "Fetches a user by their unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        UserDTO user = UserDTO.fromEntity(
                userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"))
        );
        return ResponseEntity.ok(user);
    }

    @LogRequest(description = "Finding User's Tickets")
    @Operation(
            summary = "Get all tickets for a user",
            description = "Returns all tickets associated with a specific user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tickets retrieved",
                            content = @Content(schema = @Schema(implementation = TicketDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<TicketDTO>> findTicketsById(@PathVariable Integer id) {
        return ResponseEntity.ok(ticketService.getUserTickets(id).stream()
                .map(TicketDTO::fromEntity)
                .toList());
    }

    @LogRequest(description = "Creating User")
    @Operation(
            summary = "Register a new user (Admin only)",
            description = "Registers a new user using the provided registration details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User created",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @PostMapping("/")
    public ResponseEntity<UserDTO> create(@RequestBody RegisterRequest registerRequest) throws Exception {
        User user = new User(registerRequest);
        userService.register(user);
        return ResponseEntity.ok(UserDTO.fromEntity(user));
    }

    @LogRequest(description = "Updating User")
    @Operation(
            summary = "Update user by ID",
            description = "Updates user details (Admin only).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UpdateUserRequest updateUserRequest,
                                          @PathVariable Integer id) throws Exception {
        User user = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User updatedUser = UpdateUserRequest.mergeUserUpdates(user, updateUserRequest);
        User userUpdated = userService.update(updatedUser);
        return ResponseEntity.ok(UserDTO.fromEntity(userUpdated));
    }

    @LogRequest(description = "Deleting User by Id")
    @Operation(
            summary = "Delete user by ID",
            description = "Deletes a user and returns the deleted user's info (Admin only).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User deleted",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Integer id) throws Exception {
        User user = userService.delete(id);
        return ResponseEntity.ok(UserDTO.fromEntity(user));
    }
}
