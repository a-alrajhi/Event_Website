package com.event_website.Controller;

import com.event_website.Entity.User;
import com.event_website.Request.RegisterRequest;
import com.event_website.Service.UserService;
import com.event_website.Utils.JwtUtil;
import com.event_website.Utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserService userService;
    @Autowired
    SecurityUtil securityUtil;

    @GetMapping("/")
    public ResponseEntity<User> getUser() {
        User user = securityUtil.getCurrentUser();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}


