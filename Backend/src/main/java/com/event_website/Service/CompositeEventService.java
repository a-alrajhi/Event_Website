package com.event_website.Service;

import com.event_website.Dto.*;
import com.event_website.Entity.*;
import com.event_website.Request.CompositeCreateCapacity;
import com.event_website.Request.CompositeCreateEvent;
import com.event_website.Request.CreateCategoryRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CompositeEventService {
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private final LocationService locationService;
    @Autowired
    private final EventService eventService;
    @Autowired
    private final SlotService slotService;
    @Autowired
    private final SlotTicketTypeCapacityService slotTicketTypeCapacityService;

    public CreateCompositeEventDTO createCompositeEvent(CompositeCreateEvent request) {
        // Handle category
        Category category = (request.getCategory().getId() != null)
                ? categoryService.getCategoryById(request.getCategory().getId()).toEntity()
                : categoryService.createCategory(new CreateCategoryRequest(request.getCategory())).toEntity();

        // Handle location
        Location location = (request.getLocation().getId() != null)
                ? locationService.getLocationById(request.getLocation().getId()).toEntity()
                : locationService.createLocation(request.getLocation()).toEntity();

        // Create event
        Event event = request.getEvent().toEntity(category);
        event.setLocation(location);
        Event savedEvent = eventService.save(event);

        // Save slots
        List<Slot> savedSlots = new ArrayList<>();
        for (SlotWithTicketTypesDTO slotDTO : request.getSlots()) {
            Slot slot = slotDTO.toSlot();
            slot.setEvent(savedEvent); // or setEventId
            Slot savedSlot = slotService.save(slot);
            savedSlots.add(savedSlot);
        }

        // Save capacities (linked to the saved slot IDs)
        List<SlotTicketTypeCapacity> capacities = new ArrayList<>();
        for (int i = 0; i < savedSlots.size(); i++) {
            SlotWithTicketTypesDTO originalSlotDTO = request.getSlots().get(i);
            Slot savedSlot = savedSlots.get(i);
            System.out.println("slot id: " + savedSlot.getId());

            for (CompositeCreateCapacity ticketTypeDTO : originalSlotDTO.getTicketTypes()) {
                System.out.println("TT id: " + ticketTypeDTO.getTtId());
                SlotTicketTypeCapacity cap = slotTicketTypeCapacityService.createCapacity(
                        savedSlot.getId(),
                        ticketTypeDTO.getTtId(),
                        ticketTypeDTO.getCapacity()
                );
                capacities.add(cap);
            }
        }

        // Build response DTO
        CreateCompositeEventDTO dto = new CreateCompositeEventDTO();
        dto.setEvent(EventDto.fromEntity(savedEvent));
        dto.setSlots(savedSlots.stream().map(SlotDTO::fromEntity).toList());
        dto.setCapacities(capacities.stream().map(SlotTicketTypeCapacityDTO::fromEntity).toList());

        return dto;
    }
}
