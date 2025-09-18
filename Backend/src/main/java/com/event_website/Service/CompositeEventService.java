package com.event_website.Service;

import com.event_website.Dto.*;
import com.event_website.Entity.*;
import com.event_website.Request.CompositeCreateCapacity;
import com.event_website.Request.CompositeCreateEvent;
import com.event_website.Request.CreateCategoryRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private TicketTypeService ticketTypeService;

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

    public CompositeCreateEvent getComposite(int id) {
        Event eventEntity = eventService.findEventbyId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        EventDto event = EventDto.fromEntity(eventEntity);
        LocationDTO location = locationService.getLocationById(eventEntity.getLocation().getId());
        CategoryDTO category = categoryService.getCategoryById(eventEntity.getCategory().getId());
        List<SlotWithTicketTypesDTO> slots = slotService.findByEventId(id).stream().map((slot) -> {
            SlotWithTicketTypesDTO slotWithTicketTypesDTO = new SlotWithTicketTypesDTO();
            slotWithTicketTypesDTO.setId(slot.getId());
            slotWithTicketTypesDTO.setDate(slot.getDate());
            slotWithTicketTypesDTO.setStartTime(slot.getStartTime());
            slotWithTicketTypesDTO.setEndTime(slot.getEndTime());
            List<CompositeCreateCapacity> capacities = slotTicketTypeCapacityService.getBySlotId(slot.getId()).stream().map((capacity) -> {
                CompositeCreateCapacity compositeCreateCapacity = new CompositeCreateCapacity();
                compositeCreateCapacity.setCapacity(capacity.getCapacity());
                compositeCreateCapacity.setTtId(capacity.getTicketType().getId());
                return compositeCreateCapacity;
            }).toList();
            slotWithTicketTypesDTO.setTicketTypes(capacities);
            return slotWithTicketTypesDTO;
        }).toList();
        CompositeCreateEvent compositeCreateEvent = new CompositeCreateEvent();
        compositeCreateEvent.setEvent(event);
        compositeCreateEvent.setLocation(location);
        compositeCreateEvent.setCategory(category);
        compositeCreateEvent.setSlots(slots);
        return compositeCreateEvent;
    }


    @Transactional
    public CompositeCreateEvent updateCompositeEvent(int eventId, CompositeCreateEvent request) {
        // 1. Fetch existing event (with related slots & capacities)
        Event existingEvent = eventService.findEventbyId(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        // 2. Update event fields
        existingEvent.setName(request.getEvent().getName());
        existingEvent.setArName(request.getEvent().getArName());
        existingEvent.setDescription(request.getEvent().getDescription());
        existingEvent.setArDescription(request.getEvent().getArDescription());
        existingEvent.setPhotoUrl(request.getEvent().getPhotoUrl());
        // Update category / location if provided
        Category cat;
        Location loc;
        if (request.getCategory().getId() != null) {
            cat = categoryService.getCategoryById(request.getCategory().getId()).toEntity();
        }
        else {
            cat = categoryService.createCategory(new CreateCategoryRequest(request.getCategory())).toEntity();
        }
        existingEvent.setCategory(cat);
        if (request.getLocation().getId() != null) {
            loc = locationService.getLocationById(request.getLocation().getId()).toEntity();
        }
        else {
            loc = locationService.createLocation(request.getLocation()).toEntity();
        }
        existingEvent.setLocation(loc);
        eventService.save(existingEvent);

        // 3. Handle slots:
        //    a) Get existing slots list
        List<Slot> existingSlots = slotService.findByEventId(eventId);

        // Map of slotId -> Slot entity for quick lookup
        Map<Integer, Slot> existingSlotMap = existingSlots.stream()
                .collect(Collectors.toMap(Slot::getId, s -> s));

        // Also keep track of which slots are in the request
        Set<Integer> requestSlotIds = new HashSet<>();

        for (SlotWithTicketTypesDTO slotDTO : request.getSlots()) {
            if (slotDTO.getId() != null && existingSlotMap.containsKey(slotDTO.getId())) {
                // Slot exists: update it
                Slot slot = existingSlotMap.get(slotDTO.getId());
                slot.setDate(slotDTO.getDate());
                slot.setStartTime(slotDTO.getStartTime());
                slot.setEndTime(slotDTO.getEndTime());
                slotService.save(slot);
                requestSlotIds.add(slot.getId());

                // 4. Handle capacities within this slot
                //    a) fetch existing capacities for that slot
                List<SlotTicketTypeCapacity> existingCaps = slotTicketTypeCapacityService.getBySlotId(slot.getId());
                Map<Integer, SlotTicketTypeCapacity> capsMap = existingCaps.stream()
                        .collect(Collectors.toMap(cap -> cap.getTicketType().getId(), cap -> cap));

                // for each ticketType DTO in slotDTO
                for (CompositeCreateCapacity capDTO : slotDTO.getTicketTypes()) {
                    Integer ttId = capDTO.getTtId();
                    Integer capVal = capDTO.getCapacity();

                    if (capsMap.containsKey(ttId)) {
                        // update
                        SlotTicketTypeCapacity capEntity = capsMap.get(ttId);
                        capEntity.setCapacity(capVal);
                        slotTicketTypeCapacityService.save(capEntity); // you may need a save method
                        capsMap.remove(ttId);
                    } else {
                        // new capacity
                        slotTicketTypeCapacityService.createCapacity(slot.getId(), ttId, capVal);
                    }
                }

                // any capacity left in capsMap not in request -> delete those
                for (SlotTicketTypeCapacity toDelete : capsMap.values()) {
                    slotTicketTypeCapacityService.deleteById(toDelete.getId());
                }

            } else {
                // New slot (add it)
                Slot newSlot = slotDTO.toSlot();
                newSlot.setEvent(existingEvent);
                Slot saved = slotService.save(newSlot);

                // Add its capacities
                for (CompositeCreateCapacity capDTO : slotDTO.getTicketTypes()) {
                    slotTicketTypeCapacityService.createCapacity(saved.getId(), capDTO.getTtId(), capDTO.getCapacity());
                }
                requestSlotIds.add(saved.getId());
            }
        }

        // 5. Delete slots removed in request
        for (Slot existingSlot : existingSlots) {
            if (!requestSlotIds.contains(existingSlot.getId())) {
                slotTicketTypeCapacityService.deleteBySlotId(existingSlot.getId()); // delete capacities first
                slotService.delete(existingSlot.getId());
            }
        }

        // 6. Build response same as getComposite
        EventDto eventDto = EventDto.fromEntity(existingEvent);
        LocationDTO locDto = locationService.getLocationById(existingEvent.getLocation().getId());
        CategoryDTO catDto = categoryService.getCategoryById(existingEvent.getCategory().getId());
        List<SlotWithTicketTypesDTO> slotsDto = slotService.findByEventId(eventId).stream().map(slot -> {
            SlotWithTicketTypesDTO sdt = new SlotWithTicketTypesDTO();
            sdt.setId(slot.getId());
            sdt.setDate(slot.getDate());
            sdt.setStartTime(slot.getStartTime());
            sdt.setEndTime(slot.getEndTime());
            List<CompositeCreateCapacity> capDtos = slotTicketTypeCapacityService.getBySlotId(slot.getId()).stream().map(cap -> {
                CompositeCreateCapacity cd = new CompositeCreateCapacity();
                cd.setTtId(cap.getTicketType().getId());
                cd.setCapacity(cap.getCapacity());
                return cd;
            }).toList();
            sdt.setTicketTypes(capDtos);
            return sdt;
        }).toList();

        CompositeCreateEvent dto = new CompositeCreateEvent();
        dto.setEvent(eventDto);
        dto.setLocation(locDto);
        dto.setCategory(catDto);
        dto.setSlots(slotsDto);

        return dto;
    }

}
