package com.event_website.Repository;

import com.event_website.Entity.Ticket;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Ticket entity.
 *
 * <p>Provides CRUD operations and custom query methods for accessing tickets in the database.
 *
 * <p>Custom Methods: - findByTicketCode(String ticketCode): Find a ticket by its unique code
 *
 * <p>Extends: - JpaRepository<Ticket, Integer>
 *
 * @since 09-09-2025
 * @version 1.0
 * @author Abdulrahman Al Rajhi
 * @author Omar Alomair
 */
@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer>, TicketCustomRepo {
  Optional<Ticket> findByTicketCode(String ticketCode);

  List<Ticket> findByUserId(Integer userId);

  @Query("SELECT COUNT(t) FROM Ticket t")
  long countAllTickets();

  @Query("SELECT COUNT(t) FROM Ticket t WHERE t.checkedIn = true")
  long countCheckedInTickets();

  @Query(
      "SELECT COUNT(t) FROM Ticket t "
          + "WHERE t.slotTicketTypeCapacity.slot.id = :slotId "
          + "AND t.slotTicketTypeCapacity.ticketType.id = :ticketTypeId")
  long countTicketsBySlotAndType(Integer slotId, Integer ticketTypeId);
}
