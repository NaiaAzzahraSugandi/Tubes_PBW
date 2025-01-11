package com.PBW.RanTreker.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JDBCUserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findAll(String name, String nameSort, String peran, int pageSize, int offset) {
        String sql = "SELECT * FROM users";
        List<Object> params = new ArrayList<>();
    
        if (name.length() > 0) {
            sql += " WHERE name ILIKE ?";
            params.add("%"+name+"%");
        }
    
        if (peran.length() > 0 && !peran.equals("None")) {
            // buat periksa search box name diisi atau ngga
            // kalau ga diisi, tambahin WHERE ke syntax
            // kalau diisi, where udah ada, tambahin AND
            if(params.isEmpty()){
                sql += " WHERE peran = ?";
            }
            else{
                sql += " AND peran = ?";
            }

            params.add(peran);
        }
    
        if (nameSort != null && !nameSort.equals("None")) {
            sql += " ORDER BY name " + nameSort;
        }

        // Pagination
        sql += " LIMIT " + pageSize;
        sql += " OFFSET " + offset;

        return jdbcTemplate.query(sql, this::mapRowToUser , params.toArray());
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

    public int countUsers(String name, String nameSort, String peran) {
        String sql = "SELECT COUNT(*) FROM users";
        List<Object> params = new ArrayList<>();
    
        if (name.length() > 0) {
            sql += " WHERE name ILIKE ?";
            params.add("%"+name+"%");
        }
    
        if (peran.length() > 0 && !peran.equals("None")) {
            // buat periksa search box name diisi atau ngga
            // kalau ga diisi, tambahin WHERE ke syntax
            // kalau diisi, where udah ada, tambahin AND
            if(params.isEmpty()){
                sql += " WHERE peran = ?";
            }
            else{
                sql += " AND peran = ?";
            }

            params.add(peran);
        }
    
        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    public void save(User user) throws Exception{
        String sql = "INSERT INTO users (name, email, password, peran) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getPeran());
    }
    
    public Optional<User> findByUsername(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> results = jdbcTemplate.query(sql, this::mapRowToUser, email);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    public User findByID(int id){
        String sql = "SELECT * FROM users WHERE id = ?";
        User result = jdbcTemplate.query(sql, this::mapRowToUser, id).get(0);
        return result;
    }

    public void updateMember(int id, String name, String email, String peran){
        String sql = "UPDATE users SET name = ?, email = ?, peran = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, email, peran, id);
    }

    public void deleteMember(int id){
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
