package com.event_website.Controller;

import com.event_website.Dto.UserDTO;
import com.event_website.Entity.User;
import com.event_website.Logging.LogRequest;
import com.event_website.Request.RegisterRequest;
import com.event_website.Request.UpdateUserRequest;
import com.event_website.Service.UserService;
import com.event_website.Utils.SecurityUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Omar Alomair
 *
 * This route is used by the normal (logged in) user to manage their profile
 *
 * */

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    SecurityUtil securityUtil;
    @Autowired
    UserService userService;

    @LogRequest(description = "Getting profile")
    @GetMapping("/")
    public ResponseEntity<UserDTO> getProfile() throws Exception {
        UserDTO userDTO = UserDTO.fromEntity(securityUtil.getCurrentUser());
        return ResponseEntity.ok(userDTO);
    }
    @LogRequest(description = "Updating profile")
    @PutMapping("/")
    public ResponseEntity<UserDTO> update(@RequestBody UpdateUserRequest updateUserRequest) throws Exception {
        User user = securityUtil.getCurrentUser();
        User updatedUser = UpdateUserRequest.mergeUserUpdates(user, updateUserRequest);
        UserDTO userDTO = UserDTO.fromEntity(userService.update(updatedUser));
        return ResponseEntity.ok(userDTO);
    }
}
