package com.PBW.RanTreker.Notification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCNotificationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Notification notification){
        String sql = "INSERT INTO notifications (user_id, created_date, message) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, notification.getUser_id(), notification.getCreatedDate(), notification.getMessage());
    }

    public List<Notification> getAllNotifications(int userId){
        String sql = "SELECT * FROM notifications WHERE user_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToNotification);
    }

    public Notification mapRowToNotification(ResultSet resultSet, int rowNum) throws SQLException{
        return new Notification(resultSet.getInt("id"), 
                                resultSet.getInt("user_id"), 
                                resultSet.getTimestamp("message").toLocalDateTime(), 
                                resultSet.getString("message"));
    }
}
