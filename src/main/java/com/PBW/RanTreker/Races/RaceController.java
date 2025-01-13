package com.PBW.RanTreker.Races;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.PBW.RanTreker.RequiredRole;
import com.PBW.RanTreker.Notification.JDBCNotificationRepository;
import com.PBW.RanTreker.Notification.Notification;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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
import java.util.List;

@Controller
@RequestMapping("admin")
public class RaceController {

    @Autowired
    private JDBCRaceRepository raceRepository;

    @Autowired
    private JDBCRaceParticipantRepository raceParticipantRepository;

    @Autowired
    private JDBCNotificationRepository notificationRepository;

    @GetMapping("/races")
    @RequiredRole("admin")
    public String getAllRaces(Model model,
                            @RequestParam(value = "raceName", required = false, defaultValue = "") String raceName,
                            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                            @RequestParam(value = "distance", required = false, defaultValue = "None") String distance,
                            @RequestParam(value = "status", required = false, defaultValue = "All") String status,
                            @RequestParam(value = "participants", required = false, defaultValue = "None") String participants,
                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                            HttpSession session) {

        // set jumlah record per page dan offset
        int pageSize = 5;
        int offset = (page - 1) * pageSize;

        // Ambil data races dari repository
        List<Race> races = raceRepository.getAllRaces(raceName, startDate, endDate, distance, participants, status, pageSize, offset);

        // update status race
        for (Race race : races) {
            updateRaceStatus(race);
        }

        // hitung jumlah race dan jumlah halaman
        int totalRaces = raceRepository.countRaces(raceName, startDate, endDate, distance, status);
        int totalPages = (int) Math.ceil((double) totalRaces / pageSize);

        // tambah atribut untuk ditampilkan ke view
        model.addAttribute("races", races);
        model.addAttribute("size", races.size());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("raceName", raceName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("distance", distance);
        model.addAttribute("status", status);
        model.addAttribute("participants", participants);

        return "/admin/races";
    }


    @GetMapping("/addRace")
    @RequiredRole("admin")
    public String showAddRaceForm(Model model) {
        Race race = new Race(0, "", 0, null, null, 0, "", "", "", null);
        model.addAttribute("race", race);
        return "/admin/raceadd";
    }

    @PostMapping("/addRace")
    @RequiredRole("admin")
    public String addRace(@Valid Race race, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        // cek apakah masukkan ada error
        if (bindingResult.hasErrors()) {
            model.addAttribute("race", race);
            return "/admin/raceadd";
        }

        // save image
        MultipartFile image = race.getImage_file();

        // format file name
        // ambil tanggal hari ini
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        // ambil waktu sekarang
        String formattedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
        // generate file name
        String fileName = "RaceBanner" + "_" +formattedDate + "_" + formattedTime + "_" + ".jpg";

        // Save image kalo usernya submit
        if (race.getImage_file() != null && !race.getImage_file().isEmpty()) {
            try {
                // directorynya mau disimpan dimana
                String directory = "public/banner_images/";
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
                race.setImage_location(fileName);
            } 
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        updateRaceStatus(race);

        raceRepository.addRace(race);

        redirectAttributes.addFlashAttribute("successMessage", "Race added successfully!");

        return "redirect:/admin/races";
    }

    @GetMapping("/races/edit")
    @RequiredRole("admin")
    public String editRaceView(Model model, @RequestParam int id) {
        Race race = raceRepository.findByRaceID(id).get(0);
        model.addAttribute("race", race);

        System.out.println(race.getImage_location());
        return "/admin/racedit";
    }

    @PostMapping("/races/edit")
    @RequiredRole("admin")
    public String editRace(@Valid Race race, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println("Errors : ");
            // log errors
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getDefaultMessage());
            });
            model.addAttribute("race", race);
            return "/admin/racedit";
        }

        try{
            // periksa apakah user submit image baru
            if(!race.getImage_file().isEmpty()){
                // buang image yang lama
                String directory = "public/banner_images/";

                if(race.getImage_location() != null && !race.getImage_location().equals("")){
                    Path oldImagePath = Paths.get(directory + race.getImage_location());
                    
                    try{
                        Files.delete(oldImagePath);
                    }
                    catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                }
    
                // save image yang baru
                MultipartFile image = race.getImage_file();
                // ambil tanggal hari ini
                String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
                // ambil waktu sekarang
                String formattedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
                // generate file name
                String fileName = "RaceBanner" + "_" + formattedDate + "_" + formattedTime + "_" + ".jpg";
    
                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(directory + fileName), StandardCopyOption.REPLACE_EXISTING);
                }

                // set isi kolom image_location dengan lokasi baru
                race.setImage_location(fileName);
    
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        // update status dari race
        updateRaceStatus(race);
        // panggil query edit dari repository
        raceRepository.editRace(race);

        redirectAttributes.addFlashAttribute("successMessage", "Race edited successfully!");
        return "redirect:/admin/races";
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

    @GetMapping("/races/delete")
    @RequiredRole("admin")
    public String deleteRace(@RequestParam int id, RedirectAttributes redirectAttributes){
        Race race = raceRepository.findByRaceID(id).get(0);

        // delete image kalo misalnya ada
        if (race.getImage_location() != null && !race.getImage_location().isEmpty()) {
            try {
                Path imagePath = Paths.get("public/banner_images/" +race.getImage_location());
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                System.out.println("Error deleting image: " + e.getMessage());
            }
        }

        // delete record race participant
        List<RaceParticipant> raceParticipants = raceParticipantRepository.getAllParticipantsFromRace(id);
        for (RaceParticipant raceParticipant : raceParticipants) {
            if (raceParticipant.getImage_location() != null && !raceParticipant.getImage_location().isEmpty()) {
                try {
                    Path imagePath = Paths.get("public/race_images/" +raceParticipant.getImage_location());
                    Files.deleteIfExists(imagePath);
                } catch (IOException e) {
                    System.out.println("Error deleting image: " + e.getMessage());
                }
            }

            raceParticipantRepository.deleteParticipant(raceParticipant.getRace_id(), raceParticipant.getUser_id());
        }

        raceRepository.deleteRace(id);
        redirectAttributes.addFlashAttribute("successMessage", "Race deleted successfully!");
        return "redirect:/admin/races";
    }

    @GetMapping("/races/detail")
    @RequiredRole("admin")
    public String raceDetails(@RequestParam int id,
                            Model model){

        Race race = raceRepository.findByRaceID(id).get(0);
        model.addAttribute("race", race);

        List<RaceParticipant> raceParticipants = raceParticipantRepository.getAllParticipantsFromRace(id);
        model.addAttribute("raceParticipants", raceParticipants);
        model.addAttribute("size", raceParticipants.size());
        return "/admin/racedetails";
    }

    @GetMapping("/races/detail/delete")
    @RequiredRole("admin")
    public String deleteParticipant(int raceID, int userID, String message, String raceTitle, RedirectAttributes redirectAttributes){
        RaceParticipant raceParticipant = raceParticipantRepository.findByParticipantID(raceID, userID).get();

        // delete image kalo misalnya ada
        if (raceParticipant.getImage_location() != null && !raceParticipant.getImage_location().isEmpty()) {
            try {
                Path imagePath = Paths.get("public/race_images/" +raceParticipant.getImage_location());
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                System.out.println("Error deleting image: " + e.getMessage());
            }
        }

        raceParticipantRepository.deleteParticipant(raceID, userID);

        // format created date, jangan include fractional seconds
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String todayString = today.format(formatter);
        LocalDateTime todayFormatted = LocalDateTime.parse(todayString, formatter);

        // format message
        String newMessage = "You have been removed from " + raceTitle + "!<br>" + "Reason :<br>" + message; 
        // buat notification dan submit
        Notification notification = new Notification(0, userID, todayFormatted, newMessage);
        notificationRepository.save(notification);

        redirectAttributes.addFlashAttribute("successMessage", "Participant deleted successfully!");

        return "redirect:/admin/races/detail?id=" + raceID;
    }
}
