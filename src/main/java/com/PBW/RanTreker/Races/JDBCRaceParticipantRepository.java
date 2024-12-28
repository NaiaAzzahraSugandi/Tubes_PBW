package com.PBW.RanTreker.Races;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCRaceParticipantRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RaceParticipant> getAllParticipantsFromRace(int raceID){
        String sql = "SELECT * FROM race_participants_view WHERE race_id = ? ORDER BY speed_km_min DESC";
        return jdbcTemplate.query(sql, this::mapRowToRaceParticipant, raceID);
    }

    public Optional<RaceParticipant> findByParticipantID(int raceID, int id_user){
        String sql = "SELECT * FROM race_participants_view WHERE race_id = ? AND user_id = ?";
        List<RaceParticipant> raceParticipant = jdbcTemplate.query(sql, this::mapRowToRaceParticipant, raceID, id_user);
        
        return raceParticipant.size() == 0 ? Optional.empty() : Optional.of(raceParticipant.get(0));
    }

    private RaceParticipant mapRowToRaceParticipant(ResultSet resultSet, int rowNum) throws SQLException {
        return new RaceParticipant(
            resultSet.getInt("id"),
            resultSet.getInt("race_id"),
            resultSet.getInt("user_id"),
            resultSet.getString("name"),
            resultSet.getTimestamp("registration_date").toLocalDateTime(),
            resultSet.getDouble("distance"),
            resultSet.getString("duration"),
            resultSet.getDouble("speed_km_min"),
            null,
            resultSet.getString("image_location")
        );
    }

    public void save(RaceParticipant raceParticipant){
        String sql = "INSERT INTO race_participants (race_id, user_id, registration_date, distance, duration, speed_km_min, image_location) VALUES (?, ?, ?, ?, ?::interval, ?, ?)";
        jdbcTemplate.update(sql, 
                            raceParticipant.getRace_id(), 
                            raceParticipant.getUser_id(), 
                            raceParticipant.getRegistration_date(),
                            raceParticipant.getDistance(),
                            raceParticipant.getDuration(),
                            raceParticipant.getSpeed_km_min(),
                            raceParticipant.getImage_location());
    }

    public void deleteParticipant(int raceID, int user_id){
        String sql = "DELETE FROM race_participants WHERE race_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, raceID, user_id);
    }
}
