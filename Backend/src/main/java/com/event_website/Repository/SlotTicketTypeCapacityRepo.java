package com.event_website.Repository;

import com.event_website.Entity.SlotTicketTypeCapacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SlotTicketTypeCapacityRepo extends JpaRepository<SlotTicketTypeCapacity, Integer> {

    List<SlotTicketTypeCapacity> findBySlot_Id(Integer slotId);

    List<SlotTicketTypeCapacity> findByTicketType_Id(Integer ticketTypeId);

    Optional<SlotTicketTypeCapacity> findBySlot_IdAndTicketType_Id(Integer slotId, Integer ticketTypeId);

    List<SlotTicketTypeCapacity> findBySlot_IdIn(List<Integer> slotIds);

    @Query("""
        SELECT COALESCE(SUM(tt.price), 0)
        FROM Ticket t
        JOIN t.slotTicketTypeCapacity sttc
        JOIN TicketType tt ON tt.id = sttc.ticketType.id
    """)
    BigDecimal getTotalRevenue();

    void deleteAllBySlot_Id(Integer slotId);

    List<SlotTicketTypeCapacity> getAllBySlot_Id(Integer slotId);
}
