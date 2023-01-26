package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(User siteUser) {
        User    user = User.builder()
                .username(siteUser.getUsername())
                .password(passwordEncoder.encode(siteUser.getPassword()))
                .build();
        this.userRepository.save(user);
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username); // 에러 핸들링?
    }
}
