package com.PBW.RanTreker.Races;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Race {
    private int id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double distance;
    private String status;
}
