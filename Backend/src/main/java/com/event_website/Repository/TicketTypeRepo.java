/**
 * Repository interface for performing CRUD operations on TicketType entities.
 *
 * <p>Extends JpaRepository to provide built-in JPA methods and includes a custom method to find a
 * TicketType by name.
 *
 * <p>Used by TicketTypeService.
 *
 * <p>Methods: - findByName(String name) - All standard JpaRepository methods (save, findById,
 * deleteById, etc.)
 *
 * @author Abdulrahman Al Rajhi
 * @author Omar Alomair
 * @since 10-09-2025
 * @version 1.0
 */
package com.event_website.Repository;

import com.event_website.Entity.TicketType;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeRepo extends JpaRepository<TicketType, Integer> {
    List<TicketType> findBySlot_Id(Integer slotId);
    Optional<TicketType> findByName(String name);
}
