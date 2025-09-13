package com.event_website.Service;

import com.event_website.Entity.Slot;
import com.event_website.Entity.SlotTicketTypeCapacity;
import com.event_website.Entity.TicketType;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Repository.SlotRepo;
import com.event_website.Repository.SlotTicketTypeCapacityRepo;
import com.event_website.Repository.TicketTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SlotTicketTypeCapacityService {

    @Autowired
    private SlotTicketTypeCapacityRepo capacityRepo;

    @Autowired
    private SlotRepo slotRepo;

    @Autowired
    private TicketTypeRepo ticketTypeRepo;

    public SlotTicketTypeCapacity createCapacity(Integer slotId, Integer ticketTypeId, Integer capacityValue) {
        Slot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found"));

        TicketType ticketType = ticketTypeRepo.findById(ticketTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket type not found"));

        SlotTicketTypeCapacity capacity = new SlotTicketTypeCapacity();
        capacity.setSlot(slot);
        capacity.setTicketType(ticketType);
        capacity.setCapacity(capacityValue);

        return capacityRepo.save(capacity);
    }

    public List<SlotTicketTypeCapacity> getByTicketTypeId(Integer ticketTypeId) {
        return capacityRepo.findByTicketType_Id(ticketTypeId);
    }

    public List<SlotTicketTypeCapacity> getBySlotId(Integer slotId) {
        return capacityRepo.findBySlot_Id(slotId);
    }

    public List<SlotTicketTypeCapacity> getBySlotIds(List<Integer> slotIds) {
        if (slotIds == null || slotIds.isEmpty()) return List.of();
        return capacityRepo.findBySlot_IdIn(slotIds);
    }

    public Optional<SlotTicketTypeCapacity> getBySlotAndTicketType(Integer slotId, Integer ticketTypeId) {
        return capacityRepo.findBySlot_IdAndTicketType_Id(slotId, ticketTypeId);
    }
}
