package com.event_website.Service;

import com.event_website.Dto.AdminStatsDTO;
import com.event_website.Dto.EventSimpleStatDTO;
import com.event_website.Dto.MonthlyStatDTO;
import com.event_website.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminStatService {

    @Autowired
    private UserRepo userRepo;
    @Autowired private EventRepo eventRepo;
    @Autowired private TicketRepo ticketRepo;
    @Autowired private SlotTicketTypeCapacityRepo slotTicketTypeCapacityRepo;
    @Autowired private BookmarkRepo bookmarkRepo;

    public AdminStatsDTO getDashboardStats() {
        AdminStatsDTO dto = new AdminStatsDTO();

        dto.setTotalUsers(userRepo.count());
        dto.setTotalEvents(eventRepo.count());
        dto.setTotalTicketsSold(ticketRepo.countAllTickets());

        dto.setTotalRevenue(slotTicketTypeCapacityRepo.getTotalRevenue());

        long checkedIn = ticketRepo.countCheckedInTickets();
        long total = dto.getTotalTicketsSold();
        dto.setOverallCheckInRate(total == 0 ? 0 : ((double) checkedIn / total) * 100);

        dto.setTopEventsBySales(ticketRepo.findTopEventsBySales(PageRequest.of(0, 5)));
        dto.setTopEventsByBookmarks(bookmarkRepo.findTopBookmarkedEvents(PageRequest.of(0, 5)));

        return dto;
    }

    public List<MonthlyStatDTO> getMonthlyRevenueAndSales() {
        return ticketRepo.getMonthlyRevenueAndSales();
    }

    public List<EventSimpleStatDTO> getTopEventsThisMonth(int topN) {
        return ticketRepo.findTopEventsThisMonth(topN);
    }
}
