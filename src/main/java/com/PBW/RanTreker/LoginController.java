package com.PBW.RanTreker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.PBW.RanTreker.User.User;

@Controller
public class LoginController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String loginView(User user){
        return "login";
    }
}
