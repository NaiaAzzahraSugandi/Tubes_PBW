package com.PBW.RanTreker.Activity;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ActivityController {
    private final HttpSession session;

    public ActivityController(HttpSession session) {
        this.session = session;
    }

    @Autowired
    JDBCActivityRepository activityRepository;

   @GetMapping("/dashboard")
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
    public String activityEntryView(Activity activity, Model model) {
        int id_user = (int) session.getAttribute("id_user");
        model.addAttribute("id_user", id_user);
        return "/user/entryRun";
    }

    @PostMapping("/activityEntry")
    public String activityEntry(@Valid Activity activity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/entryRun";
        }

        // // Get the current date in the desired format (ddMMyyyy)
        // String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        // // Get the user ID from the activity object
        // Integer userId = activity.getId_user();
        // // Generate the file name using the current date and user ID
        // String fileName = formattedDate + "_" + userId + ".jpg"; // or .png based on your requirement

        // // Save image if it exists
        // if (activity.getImage_file() != null && !activity.getImage_file().isEmpty()) {
        //     try {
        //         // Define the directory where the image will be saved
        //         String directory = "path/to/your/image/directory"; // Update this path
        //         // Create the file object
        //         File file = new File(directory, fileName);
        //         // Save the image file
        //         activity.getImage_file().transferTo(file);
        //         // Set the image location in the activity object
        //         activity.setImage_location(file.getAbsolutePath());
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //         // Handle the exception (e.g., log the error, return an error message)
        //     }
        // }

        // Save the activity record to the database
        activityRepository.save(activity);

        return "redirect:/activity";
    }
}