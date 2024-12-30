package com.PBW.RanTreker.Activity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Activity {
    private Integer id;

    @NotNull
    private Integer id_user;

    @NotBlank(message = "Please add a title!")
    private String title;

    @NotNull(message = "Distance is required!")
    @Min(value = 1, message = "Distance must be at least 1!")
    @Max(value = 999, message = "Distance must not exceed 999!")
    private Double distance;

    @NotBlank(message = "Duration is required. Please enter the time in HH:MM:SS format!")
    @Pattern(
        regexp = "^([0-9]{1,2}):([0-5][0-9]):([0-5][0-9])$",
        message = "Time must be in HH:MM:SS format (e.g., 01:23:45)."
    )
    private String duration;
    
    @NotNull(message = "Please input a date!")
    private LocalDate date;
    
    @NotNull(message = "Please input the time you went on the run!")
    private LocalTime time;
    
    @Size(max = 150, message = "Description is over 150 characters long!")
    private String description;
    
    private MultipartFile image_file;

    private String image_location;

    public String toString(){
        return "User ID: "+ id_user + " \n"
               + "Title: " + title + " \n"
               + "Distance: " + distance + " \n"
               + "Duration: " + duration + " \n"
               + "Date: " + date.toString() + " \n"
               + "Time: " + time.toString() + " \n"
               + "Description: " + description + " \n"
               + "Image location: " + image_location;
    }
}
