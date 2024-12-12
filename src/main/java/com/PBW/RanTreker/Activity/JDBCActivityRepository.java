package com.PBW.RanTreker.Activity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCActivityRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Activity activity) {
        String sql = "INSERT INTO activities (id_user, title, distance, duration, date, time, description, image_location) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                activity.getId_user(),
                activity.getTitle(),
                activity.getDistance(),
                activity.getDuration(),
                activity.getDate(),
                activity.getTime(),
                activity.getDescription(),
                activity.getImage_location());
    }

    public List<Activity> findAll(Integer id_user,
            String title,
            LocalDate startDate,
            LocalDate endDate,
            String time,
            String duration,
            String distance) {
        String sql = "SELECT * FROM activities WHERE id_user = ?";
        List<Object> filterList = new ArrayList<>();
        filterList.add(id_user); // Add user ID to the filter list

        if (title.length() > 0) {
            sql += " AND title ILIKE ?";
            filterList.add("%" + title + "%");
        }
        if (startDate != null) {
            sql += " AND date >= ?";
            filterList.add(startDate);
        }
        if (endDate != null) {
            sql += " AND date <= ?";
            filterList.add(endDate);
        }

        // Append ORDER BY clause based on the sorting parameter
        if (!time.equals("None")) {
            sql += " ORDER BY time " + time;
        } 
        else if (!duration.equals("None")) {
            sql += " ORDER BY duration " + duration;
        } 
        else if (!distance.equals("None")) {
            sql += " ORDER BY distance " + distance;
        }

        return jdbcTemplate.query(sql.toString(), this::mapRowToActivity, filterList.toArray());
    }

    public Activity mapRowToActivity(ResultSet resultSet, int rowNum) throws SQLException {
        return new Activity(
                resultSet.getInt("id"),
                resultSet.getInt("id_user"),
                resultSet.getString("title"),
                resultSet.getInt("distance"),
                resultSet.getInt("duration"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("description"),
                null,
                resultSet.getString("image_location"));
    }

    public List<Activity> findById(int id){
        String sql = "SELECT * FROM activities WHERE id = " + id;
        return jdbcTemplate.query(sql, this::mapRowToActivity);
    }

    public void updateRun(int id, String title, String description, String image_location){
        String sql = "UPDATE activities SET title = ?, description = ?, image_location = ?  WHERE id = ?";

        // update the data
        jdbcTemplate.update(sql, title, description, image_location, id);
    }

    public void deleteRun(int id){
        String sql = "DELETE FROM activities WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
  
    public Map<String, Integer> getActivitySummaryByMonth(Integer userId) {
        String sql = "SELECT TO_CHAR(date, 'Month') AS month, SUM(distance) AS total_distance " +
                    "FROM activities WHERE id_user = ? GROUP BY month ORDER BY MIN(date)";
        return jdbcTemplate.query(sql, rs -> {
            Map<String, Integer> summary = new LinkedHashMap<>();
            while (rs.next()) {
                summary.put(rs.getString("month").trim(), rs.getInt("total_distance"));
            }
            return summary;
        }, userId);
    }
    public Map<String, Integer> getActivitySummaryByWeek(Integer userId) {
        String sql = """
            SELECT TO_CHAR(date, 'Day') AS day, SUM(distance) AS total_distance
            FROM activities
            WHERE id_user = ?
            GROUP BY day
            ORDER BY 
                CASE TRIM(TO_CHAR(date, 'Day'))
                    WHEN 'Monday' THEN 1
                    WHEN 'Tuesday' THEN 2
                    WHEN 'Wednesday' THEN 3
                    WHEN 'Thursday' THEN 4
                    WHEN 'Friday' THEN 5
                    WHEN 'Saturday' THEN 6
                    WHEN 'Sunday' THEN 7
                    ELSE 8  -- Handle any unexpected results
                END;
            """;
    
        return jdbcTemplate.query(sql, rs -> {
            Map<String, Integer> summary = new LinkedHashMap<>();
            while (rs.next()) {
                summary.put(rs.getString("day").trim(), rs.getInt("total_distance"));
            }
            return summary;
        }, userId);
    }
    
    public Map<String, Integer> getActivitySummaryByYear(Integer userId) {
        String sql = """
            SELECT EXTRACT(YEAR FROM date) AS year, SUM(distance) AS total_distance
            FROM activities
            WHERE id_user = ?
            GROUP BY year
            ORDER BY year
            """;
        return jdbcTemplate.query(sql, rs -> {
            Map<String, Integer> summary = new LinkedHashMap<>();
            while (rs.next()) {
                summary.put("Year " + rs.getInt("year"), rs.getInt("total_distance"));
            }
            return summary;
        }, userId);
    }

}
