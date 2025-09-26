package com.event_website.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Create capacity request for a ticket type in a slot")
public class CompositeCreateCapacity {

    @NotNull
    @Schema(description = "ID of the ticket type", example = "1")
    private Integer ttId;

    @NotNull
    @Min(value = 0, message = "Capacity must be non-negative")
    @Schema(description = "Capacity for this ticket type", example = "100")
    private Integer capacity;
}
