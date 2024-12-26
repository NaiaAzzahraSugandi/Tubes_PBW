package com.PBW.RanTreker.Races;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.PBW.RanTreker.RequiredRole;

import jakarta.validation.Valid;

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
    @RequiredRole("admin")
    public String getAllRaces(Model model,
            @RequestParam(value = "raceName", required = false, defaultValue = "") String raceName,
            @RequestParam(value = "startDate", required = false, defaultValue = "") LocalDate startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") LocalDate endDate,
            @RequestParam(value = "distance", required = false, defaultValue = "None") String distance,
            @RequestParam(value = "participants", required = false, defaultValue = "None") String participants,
            @RequestParam(value = "status", required = false, defaultValue = "All") String status) {

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

    @GetMapping("/addRace")
    @RequiredRole("admin")
    public String showAddRaceForm(Model model) {
        Race race = new Race(0, null, null, null, 0, null, 0);
        model.addAttribute("race", race);
        return "/admin/raceadd";
    }

    @PostMapping("/addRace")
    @RequiredRole("admin")
    public String addRace(@Valid Race race, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("race", race);
            return "/admin/raceadd";
        }

        LocalDateTime today = LocalDateTime.now();
        if(race.getStartTime().isBefore(today)){
            race.setStatus("Closed");
        }
        else{
            race.setStatus("Open");
        }

        raceRepository.addRace(race);

        redirectAttributes.addFlashAttribute("successMessage", "Race added successfully!");

        return "redirect:/admin/races";
    }
}
