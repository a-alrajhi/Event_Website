package com.event_website.Dto;


import com.event_website.Entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private Integer id;
    private String name;
    private String arName;
    private String description;
    private String arDescription;
    private String photoUrl;
    private Boolean hasAssignedSeating;
    private Integer categoryId;

    public static EventDto fromEntity(Event event) {
        if (event == null) return null;

        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setArName(event.getArName());
        dto.setDescription(event.getDescription());
        dto.setArDescription(event.getArDescription());
        dto.setPhotoUrl(event.getPhotoUrl());
        dto.setHasAssignedSeating(event.getHasAssignedSeating());
        return dto;
    }
};
