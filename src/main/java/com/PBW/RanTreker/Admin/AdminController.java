package com.PBW.RanTreker.Admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PBW.RanTreker.RequiredRole;
import com.PBW.RanTreker.Activity.Activity;
import com.PBW.RanTreker.Activity.JDBCActivityRepository;
import com.PBW.RanTreker.User.JDBCUserRepository;
import com.PBW.RanTreker.User.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    JDBCUserRepository userRepository;

    @Autowired
    JDBCActivityRepository activityRepository;

    private final HttpSession session;

    public AdminController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/members")
    @RequiredRole("admin")
    public String memberView(Model model,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "nameSort", required = false, defaultValue = "ASC") String nameSort,
            @RequestParam(value = "peran", required = false, defaultValue = "") String peran) {

        List<User> users = userRepository.findAll(name, nameSort, peran);

        model.addAttribute("users", users);
        model.addAttribute("name", name);
        model.addAttribute("nameSort", nameSort);
        model.addAttribute("peran", peran);
        model.addAttribute("totalUser", users.size());
        return "/admin/members";
    }

    @GetMapping("/editMember")
    @RequiredRole("admin")
    public String editMemberView(@RequestParam(value = "id", required = true) int id,
            Model model) {

        User user = userRepository.findByID(id);
        model.addAttribute("user", user);

        return "/admin/memberedit";
    }

    @PostMapping("/editMember")
    @RequiredRole("admin")
    public String editMember(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/memberedit";
        }

        userRepository.updateMember(user.getId_user(), user.getName(), user.getEmail(), user.getPeran());

        return "redirect:/admin/members";
    }

    @GetMapping("delete")
    @RequiredRole("admin")
    public String deleteMember(@RequestParam(value = "id", required = true) int id) {
        // delete activities milik member
        List<Activity> activitiesToDelete = activityRepository.findByUserID(id);
        System.out.println(activitiesToDelete.size());
        // buang setiap activity yang ditemukan
        for (Activity activity : activitiesToDelete) {
            // delete image kalo misalnya ada
            if (activity.getImage_location() != null && !activity.getImage_location().isEmpty()) {
                try {
                    Path imagePath = Paths.get("public/images/" + activity.getImage_location());
                    Files.deleteIfExists(imagePath);
                } catch (IOException e) {
                    System.out.println("Error deleting image: " + e.getMessage());
                }
            }

            // delete record
            activityRepository.deleteRun(activity.getId());

            System.out.println(activity.toString());
        }

        // delete member
        userRepository.deleteMember(id);
        return "redirect:/admin/members";
    }

    @GetMapping("/logout")
    @RequiredRole("admin")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

}
