package com.PBW.RanTreker.User;

import java.util.Optional;

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
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public User login(String email, String password) throws Exception{
        Optional<User> findUser = userRepository.findByUsername(email);
        // throw exception email belum terdaftar kalau gaada
        if(!findUser.isPresent()){
            throw new Exception("EmailFault");
        }

        User user = findUser.get();
        // cek password
        // kalau salah throw exception salah password
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new Exception("PasswordFault");
        }
        // if(!user.getPassword().equals(password)){
        //     throw new Exception("PasswordFault");
        // }

        return user;
    }
}
