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
        String sql = "INSERT INTO activities (id_user, title, distance, duration, date, time, description, image_location) VALUES (?, ?, ?, ?::INTERVAL, ?, ?, ?, ?)";

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
            String distance,
            int offset,
            int pageSize,
            int page) {
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

        // Pagination
        sql += " LIMIT " + pageSize +  " OFFSET " + offset;

        return jdbcTemplate.query(sql.toString(), this::mapRowToActivity, filterList.toArray());
    }

    public int countActivities(int idUser, String title, LocalDate startDate, LocalDate endDate, String time, String duration, String distance) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM activities WHERE id_user = ?");
        List<Object> params = new ArrayList<>();
        params.add(idUser);
    
        if (title != null && !title.isEmpty()) {
            sql.append(" AND title LIKE ?");
            params.add("%" + title + "%");
        }
        if (startDate != null) {
            sql.append(" AND date >= ?");
            params.add(startDate);
        }
        if (endDate != null) {
            sql.append(" AND date <= ?");
            params.add(endDate);
        }
        if (time != null && !time.isEmpty() && !time.equalsIgnoreCase("None")) {
            sql.append(" AND time IS NOT NULL");
        }
        if (duration != null && !duration.isEmpty() && !duration.equalsIgnoreCase("None")) {
            sql.append(" AND duration IS NOT NULL");
        }
        if (distance != null && !distance.isEmpty() && !distance.equalsIgnoreCase("None")) {
            sql.append(" AND distance IS NOT NULL");
        }
    
        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    public List<Activity> getRecentActivities(Integer userId, int limit) {
        String sql = "SELECT *" +
                     "FROM activities " +
                     "WHERE id_user = " + userId +
                     " ORDER BY date DESC, time DESC " +
                     "LIMIT " + limit;
        
        // Menggunakan jdbcTemplate untuk query dan mapper
        return jdbcTemplate.query(sql, this::mapRowToActivity);
    }
    
    
    

    public Activity mapRowToActivity(ResultSet resultSet, int rowNum) throws SQLException {
        return new Activity(
                resultSet.getInt("id"),
                resultSet.getInt("id_user"),
                resultSet.getString("title"),
                resultSet.getDouble("distance"),
                resultSet.getString("duration"),
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

    public List<Activity> findByUserID(int id){
        String sql = "SELECT * FROM activities WHERE id_user = " + id;
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
        String sql = """
            SELECT month, COALESCE(SUM(distance), 0) AS total_distance
            FROM (
                SELECT TO_CHAR(date, 'Month') AS month, distance
                FROM activities
                WHERE id_user = ?
                UNION ALL
                SELECT 'January', 0
                UNION ALL
                SELECT 'February', 0
                UNION ALL
                SELECT 'March', 0
                UNION ALL
                SELECT 'April', 0
                UNION ALL
                SELECT 'May', 0
                UNION ALL
                SELECT 'June', 0
                UNION ALL
                SELECT 'July', 0
                UNION ALL
                SELECT 'August', 0
                UNION ALL
                SELECT 'September', 0
                UNION ALL
                SELECT 'October', 0
                UNION ALL
                SELECT 'November', 0
                UNION ALL
                SELECT 'December', 0
            ) AS all_months
            GROUP BY month
            ORDER BY
                CASE month
                    WHEN 'January' THEN 1
                    WHEN 'February' THEN 2
                    WHEN 'March' THEN 3
                    WHEN 'April' THEN 4
                    WHEN 'May' THEN 5
                    WHEN 'June' THEN 6
                    WHEN 'July' THEN 7
                    WHEN 'August' THEN 8
                    WHEN 'September' THEN 9
                    WHEN 'October' THEN 10
                    WHEN 'November' THEN 11
                    WHEN 'December' THEN 12
                END;
        """;
    
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
            SELECT 
                day_of_week.day AS day,
                COALESCE(SUM(a.distance), 0) AS total_distance
            FROM 
                (VALUES ('Sunday'), ('Monday'), ('Tuesday'), ('Wednesday'), 
                        ('Thursday'), ('Friday'), ('Saturday')) AS day_of_week(day)
            LEFT JOIN activities a 
                ON TRIM(TO_CHAR(a.date, 'Day')) = day_of_week.day
                AND a.id_user = ?
            GROUP BY day_of_week.day
            ORDER BY 
                CASE day_of_week.day
                    WHEN 'Sunday' THEN 1
                    WHEN 'Monday' THEN 2
                    WHEN 'Tuesday' THEN 3
                    WHEN 'Wednesday' THEN 4
                    WHEN 'Thursday' THEN 5
                    WHEN 'Friday' THEN 6
                    WHEN 'Saturday' THEN 7
                    ELSE 8
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
