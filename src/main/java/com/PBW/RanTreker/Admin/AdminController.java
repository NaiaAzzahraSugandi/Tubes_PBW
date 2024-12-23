package com.PBW.RanTreker.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PBW.RanTreker.RequiredRole;
import com.PBW.RanTreker.User.JDBCUserRepository;
import com.PBW.RanTreker.User.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    JDBCUserRepository userRepository;

    private final HttpSession session;

    public AdminController(HttpSession session){
        this.session = session;
    }

    @GetMapping("/members")
    @RequiredRole("admin")
    public String memberView(Model model,
                            @RequestParam(value = "name", required = false, defaultValue = "") String name){
        
        List<User> users = userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("name", name);
        model.addAttribute("totalUser", users.size());
        return "/admin/members";
    }

    @GetMapping("/races")
    @RequiredRole("admin")
    public String racesView(){

        return "/admin/races";
    }
}
