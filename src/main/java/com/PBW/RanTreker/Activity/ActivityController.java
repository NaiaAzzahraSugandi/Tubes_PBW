package com.PBW.RanTreker.Activity;

import java.io.File;
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
import java.util.Map;

import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import javax.imageio.ImageIO;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.ByteArrayOutputStream;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

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
        
        model.addAttribute("activitySummary", activitySummary);
    
        return "/user/dashboard";  
    }

    @GetMapping("/chart")
    @RequiredRole("user")
    public ResponseEntity<byte[]> getChartImage(@RequestParam String type) {
        Integer userId = (Integer) session.getAttribute("id_user");

        Map<String, Integer> activitySummary;
        switch (type.toLowerCase()) {
            case "monthly":
                activitySummary = activityRepository.getActivitySummaryByMonth(userId);
                break;
            case "yearly":
                activitySummary = activityRepository.getActivitySummaryByYear(userId);
                break;
            case "weekly":
            default:
                activitySummary = activityRepository.getActivitySummaryByWeek(userId);
                break;
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        activitySummary.forEach((label, distance) -> dataset.addValue(distance, "Distance", label));

        JFreeChart barChart = ChartFactory.createBarChart(
            type.substring(0, 1).toUpperCase() + type.substring(1) + " Activity Summary",
            "Time", 
            "Distance (km)", 
            dataset,  // Dataset
            PlotOrientation.VERTICAL,
            true, true, false 
        );

        barChart.setBackgroundPaint(Color.WHITE); 

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE); 

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0xFC4C02)); 
        renderer.setSeriesOutlinePaint(0, Color.BLACK);  
        renderer.setSeriesOutlineStroke(0, new BasicStroke(2.0f)); 

        Font titleFont = new Font("Arial", Font.BOLD, 18); 
        barChart.getTitle().setFont(titleFont);

        Font axisFont = new Font("Arial", Font.PLAIN, 14);  
        plot.getDomainAxis().setLabelFont(axisFont);
        plot.getRangeAxis().setLabelFont(axisFont);
        plot.getDomainAxis().setTickLabelFont(axisFont);
        plot.getRangeAxis().setTickLabelFont(axisFont);

        // Convert chart to byte array
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(barChart.createBufferedImage(800, 600), "png", baos);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        activity.setId_user(id_user);
        model.addAttribute("id_user", id_user);
        return "/user/entryRun";
    }

    @PostMapping("/activityEntry")
    @RequiredRole("user")
    public String activityEntry(@Valid Activity activity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // log errors
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getDefaultMessage());
            });
            model.addAttribute("activity", activity);
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

        redirectAttributes.addFlashAttribute("successMessage", "Activity has been added successfully!");

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
    public String editRun(@Valid Activity activity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
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
                
                if(!activity.getImage_location().equals("")){
                    Path oldImagePath = Paths.get(directory + activity.getImage_location());
                    try{
                        Files.delete(oldImagePath);
                    }
                    catch(IOException e){
                        System.out.println(e.getMessage());
                    }
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

        redirectAttributes.addFlashAttribute("successMessage", "Activity has been edited successfully!");

        return "redirect:/user/activity";
    }

    @GetMapping("/delete")
    @RequiredRole("user")
    public String deleteActivity(@RequestParam int id, RedirectAttributes redirectAttributes) {
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

        redirectAttributes.addFlashAttribute("successMessage", "Activity has been deleted successfully!");
    
        return "redirect:/user/activity";
    }

    @GetMapping("/exportChart")
    @RequiredRole("user")
    public ResponseEntity<byte[]> exportChartsToPdf() {
        Integer userId = (Integer) session.getAttribute("id_user");

        byte[] weeklyChartImage = generateChartImage(activityRepository.getActivitySummaryByWeek(userId), "Weekly Activity Summary");
        byte[] monthlyChartImage = generateChartImage(activityRepository.getActivitySummaryByMonth(userId), "Monthly Activity Summary");
        byte[] yearlyChartImage = generateChartImage(activityRepository.getActivitySummaryByYear(userId), "Yearly Activity Summary");

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Activity Summary").setBold().setFontSize(18));

            document.add(new Image(com.itextpdf.io.image.ImageDataFactory.create(weeklyChartImage)).scaleToFit(500, 500));

            document.add(new Image(com.itextpdf.io.image.ImageDataFactory.create(monthlyChartImage)).scaleToFit(500, 500));

            document.add(new Image(com.itextpdf.io.image.ImageDataFactory.create(yearlyChartImage)).scaleToFit(500, 500));

            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "ActivitySummary.pdf");

            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private byte[] generateChartImage(Map<String, Integer> activitySummary, String chartTitle) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        activitySummary.forEach((label, distance) -> dataset.addValue(distance, "Distance", label));
    
        JFreeChart chart = ChartFactory.createBarChart(
            chartTitle,            
            "Time",                 
            "Distance (km)",        
            dataset,               
            PlotOrientation.VERTICAL, 
            true, true, false      
        );
    
        chart.setBackgroundPaint(java.awt.Color.WHITE); 
    
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(java.awt.Color.WHITE);  
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new java.awt.Color(0xFC4C02)); 
        renderer.setSeriesOutlinePaint(0, java.awt.Color.BLACK);  
        renderer.setSeriesOutlineStroke(0, new java.awt.BasicStroke(2.0f));  
    
        java.awt.Font titleFont = new java.awt.Font("Arial", java.awt.Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);
    
        java.awt.Font axisFont = new java.awt.Font("Arial", java.awt.Font.PLAIN, 14); 
        plot.getDomainAxis().setLabelFont(axisFont);
        plot.getRangeAxis().setLabelFont(axisFont);
        plot.getDomainAxis().setTickLabelFont(axisFont);
        plot.getRangeAxis().setTickLabelFont(axisFont);
    
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(chart.createBufferedImage(800, 600), "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0]; 
        }
    }
}