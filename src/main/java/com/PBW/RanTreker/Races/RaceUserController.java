package com.PBW.RanTreker.Races;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.PBW.RanTreker.RequiredRole;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("user")
public class RaceUserController {
    private final HttpSession session;

    @Autowired
    private JDBCRaceRepository raceRepository;

    @Autowired
    private JDBCRaceParticipantRepository raceParticipantRepository;

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
            @RequestParam(value = "status", required = false, defaultValue = "All") String status) {

        List<Race> races = raceRepository.getAllRaces(raceName, startDate, endDate, distance, null, status);
        
        // menyimpan race yang sudah diikuti
        // berfungsi untuk mengatur isi kolom "Join Status" dan tombol "Participate"
        List<String> joinStatuses = new ArrayList<>();
        List<Boolean> participationList = new ArrayList<>();

        // loop untuk update race status dan untuk mengatur apakah user sudah join atau belum ke dalam race
        for (Race race : races) {
            updateRaceStatus(race);
            int raceID = race.getRaceID();
            Optional<RaceParticipant> findRaceParticipant = raceParticipantRepository.findByParticipantID(raceID, (int)session.getAttribute("id_user"));


            if(findRaceParticipant.isPresent()){
                joinStatuses.add("Joined");
                participationList.add(true);
            }
            else if (race.getStatus().equals("Scheduled") || race.getStatus().equals("Closed")){
                joinStatuses.add("Not Joined");
                participationList.add(true);
            }
            else{
                joinStatuses.add("Not Joined");
                participationList.add(false);
            }
        }

        model.addAttribute("size", races.size());
        model.addAttribute("races", races);

        // add filter model
        model.addAttribute("raceName", raceName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("distance", distance);
        model.addAttribute("status", status);
        model.addAttribute("joinStatuses", joinStatuses);
        model.addAttribute("participationList", participationList);
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
            race.setStatus("Ongoing");
        } 
        else if(race.getStartTime().isAfter(today)){
            race.setStatus("Scheduled");
        }
        else {
            race.setStatus("Closed");
        }

        raceRepository.updateStatus(race);
    }

    @GetMapping("/races/participate")
    @RequiredRole("user")
    public String raceParticipationView(@RequestParam int raceID, Model model) {
        Race race = raceRepository.findByRaceID(raceID).get(0);
        RaceParticipant raceParticipant = new RaceParticipant(0, 0, 0, null, null, 0, null, raceID, null, null);

        model.addAttribute("race", race);
        model.addAttribute("raceParticipant", raceParticipant);
        model.addAttribute("user_id", (int) session.getAttribute("id_user"));
        return "/user/raceParticipate";
    }

    @PostMapping("/races/participate")
    @RequiredRole("user")
    public String raceParticipate(@Valid RaceParticipant raceParticipant, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
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

        // set speed_km_min
        String[] parts = raceParticipant.getDuration().split(":");
        // ubah HH:MM:ss ke detik
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        int totalSeconds = (hours * 3600) + (minutes * 60) + seconds;

        // hitung km/min
        double speedKmMin = raceParticipant.getDistance() / (totalSeconds / 60.0);

        // set speed
        raceParticipant.setSpeed_km_min(speedKmMin);

        // format registration date, jangan include fractional seconds
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String todayString = today.format(formatter);
        LocalDateTime todayFormatted = LocalDateTime.parse(todayString, formatter);
        raceParticipant.setRegistration_date(todayFormatted);

        
        MultipartFile image = raceParticipant.getImage_file();

        // format file name
        String dateTime = raceParticipant.getRegistration_date().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        // ambil id user dan id race
        Integer userId = raceParticipant.getUser_id();
        Integer raceId = raceParticipant.getRace_id();
        // generate file name
        String fileName = "Race" + "_" + raceId + "_" + userId + "_" + dateTime + ".jpg";

        // Save image kalo usernya submit
        if (raceParticipant.getImage_file() != null && !raceParticipant.getImage_file().isEmpty()) {
            try {
                // directorynya mau disimpan dimana
                String directory = "public/race_images/";
                Path uploadPath = Paths.get(directory);

                // buat directorynya kalau belum ada
                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                // save file ke directory
                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(directory + fileName), StandardCopyOption.REPLACE_EXISTING);
                }

                // update kolom image_location
                raceParticipant.setImage_location(fileName);
            } 
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        // Save activity ke database
        raceParticipantRepository.save(raceParticipant);
        redirectAttributes.addFlashAttribute("successMessage", "You have successfully registered for the race!");

        return "redirect:/user/races";
    }

    @GetMapping("races/leaderboard")
    @RequiredRole("user")
    public String viewLeaderboard(@RequestParam int raceID, Model model){
        List<RaceParticipant> raceParticipants = raceParticipantRepository.getAllParticipantsFromRace(raceID);
        Race race = raceRepository.findByRaceID(raceID).get(0);

        // cari posisi user
        int userPosition = -1; // -1 kalau ga ketemu
        for (int i = 0; i < raceParticipants.size(); i++) {
            if (raceParticipants.get(i).getUser_id() == (int)session.getAttribute("id_user")) {
                userPosition = i + 1;
                break;
            }
        }

        model.addAttribute("raceParticipants", raceParticipants);
        model.addAttribute("raceTitle", race.getTitle());
        model.addAttribute("size", raceParticipants.size());
        model.addAttribute("userPosition", userPosition);
        return "/user/raceLeaderboard";
    }
}
