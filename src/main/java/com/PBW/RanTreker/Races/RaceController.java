package com.PBW.RanTreker.Races;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class RaceController {

    @Autowired
    private JDBCRaceRepository raceRepository;

    @GetMapping("/races")
    public String getAllRaces(Model model,
                              @RequestParam(value = "raceName", required = false, defaultValue = "") String raceName,
                              @RequestParam(value = "startDate", required =  false, defaultValue = "") LocalDate startDate,
                              @RequestParam(value = "endDate", required = false, defaultValue = "") LocalDate endDate,
                              @RequestParam(value = "distance", required = false, defaultValue = "None") String distance,
                              @RequestParam(value = "participants", required = false, defaultValue = "None") String participants,
                              @RequestParam(value = "status", required = false, defaultValue = "All")String status) {
        
        
        List<Race> races = raceRepository.getAllRaces(raceName, startDate, endDate, distance, participants, status);
    
        model.addAttribute("size", races.size());
        model.addAttribute("races", races);

        // add filter model
        model.addAttribute("raceName", raceName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("distance", distance);
        model.addAttribute("participants", participants);
        model.addAttribute("status", status);
        return "/admin/races";
    }

    // Add a new race
    @GetMapping("/races/add")
    public String showAddRaceForm() {
        return "/admin/raceadd"; // The name of your Add Race HTML template
    }

    // // Save a new race to the database
    // @PostMapping("/races/add")
    // public String addRace(@RequestParam String race_name,
    //                       @RequestParam double race_length,
    //                       @RequestParam String race_date_time,
    //                       Model model) throws SQLException {
    //     Race race = new Race(1, race_name, race_length, null);
    //     race.setName(race_name);
    //     race.setDistance(race_length);
    //     race.setDateTime(LocalDateTime.parse(race_date_time));

    //     raceRepository.addRace(race);
    //     return "redirect:/races";
    // }
}
