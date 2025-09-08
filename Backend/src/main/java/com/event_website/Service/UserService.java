package com.event_website.Service;

import com.event_website.Entity.User;
import com.event_website.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public User register(User user) throws Exception {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Username is already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        return user;
    }

}
