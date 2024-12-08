package com.PBW.RanTreker.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private JDBCUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean register(User user){
        try{
            userRepository.save(user);            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean login(){

        return true;
    }
}
