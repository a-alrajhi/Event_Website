package com.event_website.Repository;

import com.event_website.Entity.LocationSeatTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationSeatTemplateRepo extends JpaRepository<LocationSeatTemplate, Integer> {
}
