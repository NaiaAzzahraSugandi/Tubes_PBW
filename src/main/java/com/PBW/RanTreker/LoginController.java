package com.PBW.RanTreker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PBW.RanTreker.User.User;
import com.PBW.RanTreker.User.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    private final HttpSession session;

    public LoginController(HttpSession session){
        this.session = session;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String loginView(){
        String email = (String) session.getAttribute("email");
        if(email == null || email.length() == 0){
            return "login";
        }

        String peran = (String) session.getAttribute("peran");
        if(peran.equals("admin")){
            return "redirect:/admin/members";
        }
        else{
            return "redirect:/user/dashboard";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam(required = true) String email,
                        @RequestParam(required = true) String password,
                        Model model){

        User user = null;
        try{
            user = userService.login(email, password);
        }
        catch(Exception e){
            if(e.getMessage().equals("EmailFault")){
                model.addAttribute("error", "EmailFault");
            }
            else{
                model.addAttribute("error", "PasswordFault");
            }
        }
        
        if(user == null){
            return "login";
        }

        session.setAttribute("id_user", user.getId_user());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("nama", user.getName());
        session.setAttribute("peran", user.getPeran());

        if(user.getPeran().equals("admin")){
            return "redirect:/admin/members";
        }

        return "redirect:/user/dashboard";
    }

    @GetMapping("/wrongRole")
    public String wrongRole(){
        return "wrongRole";
    }

    @GetMapping("/logout")
    public String logout(){
        session.invalidate();
        return "redirect:/";
    }

}
