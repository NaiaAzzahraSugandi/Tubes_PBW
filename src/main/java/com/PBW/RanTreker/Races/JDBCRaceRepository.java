package com.PBW.RanTreker.Races;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDBCRaceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Race> getAllRaces(String name,
                                LocalDate startDate,
                                LocalDate endDate,
                                String distanceOrder,
                                String participantOrder,
                                String status) {

        StringBuilder sql = new StringBuilder("SELECT * FROM races WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            sql.append(" AND name ILIKE ?");
            params.add("%" + name + "%");
        }

        if (startDate != null) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            sql.append(" AND start_date_time >= ?");
            params.add(startDateTime);
        }

        if (endDate != null) {
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
            sql.append(" AND end_date_time <= ?");
            params.add(endDateTime);
        }

        if (status != null && !status.equals("All")) {
            sql.append(" AND status = ?");
            params.add(status);
        }

        if (distanceOrder != null && !distanceOrder.equals("None")) {
            sql.append(" ORDER BY distance ");
            sql.append(distanceOrder);
        }
        else if (participantOrder != null && !participantOrder.equals("None")) {
            sql.append(" ORDER BY participants ");
            sql.append(participantOrder);
        }
        // kalau filter order by ga ada yang diisi, default berdasarkan name
        else{
            sql.append(" ORDER BY name ASC");
        }

        return jdbcTemplate.query(sql.toString(), this::mapRowToRace, params.toArray());
    }

    public List<Race> findByRaceID(int raceID){
        String sql = "SELECT * FROM races WHERE id = ?";
        return jdbcTemplate.query(sql, this::mapRowToRace, raceID);
    }

    private Race mapRowToRace(ResultSet resultSet, int rowNum) throws SQLException {
        return new Race(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getTimestamp("start_date_time").toLocalDateTime(),
                resultSet.getTimestamp("end_date_time").toLocalDateTime(),
                resultSet.getDouble("distance"),
                resultSet.getString("status"),
                resultSet.getInt("participants"));
    }

    public void addRace(Race race) {
        String sql = "INSERT INTO races (name, start_date_time, end_date_time, distance, status, participants) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, race.getTitle(), race.getStartTime(), race.getEndTime(), race.getDistance(), race.getStatus(), race.getParticipants());
    }

    public void editRace(Race race){
        String sql = "UPDATE races SET name = ?, start_date_time = ?, end_date_time = ?, distance = ?, status = ? WHERE id = ?";
        jdbcTemplate.update(sql, race.getTitle(), race.getStartTime(), race.getEndTime(), race.getDistance(), race.getStatus(), race.getRaceID());
    }

    public void deleteRace(int raceID){
        String sql = "DELETE FROM races WHERE id = ?";
        jdbcTemplate.update(sql, raceID);
    }
}
