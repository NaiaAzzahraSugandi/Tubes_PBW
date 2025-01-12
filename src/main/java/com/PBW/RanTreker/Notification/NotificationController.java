package com.PBW.RanTreker.Notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.PBW.RanTreker.RequiredRole;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class NotificationController {
    private final HttpSession session;

    @Autowired
    private JDBCNotificationRepository notificationRepository;

    public NotificationController(HttpSession httpSession) {
        this.session = httpSession;
    }
    
    @GetMapping("/notifications")
    @RequiredRole("user")
    public String viewNotifications(Model model){
        List<Notification> notifications = notificationRepository.getAllNotifications((int)session.getAttribute("id_user"));

        model.addAttribute("notifications", notifications);
        return "/user/notifications";
    }

    @GetMapping("/notifications/delete")
    @RequiredRole("user")
    public String deleteNotification(int id, RedirectAttributes redirectAttributes){
        notificationRepository.deleteNotification(id);

        redirectAttributes.addFlashAttribute("successMessage", "Notification successfully deleted!");
        return "redirect:/user/notifications";
    }

    @GetMapping("/notifications/deleteAll")
    @RequiredRole("user")
    public String deleteAllNotification(RedirectAttributes redirectAttributes){
        notificationRepository.deleteAllNotifications((int)session.getAttribute("id_user"));

        redirectAttributes.addFlashAttribute("successMessage", "Notifications cleared!");
        return "redirect:/user/notifications";
    }
}
