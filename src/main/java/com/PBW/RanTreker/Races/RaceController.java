package com.PBW.RanTreker.Races;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class RaceController {

    private final JDBCRaceRepository jdbcRace;

    @Autowired
    public RaceController(JDBCRaceRepository jdbcRace) {
        this.jdbcRace = jdbcRace;
    }

    // Display all races
    @GetMapping("/races")
    public String getAllRaces(Model model) throws SQLException {
        List<Race> races = jdbcRace.getAllRaces();
        model.addAttribute("races", races);
        return "/admin/races"; // The name of your HTML template
    }

    // Add a new race
    @GetMapping("/races/add")
    public String showAddRaceForm() {
        return "/admin/raceadd"; // The name of your Add Race HTML template
    }

    // Save a new race to the database
    @PostMapping("/races/add")
    public String addRace(@RequestParam String race_name,
                          @RequestParam double race_length,
                          @RequestParam String race_date_time,
                          Model model) throws SQLException {
        Race race = new Race();
        race.setName(race_name);
        race.setLength(race_length);
        race.setDateTime(LocalDateTime.parse(race_date_time));

        jdbcRace.addRace(race);
        return "redirect:/races";
    }
}
