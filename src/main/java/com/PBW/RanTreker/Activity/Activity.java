package com.PBW.RanTreker.Activity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Activity {
    @NotNull
    private Integer id_user;

    @NotBlank(message = "Please add a title!")
    private String title;

    @NotNull(message = "Distance is required!")
    @Min(value = 1, message = "Distance must be at least 1!")
    @Max(value = 999, message = "Distance must not exceed 999!")
    private Integer distance;

    @NotNull(message = "Duration is required!")
    @Min(value = 1, message = "Duration must be at least 1 minute!")
    @Max(value = 1440, message = "Duration must not exceed 1440 minutes (24 hours)!")
    private Integer duration;
    
    @NotNull(message = "Please input a date!")
    private LocalDate date;
    
    @NotNull(message = "Please input the time you went on the run!")
    private LocalTime time;
    
    @Size(max = 150, message = "Description is over 150 characters long!")
    private String description;
    
    private String image_location;

    public String toString(){
        return id_user + " \n"
               + title + " \n"
               + distance + " \n"
               + duration + " \n"
               + date.toString() + " \n"
               + time.toString() + " \n"
               + description +" \n";
    }
}
