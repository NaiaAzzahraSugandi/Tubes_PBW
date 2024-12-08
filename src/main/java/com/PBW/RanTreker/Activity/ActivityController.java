package com.PBW.RanTreker.Activity;

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
    @Autowired
    JDBCActivityRepository activityRepository;

    @GetMapping("/activity")
    public String activityView(){
        return "activities";
    }

    @GetMapping("/activityEntry")
    public String activityEntryView(Activity activity, HttpSession session, Model model){
        session.setAttribute("id_user", 2);
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
