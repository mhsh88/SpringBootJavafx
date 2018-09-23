package com.codetreatise.config;

import com.codetreatise.bean.User;
import com.codetreatise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
        LoadUsers();
    }

    private void LoadUsers() {
        User user = userRepository.findByEmailAndPassword("admin","admin");
        if(user==null)
            userRepository.save(new User("admin", "admin"));
    }
}