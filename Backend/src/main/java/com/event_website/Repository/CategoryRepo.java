package com.event_website.Repository;

import com.event_website.Entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Category} entity.
 *
 * <p>Provides basic CRUD operations and custom query methods for category lookup and validation.
 *
 * <p>Used primarily by the service layer to interact with the database.
 *
 * @author Abdulrahman
 * @author Omar Alomair
 * @since 09-09-2025
 * @since 1.0
 */
public interface CategoryRepo extends JpaRepository<Category, Integer> {
  Optional<Category> findByName(String name);

  Optional<Category> findByArName(String arName);

  boolean existsByName(String name);
}
