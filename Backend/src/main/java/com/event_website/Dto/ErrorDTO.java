package com.event_website.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * Represents the structure of an error response.
 * Used for all client-side (4XX) and server-side (5XX) errors.
 *
 * Author: Omar Alomair
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Standard error response for failed API calls.")
public class ErrorDTO {

    @Schema(description = "HTTP status code of the error", example = "404")
    private int status;

    @Schema(description = "Detailed message about the error", example = "Resource not found")
    private String message;

    @Schema(description = "The timestamp when the error occurred", example = "2025-09-21T15:22:05.123+03:00")
    private ZonedDateTime timestamp;

    @Schema(description = "The path of the request that caused the error", example = "/api/events/123")
    private String path;
}
