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

    @GetMapping("/activity")
    public String activityView(Model model){
        // mock the session
        session.setAttribute("id_user", 2);
        
        int id_user = (int) session.getAttribute("id_user");
        List<Activity> activities = activityRepository.findAll(id_user);
        model.addAttribute("totalActivity", activities.size());
        model.addAttribute("activities", activities);
        return "activities";
    }

    @GetMapping("/activityEntry")
    public String activityEntryView(Activity activity, Model model){
        model.addAttribute("id_user", (Integer)session.getAttribute("id_user"));
        return "entryRun";
    }

    @PostMapping("/activityEntry")
    public String activityEntry(@Valid Activity activity, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "entryRun";
        }

        // save image kalo ada

        // update image_location attribute

        
        activityRepository.save(activity);
        return "redirect:/activity";
    }
}
