package com.event_website.Request;

import com.event_website.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String name;
    private String phoneNumber;
    private String email;
    private String password;

    public static User mergeUserUpdates(User existingUser, UpdateUserRequest updateRequest) {
        if (updateRequest.getName() != null) {
            existingUser.setName(updateRequest.getName());
        }
        if (updateRequest.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updateRequest.getPhoneNumber());
        }
        if (updateRequest.getEmail() != null) {
            existingUser.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getPassword() != null) {
            existingUser.setPassword(updateRequest.getPassword());
        }

        return existingUser;
    }
}
