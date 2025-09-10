package com.event_website.Repository;

import com.event_website.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SlotRepo extends JpaRepository<Slot, Integer> {
    List<Slot> findByEvent_Id(Integer eventId);

    List<Slot> findByDate(LocalDate date);
}
