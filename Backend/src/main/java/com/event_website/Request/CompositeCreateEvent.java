package com.event_website.Request;

import com.event_website.Dto.EventDto;
import com.event_website.Dto.LocationDTO;
import com.event_website.Dto.CategoryDTO;
import com.event_website.Dto.SlotWithTicketTypesDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Composite request to create an event with location, category, and slots with ticket types")
public class CompositeCreateEvent {

    @NotNull
    @Valid
    @Schema(description = "Details of the event")
    private EventDto event;

    @NotNull
    @Valid
    @Schema(description = "Location details for the event")
    private LocationDTO location;

    @NotNull
    @Valid
    @Schema(description = "Category details for the event")
    private CategoryDTO category;

    @NotEmpty
    @Valid
    @Schema(description = "List of slots with ticket types and capacities")
    private List<SlotWithTicketTypesDTO> slots;
}
