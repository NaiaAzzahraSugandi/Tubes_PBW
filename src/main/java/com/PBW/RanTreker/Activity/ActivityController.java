package com.PBW.RanTreker.Activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ActivityController {
    private final HttpSession session;

    public ActivityController(HttpSession session){
        this.session = session;
    }

    @Autowired
    JDBCActivityRepository activityRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        String nama = (String) session.getAttribute("nama");
        model.addAttribute("nama", nama);
        return "/user/dashboard";
    }

    @GetMapping("/activity")
    public String activityView(Model model){
        int id_user = (int) session.getAttribute("id_user");
        List<Activity> activities = activityRepository.findAll(id_user);
        model.addAttribute("totalActivity", activities.size());
        model.addAttribute("activities", activities);
        return "/user/activities";
    }

    @GetMapping("/activityEntry")
    public String activityEntryView(Activity activity, Model model){
        int id_user = (int) session.getAttribute("id_user");
        model.addAttribute("id_user", id_user);
        return "/user/entryRun";
    }

    @PostMapping("/activityEntry")
    public String activityEntry(@Valid Activity activity, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/user/entryRun";
        }

        // save image kalo ada

        // update image_location attribute

        
        activityRepository.save(activity);
        return "redirect:/activity";
    }
}
