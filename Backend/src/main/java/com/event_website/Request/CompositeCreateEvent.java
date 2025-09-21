package com.event_website.Request;

import com.event_website.Dto.EventDto;
import com.event_website.Dto.LocationDTO;
import com.event_website.Dto.CategoryDTO;
import com.event_website.Dto.SlotWithTicketTypesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompositeCreateEvent {
    private EventDto event;
    private LocationDTO location;
    private CategoryDTO category;
    private List<SlotWithTicketTypesDTO> slots;
}
