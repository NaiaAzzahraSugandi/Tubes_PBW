package com.PBW.RanTreker.Races;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JDBCRaceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Method to add a new race to the database
    public void addRace(Race race) {
        String sql = "INSERT INTO race (name, length, date_time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, race.getName(), race.getLength(), race.getDateTime());
    }

    // Method to retrieve all races from the database
    public List<Race> getAllRaces() {
        String sql = "SELECT * FROM race";
        return jdbcTemplate.query(sql, new RaceRowMapper());
    }

    // RowMapper for mapping ResultSet to Race object
    private static class RaceRowMapper implements RowMapper<Race> {
        @Override
        public Race mapRow(ResultSet rs, int rowNum) throws SQLException {
            Race race = new Race();
            race.setId(rs.getInt("id"));
            race.setName(rs.getString("name"));
            race.setLength(rs.getInt("length"));  // Pastikan panjangnya menggunakan tipe data yang sesuai
            // Mengambil nilai date_time sebagai LocalDateTime
            race.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
            return race;
        }
    }
}
