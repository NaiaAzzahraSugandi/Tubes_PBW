package com.PBW.RanTreker.Races;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Race {
    private int raceID;

    @NotBlank(message = "Please enter a name for the race!")
    private String title;
    
    @NotNull(message = "Please enter a start time for the race!")
    private LocalDateTime startTime;

    @NotNull(message = "Please enter an end time for the race!")
    private LocalDateTime endTime;

    @NotNull(message = "Please enter the distance for the race!")
    @Min(value = 0, message = "Please enter a valid distance")
    @Max(value = 999, message = "Please enter a valid distance")
    private double distance;

    private String status;

    private int participants = 0;
}
