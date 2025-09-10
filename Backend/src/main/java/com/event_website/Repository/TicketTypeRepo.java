package com.event_website.Repository;

import com.event_website.Entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketTypeRepo extends JpaRepository<TicketType, Integer> {
    List<TicketType> findBySlot_Id(Integer slotId);
}
