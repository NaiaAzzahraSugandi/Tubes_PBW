package com.PBW.RanTreker.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
    private Integer id_user;
     
    @NotBlank(message = "Name is required!")
    @Size(min = 4, max = 60,  message = "Name should be 4-60 characters long!")
    private String name;

    @NotBlank(message = "Email is required!")
    @Email(message = "Enter a valid email address!")
    @Size (max = 60)
    private String email;
    
    @NotBlank(message = "Password is required!")
    @Size(min = 4, max = 60,  message = "Password should be 4-60 characters long!")
    private String password;

    @NotBlank(message = "Please re-type your password!")
    private String retypepassword;
    
    @NotBlank
    private String peran;
}
