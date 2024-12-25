package com.PBW.RanTreker.Races;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Race {
    private int id;
    private String name;
    private double length;
    private LocalDateTime dateTime;
}
