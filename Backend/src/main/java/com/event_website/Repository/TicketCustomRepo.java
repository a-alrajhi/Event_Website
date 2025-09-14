package com.event_website.Repository;

import com.event_website.Dto.EventSimpleStatDTO;
import com.event_website.Dto.EventStatDTO;
import com.event_website.Dto.MonthlyStatDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketCustomRepo {
    List<EventStatDTO> findTopEventsBySales(Pageable pageable);
    List<MonthlyStatDTO> getMonthlyRevenueAndSales();
    List<EventSimpleStatDTO> findTopEventsThisMonth(int topN);
}
