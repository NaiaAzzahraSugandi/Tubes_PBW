package com.PBW.RanTreker.Activity;

import java.io.File;
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
import java.util.Map;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
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

        Integer userId = (Integer) session.getAttribute("id_user");
        Map<String, Integer> activitySummary = activityRepository.getActivitySummaryByMonth(userId);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        activitySummary.forEach((month, distance) -> dataset.addValue(distance, "Distance", month));

        JFreeChart barChart = ChartFactory.createBarChart(
            "Monthly Activity Summary",
            "Month",
            "Distance (km)",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false);

        File chartFile = new File("src/main/resources/static/img/chart.png");
        try {
            ImageIO.write(barChart.createBufferedImage(800, 600), "png", chartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("chartImage", "/chart.png");

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

                // buat directorynya kalau belum ada
                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                // save file ke directory
                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(directory + fileName), StandardCopyOption.REPLACE_EXISTING);
                }

                // update kolom image_location
                activity.setImage_location(fileName);
            } 
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        // Save activity ke database
        activityRepository.save(activity);

        return "redirect:/user/activity";
    }

    @GetMapping("/editRun")
    @RequiredRole("user")
    public String showEditPage(Model model, @RequestParam("id") Integer id) {
        Activity activity = activityRepository.findById(id).get(0);
        model.addAttribute("activity", activity);
        model.addAttribute("prevImage", activity.getImage_location());

        return "/user/editRun";
    }

    @PostMapping("/editRun")
    @RequiredRole("user")
    public String editRun(@Valid Activity activity, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            // log errors
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getDefaultMessage());
            });
            // tambah model activity untuk mencegah data hilang
            model.addAttribute("activity", activity);
            return "/user/editRun";
        }

        try{
            // periksa apakah user submit image baru
            if(!activity.getImage_file().isEmpty()){
                // buang image yang lama
                String directory = "public/images/";
                Path oldImagePath = Paths.get(directory + activity.getImage_location());
    
                try{
                    Files.delete(oldImagePath);
                }
                catch(IOException e){
                    System.out.println(e.getMessage());
                }
    
                // save image yang baru
                MultipartFile image = activity.getImage_file();
                // ambil tanggal hari ini
                String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
                // ambil waktu sekarang
                String formattedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
                // ambil id user
                Integer userId = activity.getId_user();
                // generate file name
                String fileName = formattedDate + "_" + formattedTime + "_" + userId + ".jpg";
    
                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(directory + fileName), StandardCopyOption.REPLACE_EXISTING);
                }

                // set isi kolom image_location dengan lokasi baru
                activity.setImage_location(fileName);
    
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        // update run
        activityRepository.updateRun(activity.getId(), activity.getTitle(), activity.getDescription(), activity.getImage_location());

        return "redirect:/user/activity";
    }

    @GetMapping("/delete")
    @RequiredRole("user")
    public String deleteActivity(@RequestParam int id) {
        Activity activity = activityRepository.findById(id).get(0); 

        // delete image kalo misalnya ada
        if (activity.getImage_location() != null && !activity.getImage_location().isEmpty()) {
            try {
                Path imagePath = Paths.get("public/images/" + activity.getImage_location());
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                System.out.println("Error deleting image: " + e.getMessage());
            }
        }
    
        // delete record
        activityRepository.deleteRun(id);
    
        return "redirect:/user/activity";
    }
}