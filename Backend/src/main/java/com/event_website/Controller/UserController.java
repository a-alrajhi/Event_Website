package com.event_website.Controller;

import com.event_website.Dto.TicketDTO;
import com.event_website.Dto.UserDTO;
import com.event_website.Entity.Ticket;
import com.event_website.Entity.User;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Logging.LogRequest;
import com.event_website.Request.RegisterRequest;
import com.event_website.Request.UpdateUserRequest;
import com.event_website.Service.TicketService;
import com.event_website.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Omar Alomair
 *
 * The admin uses this route to manage users.
 *
 * */

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @LogRequest(description = "Finding All Users")
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> findAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageLimit) {
        return ResponseEntity.ok(userService.findAll(page, pageLimit).stream().map(UserDTO::fromEntity).toList());
    }

    @LogRequest(description = "Finding User by Id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        UserDTO user = UserDTO.fromEntity(userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found")));
        return ResponseEntity.ok(user);
    }

    @LogRequest(description = "Finding User's Tickets")
    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<TicketDTO>> findTicketsById(@PathVariable Integer id) {
        return ResponseEntity.ok(ticketService.getUserTickets(id).stream().map(TicketDTO::fromEntity).toList());
    }

    @LogRequest(description = "Creating User")
    @PostMapping("/")
    public ResponseEntity<UserDTO> create(@RequestBody RegisterRequest registerRequest) throws Exception {
        User user = new User(registerRequest);
        userService.register(user);
        return ResponseEntity.ok(UserDTO.fromEntity(user));
    }


    @LogRequest(description = "Updating User")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UpdateUserRequest updateUserRequest, @PathVariable Integer id) throws Exception {
        User user = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User updatedUser = UpdateUserRequest.mergeUserUpdates(user, updateUserRequest);
        User userUpdated = userService.update(updatedUser);
        return ResponseEntity.ok(UserDTO.fromEntity(userUpdated));
    }

    @LogRequest(description = "Deleting User by Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Integer id) throws Exception {
        User user = userService.delete(id);
        return ResponseEntity.ok(UserDTO.fromEntity(user));
    }
}
