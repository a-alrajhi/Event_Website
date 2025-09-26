package com.event_website.Request;

import com.event_website.Entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request DTO for updating an existing user.
 *
 * <p>Allows partial or full updates of user details. All fields are optional.</p>
 *
 * @author Omar Alomair
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UpdateUserRequest", description = "Request payload for updating user details. Fields are optional and allow partial updates.")
public class UpdateUserRequest {

    @Schema(description = "User's full name", example = "John Doe", nullable = true)
    private String name;

    @Schema(description = "User's phone number", example = "+1234567890", nullable = true)
    private String phoneNumber;

    @Schema(description = "User's email address", example = "user@example.com", nullable = true)
    private String email;

    @Schema(description = "User's password", example = "newSecret123", nullable = true)
    private String password;

    /**
     * Merges non-null updateRequest fields into the existingUser entity.
     *
     * @param existingUser The current User entity to update.
     * @param updateRequest The request containing updated fields.
     * @return The merged User entity with updated fields.
     */
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
