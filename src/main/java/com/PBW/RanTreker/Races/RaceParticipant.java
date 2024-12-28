package com.PBW.RanTreker.Races;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RaceParticipant {
    private int id;
    private int race_id;
    private int user_id;
    private String name;
    private LocalDateTime registration_date;

    @NotNull(message = "Distance cannot be empty. Please enter the distance you ran!")
    @DecimalMin(value = "0.1", message = "Distance must be at least 0.1 km.")
    @DecimalMax(value = "999", message = "Distance cannot exceed 999 km.")
    private double distance;

    @NotBlank(message = "Time duration is required. Please enter the time in HH:MM:SS format!")
    @Pattern(
        regexp = "^([0-9]{1,2}):([0-5][0-9]):([0-5][0-9])$",
        message = "Time must be in HH:MM:SS format (e.g., 01:23:45)."
    )
    private String duration;

    private double speed_km_min;
    private MultipartFile image_file;
    private String image_location;
}
