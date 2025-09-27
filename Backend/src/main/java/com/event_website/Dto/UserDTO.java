package com.event_website.Dto;

import com.event_website.Entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object representing user details")
public class UserDTO {

    @Schema(description = "Unique identifier of the user", example = "123")
    private Integer id;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String name;

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Phone number of the user", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "User's bookmarked events")
    private List<EventDtoDetalis> bookmarks;

    @Schema(description = "User's bookmarked events")
    private List<TicketDTO> tickets;

    @Schema(description = "User's tickets grouped by type")
    private List<TicketWithSameTypeDTO> groupedTickets;

    @Schema(description = "User role", example = "USER")
    private String role;

    public static UserDTO fromEntity(User user) {
        if (user == null) return null;

        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
