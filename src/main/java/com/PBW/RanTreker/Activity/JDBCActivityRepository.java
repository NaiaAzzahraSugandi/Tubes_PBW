package com.PBW.RanTreker.Activity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
}
