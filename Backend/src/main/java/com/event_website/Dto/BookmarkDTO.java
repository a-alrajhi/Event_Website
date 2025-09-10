package com.event_website.Dto;
/**
 * @author Yazeed
 * DTO class to transfer bookmark data between layers,
 * mapping entity data to a simpler structure.
 */
import com.event_website.Entity.Bookmark;
import com.event_website.Entity.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDTO {
    private Integer id;
    private Integer eventId;
    private Integer userId;

}
