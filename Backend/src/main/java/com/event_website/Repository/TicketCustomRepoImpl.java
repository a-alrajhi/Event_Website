package com.event_website.Repository;

import com.event_website.Dto.EventSimpleStatDTO;
import com.event_website.Dto.EventStatDTO;
import com.event_website.Dto.MonthlyStatDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TicketCustomRepoImpl implements TicketCustomRepo {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<EventStatDTO> findTopEventsBySales(Pageable pageable) {
        String sql = """
            SELECT
                e.id AS event_id,
                e.name AS name,
                COUNT(t.id) AS tickets_sold,
                COALESCE(SUM(tt.price) FILTER (WHERE t.id IS NOT NULL), 0) AS revenue,
                CASE
                    WHEN COUNT(t.id) = 0 THEN 0
                    ELSE ROUND(SUM(CASE WHEN t.checked_in THEN 1 ELSE 0 END)::decimal / COUNT(t.id) * 100, 2)
                END AS check_in_rate
            FROM events e
            LEFT JOIN slots s ON s.event_id = e.id
            LEFT JOIN slot_ticket_type_capacities sttc ON sttc.slot_id = s.id
            LEFT JOIN tickets t ON t.slot_ticket_type_id = sttc.id
            LEFT JOIN ticket_types tt ON sttc.ticket_type_id = tt.id
            GROUP BY e.id
            ORDER BY tickets_sold DESC
            LIMIT :limit
        """;

        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("limit", pageable.getPageSize())
                .getResultList();

        return rows.stream().map(row -> {
            EventStatDTO dto = new EventStatDTO();
            dto.setEventId(((Number) row[0]).intValue());
            dto.setName((String) row[1]);
            dto.setTicketsSold(((Number) row[2]).longValue());
            dto.setRevenue((BigDecimal) row[3]);
            dto.setCheckInRate(((Number) row[4]).doubleValue());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<MonthlyStatDTO> getMonthlyRevenueAndSales() {
        String sql = """
            SELECT 
                TO_CHAR(DATE_TRUNC('month', s.date), 'YYYY-MM') AS month,
                COUNT(t.id) AS ticket_count,
                COALESCE(SUM(tt.price), 0) AS total_revenue
            FROM tickets t
            JOIN slot_ticket_type_capacities sttc ON sttc.id = t.slot_ticket_type_id
            JOIN ticket_types tt ON tt.id = sttc.ticket_type_id
            JOIN slots s ON s.id = sttc.slot_id
            GROUP BY DATE_TRUNC('month', s.date)
            ORDER BY month ASC
        """;

        List<Object[]> results = em.createNativeQuery(sql).getResultList();

        return results.stream().map(row -> new MonthlyStatDTO(
                (String) row[0],
                ((Number) row[1]).longValue(),
                (BigDecimal) row[2]
        )).collect(Collectors.toList());
    }

    @Override
    public List<EventSimpleStatDTO> findTopEventsThisMonth(int topN) {
        String sql = """
            SELECT 
                e.id AS event_id,
                e.name AS event_name,
                COUNT(t.id) AS tickets_sold,
                COALESCE(SUM(tt.price), 0) AS revenue
            FROM events e
            JOIN slots s ON s.event_id = e.id
            JOIN slot_ticket_type_capacities sttc ON sttc.slot_id = s.id
            JOIN tickets t ON t.slot_ticket_type_id = sttc.id
            JOIN ticket_types tt ON tt.id = sttc.ticket_type_id
            WHERE DATE_TRUNC('month', s.date) = DATE_TRUNC('month', CURRENT_DATE)
            GROUP BY e.id, e.name
            ORDER BY tickets_sold DESC
            LIMIT :topN
        """;

        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("topN", topN)
                .getResultList();

        return rows.stream().map(row -> {
            EventSimpleStatDTO dto = new EventSimpleStatDTO();
            dto.setEventId(((Number) row[0]).intValue());
            dto.setEventName((String) row[1]);
            dto.setTicketsSold(((Number) row[2]).longValue());
            dto.setRevenue((BigDecimal) row[3]);
            return dto;
        }).collect(Collectors.toList());
    }
}
