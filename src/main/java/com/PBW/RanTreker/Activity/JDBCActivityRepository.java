package com.PBW.RanTreker.Activity;

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

}
