package com.PBW.RanTreker.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.PBW.RanTreker.RequiredRole;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class ActivityController {
    private final HttpSession session;

    public ActivityController(HttpSession session) {
        this.session = session;
    }

    @Autowired
    JDBCActivityRepository activityRepository;

    @GetMapping("/dashboard")
    @RequiredRole("user")
    public String dashboard(Model model) {
        String nama = (String) session.getAttribute("nama");
        model.addAttribute("nama", nama);
        return "/user/dashboard";
    }

    @GetMapping("/activity")
    @RequiredRole("user")
    public String activityView(Model model,
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "startDate", required = false, defaultValue = "") LocalDate startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") LocalDate endDate,
            @RequestParam(value = "time", required = false, defaultValue = "") String time,
            @RequestParam(value = "duration", required = false, defaultValue = "") String duration,
            @RequestParam(value = "distance", required = false, defaultValue = "") String distance) {

        int id_user = (int) session.getAttribute("id_user");
        List<Activity> activities = activityRepository.findAll(id_user, title, startDate, endDate, time, duration,
                distance);

        // add models for filter
        model.addAttribute("title", title);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("time", time);
        model.addAttribute("duration", duration);
        model.addAttribute("distance", distance);

        // add models for table
        model.addAttribute("totalActivity", activities.size());
        model.addAttribute("activities", activities);
        return "/user/activities";
    }

    @GetMapping("/activityEntry")
    @RequiredRole("user")
    public String activityEntryView(Activity activity, Model model) {
        int id_user = (int) session.getAttribute("id_user");
        model.addAttribute("id_user", id_user);
        return "/user/entryRun";
    }

    @PostMapping("/activityEntry")
    @RequiredRole("user")
    public String activityEntry(@Valid Activity activity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/entryRun";
        }

        MultipartFile image = activity.getImage_file();
        // ambil tanggal hari ini
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        // ambil waktu sekarang
        String formattedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
        // ambil id user
        Integer userId = activity.getId_user();
        // generate file name
        String fileName = formattedDate + "_" + formattedTime + "_" + userId + ".jpg";

        // Save image kalo usernya submit
        if (activity.getImage_file() != null && !activity.getImage_file().isEmpty()) {
            try {
                // directorynya mau disimpan dimana
                String directory = "public/images/";
                Path uploadPath = Paths.get(directory);

                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(directory + fileName), StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        // update kolom image_location
        activity.setImage_location(fileName);
        // Save activity ke database
        activityRepository.save(activity);

        return "redirect:/activity";
    }

    @GetMapping("/editRun")
    @RequiredRole("user")
    public String showEditPage(Model model, @RequestParam("id") Integer id) {
        Activity activity = activityRepository.findById(id).get(0);
        model.addAttribute("activity", activity);

        return "/user/editRun";
    }
}