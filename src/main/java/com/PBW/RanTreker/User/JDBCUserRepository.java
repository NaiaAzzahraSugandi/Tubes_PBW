package com.PBW.RanTreker.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JDBCUserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(User user) throws Exception{
        String sql = "INSERT INTO users (name, email, password, peran) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getPeran());
    }
    
    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("password"),
            resultSet.getString("peran")
        );
    }

    public Optional<User> findByUsername(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> results = jdbcTemplate.query(sql, this::mapRowToUser, email);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }
}
