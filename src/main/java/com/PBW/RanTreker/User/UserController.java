package com.PBW.RanTreker.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String registerView(User user){
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "register";
        }
        
        if(!user.getPassword().equals(user.getRetypepassword())){
            bindingResult.rejectValue("retypepassword", "MismatchPassword","Password does not match!");
            return "register";
        }
        System.out.println(user.getName() + " " + user.getEmail() + " " + user.getPassword() + " " + user.getPeran());
        boolean registerStatus = userService.register(user);
        if(!registerStatus){
            bindingResult.rejectValue("email", "EmailDuplicate", "This email has been registered!");
            return "register";
        }
        return "redirect:/results";
    }

    @GetMapping("/results")
    public String resultView(){
        return "registerResult";
    }
}
