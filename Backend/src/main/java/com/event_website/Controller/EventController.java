package com.event_website.Controller;


import com.event_website.Dto.EventDto;
import com.event_website.Entity.Category;
import com.event_website.Entity.Event;
import com.event_website.Service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/Event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
//    private final CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<EventDto> createOrUpdate(@RequestBody Event event) {
        Event saved = eventService.save(event);
        return ResponseEntity.ok(EventDto.fromEntity(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById (@PathVariable Integer id){
        return eventService.findEventbyId(id)
                .map(EventDto::fromEntity)
                .map((ResponseEntity::ok))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(defaultValue = "0") int page) {  // page number
        int size = 30; // 30 events per page

        Page<EventDto> eventsPage = eventService.findAllEvent(page, size)
                .map(EventDto::fromEntity);

        var response = new java.util.HashMap<String, Object>();
        response.put("content", eventsPage.getContent());
        response.put("page", eventsPage.getNumber());
        response.put("size", eventsPage.getSize());
        response.put("totalElements", eventsPage.getTotalElements());
        response.put("totalPages", eventsPage.getTotalPages());
        response.put("last", eventsPage.isLast());

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (eventService.findEventbyId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}