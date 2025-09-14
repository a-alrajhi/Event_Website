package com.event_website.Controller;

import com.event_website.Dto.AdminStatsDTO;
import com.event_website.Dto.EventSimpleStatDTO;
import com.event_website.Dto.MonthlyStatDTO;
import com.event_website.Service.AdminStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/stats")
public class AdminStatController {

    @Autowired
    private AdminStatService adminStatsService;

    @GetMapping
    public ResponseEntity<AdminStatsDTO> getAdminDashboardStats() {
        AdminStatsDTO stats = adminStatsService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/monthly-sales")
    public ResponseEntity<List<MonthlyStatDTO>> getMonthlySalesAndRevenue() {
        return ResponseEntity.ok(adminStatsService.getMonthlyRevenueAndSales());
    }

    @GetMapping("/monthly-top/{count}")
    public ResponseEntity<List<EventSimpleStatDTO>> getMonthlySalesAndRevenue(@PathVariable int count) {
        return ResponseEntity.ok(adminStatsService.getTopEventsThisMonth(count));
    }
}
