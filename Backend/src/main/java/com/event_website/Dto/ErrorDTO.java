package com.event_website.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * @author Omar Alomair
 *
 * This is the DTO for any request that ends with an exception (4XX and 5XX responses)
 *
 * */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private int status;
    private String message;
    private ZonedDateTime timestamp;
    private String path;
}
