package com.PBW.RanTreker.Admin;

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
import com.PBW.RanTreker.User.JDBCUserRepository;
import com.PBW.RanTreker.User.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    JDBCUserRepository userRepository;

    private final HttpSession session;

    public AdminController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/members")
    @RequiredRole("admin")
    public String memberView(Model model,
            @RequestParam(value = "name", required = false, defaultValue = "") String name) {

        List<User> users = userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("name", name);
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

    @GetMapping("/races")
    @RequiredRole("admin")
    public String racesView() {

        return "/admin/races";
    }

    @GetMapping("/logout")
    @RequiredRole("admin")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

}
