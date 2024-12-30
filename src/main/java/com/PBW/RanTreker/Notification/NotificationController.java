package com.PBW.RanTreker.Notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.PBW.RanTreker.RequiredRole;

@Controller
@RequestMapping("/user")
public class NotificationController {
    @GetMapping("/notifications")
    @RequiredRole("user")
    public String viewNotifications(Model model){
        List<Notification> notifications = new ArrayList<>();

        model.addAttribute("notifications", notifications);
        return "/user/notifications";
    }
}
