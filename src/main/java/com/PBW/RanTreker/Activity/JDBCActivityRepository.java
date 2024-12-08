package com.PBW.RanTreker.Activity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCActivityRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Activity activity){
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
    
    public List<Activity> findAll(Integer id_user){
        String sql = "SELECT * FROM activities WHERE id_user = " + id_user;
        return jdbcTemplate.query(sql, this::mapRowToActivity);
    }

    public Activity mapRowToActivity(ResultSet resultSet, int rowNum) throws SQLException{
        return new Activity(
                resultSet.getInt("id_user"),
                resultSet.getString("title"),
                resultSet.getInt("distance"),
                resultSet.getInt("duration"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("description"),
                null,
                resultSet.getString("image_location")
                );
    }

}
