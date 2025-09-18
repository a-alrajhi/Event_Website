package com.event_website.Service;

import com.event_website.Entity.User;
import com.event_website.Exception.AuthenticationFailureException;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Repository.UserRepo;
import com.event_website.Utils.QueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    private final int pageSize = 15;

    public Optional<User> findByUsername(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public List<User> findAll(Integer page, Integer pageLimit) {
        List<User> users;
        if(page != null && pageLimit != null && page > 0 && pageLimit > 0) {
            int offset = QueryUtil.getOffset(page, pageLimit);
            users = userRepository.findUsersWithPagination(pageLimit, offset);
        }
        else {
            users = userRepository.findAll();
        }
        return users;
    }

    public User register(User user) throws Exception {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new AuthenticationFailureException("Email is already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        return user;
    }

    public User delete(int id) {
        User user = this.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(id);
        return user;
    }

    public User update(User user) {
        if(!userRepository.existsById(user.getId())) {
            throw new ResourceNotFoundException("User not found");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
