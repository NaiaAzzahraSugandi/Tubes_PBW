package com.PBW.RanTreker.Races;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PBW.RanTreker.RequiredRole;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("user")
public class RaceUserController {
    private final HttpSession session;
    @Autowired
    private JDBCRaceRepository raceRepository;

    public RaceUserController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/races")
    @RequiredRole("user")
    public String getAllRaces(Model model,
            @RequestParam(value = "raceName", required = false, defaultValue = "") String raceName,
            @RequestParam(value = "startDate", required = false, defaultValue = "") LocalDate startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") LocalDate endDate,
            @RequestParam(value = "distance", required = false, defaultValue = "None") String distance,
            @RequestParam(value = "participants", required = false, defaultValue = "None") String participants,
            @RequestParam(value = "status", required = false, defaultValue = "All") String status) {

        List<Race> races = raceRepository.getAllRaces(raceName, startDate, endDate, distance, participants, status);

        for (Race race : races) {
            updateRaceStatus(race);
        }

        model.addAttribute("size", races.size());
        model.addAttribute("races", races);

        // add filter model
        model.addAttribute("raceName", raceName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("distance", distance);
        model.addAttribute("participants", participants);
        model.addAttribute("status", status);
        return "/user/raceUser";
    }

    /**
     * Method untuk update status race
     * Ubah status dari race menjadi open/closed berdasarkan tanggal sekarang
     * 
     * @param race
     */
    private void updateRaceStatus(Race race) {
        LocalDateTime today = LocalDateTime.now();
        if (race.getStartTime().isBefore(today) && race.getEndTime().isAfter(today)) {
            race.setStatus("Open");
        } else {
            race.setStatus("Closed");
        }
    }

    @GetMapping("/races/participate")
    @RequiredRole("user")
    public String raceParticipationView(@RequestParam int raceID, Model model) {
        Race race = raceRepository.findByRaceID(raceID).get(0);
        RaceParticipant raceParticipant = new RaceParticipant(0, 0, 0, null, 0, null, raceID, null, null);

        model.addAttribute("race", race);
        model.addAttribute("raceParticipant", raceParticipant);
        model.addAttribute("user_id", (int) session.getAttribute("id_user"));
        return "/user/raceParticipate";
    }

    @PostMapping("/races/participate")
    @RequiredRole("user")
    public String raceParticipate(@Valid RaceParticipant raceParticipant, BindingResult bindingResult, Model model) {
        Race race = raceRepository.findByRaceID(raceParticipant.getRace_id()).get(0);
        if (bindingResult.hasErrors()) {
            System.out.println("Errors : ");
            // log errors
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getDefaultMessage());
            });
            model.addAttribute("raceParticipant", raceParticipant);
            model.addAttribute("user_id", (int)session.getAttribute("id_user"));
            model.addAttribute("race", race);
            return "/user/raceParticipate";
        }

        

        return "redirect:/user/races";
    }
}
