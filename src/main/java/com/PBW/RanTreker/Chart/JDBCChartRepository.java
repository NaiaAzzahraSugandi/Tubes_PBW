package com.PBW.RanTreker.Chart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JDBCChartRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Mengambil data mingguan
    public List<ChartData> fetchWeeklyData(int userId) {
        String sql = """
            SELECT EXTRACT(week FROM date) AS week, SUM(distance) AS total_distance
            FROM activities
            WHERE id_user = ?
            GROUP BY EXTRACT(week FROM date)
            ORDER BY week
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new ChartData("Week " + rs.getInt("week"), rs.getDouble("total_distance")), userId);
    }

    // Mengambil data bulanan
    public List<ChartData> fetchMonthlyData(int userId) {
        String sql = """
            SELECT EXTRACT(month FROM date) AS month, SUM(distance) AS total_distance
            FROM activities
            WHERE id_user = ?
            GROUP BY EXTRACT(month FROM date)
            ORDER BY month
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new ChartData("Month " + rs.getInt("month"), rs.getDouble("total_distance")), userId);
    }

    // Mengambil data tahunan
    public List<ChartData> fetchYearlyData(int userId) {
        String sql = """
            SELECT EXTRACT(year FROM date) AS year, SUM(distance) AS total_distance
            FROM activities
            WHERE id_user = ?
            GROUP BY EXTRACT(year FROM date)
            ORDER BY year
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new ChartData("Year " + rs.getInt("year"), rs.getDouble("total_distance")), userId);
    }
}
