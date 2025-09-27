package com.event_website.Controller;

import com.event_website.Dto.AdminStatsDTO;
import com.event_website.Dto.ErrorDTO;
import com.event_website.Dto.EventSimpleStatDTO;
import com.event_website.Dto.MonthlyStatDTO;
import com.event_website.Service.AdminStatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/stats")
@Tag(name = "Admin Stats", description = "Admin dashboard statistics for revenue, sales, and top events")
public class AdminStatController {

    @Autowired
    private AdminStatService adminStatsService;

    @Operation(
            summary = "Get overall admin dashboard statistics",
            description = "Returns total revenue, total users, total events, tickets sold, and check-in rate.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Admin dashboard stats retrieved successfully",
                            content = @Content(schema = @Schema(implementation = AdminStatsDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminStatsDTO> getAdminDashboardStats() {
        AdminStatsDTO stats = adminStatsService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }


    @Operation(
            summary = "Get monthly revenue and ticket sales",
            description = "Returns a list of monthly statistics including total revenue and ticket counts.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Monthly revenue and sales data retrieved successfully",
                            content = @Content(schema = @Schema(implementation = MonthlyStatDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @GetMapping("/monthly-sales")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MonthlyStatDTO>> getMonthlySalesAndRevenue() {
        return ResponseEntity.ok(adminStatsService.getMonthlyRevenueAndSales());
    }

    @Operation(
            summary = "Get top events of the current month",
            description = "Returns a list of the top events for this month based on ticket sales and revenue.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Top events retrieved successfully",
                            content = @Content(schema = @Schema(implementation = EventSimpleStatDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            }
    )
    @GetMapping("/monthly-top/{count}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EventSimpleStatDTO>> getMonthlySalesAndRevenue(@PathVariable int count) {
        return ResponseEntity.ok(adminStatsService.getTopEventsThisMonth(count));
    }
}
