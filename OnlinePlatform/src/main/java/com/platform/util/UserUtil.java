package com.platform.util;

import com.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserUtil {

    @Autowired
    private UserRepository userRepository;

    public int newUserId(){
        try{
            return userRepository.findMaxId() + 1;
        } catch (Exception e){
            return 1;
        }
    }

}
