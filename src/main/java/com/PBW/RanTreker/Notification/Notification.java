package com.PBW.RanTreker.Notification;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Notification {
    private int id;
    private int user_id;
    private LocalDateTime createdDate;
    private String message;
}
