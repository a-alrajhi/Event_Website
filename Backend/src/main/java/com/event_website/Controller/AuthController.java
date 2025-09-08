package com.event_website.Controller;

import com.event_website.Entity.User;
import com.event_website.Request.RegisterRequest;
import com.event_website.Service.UserService;
import com.event_website.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RegisterRequest request) {
        try {
            System.out.println("Attempting auth for: " + request.getEmail());
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            String accessToken = jwtUtil.generateToken(request.getEmail(), 1000 * 60 * 15); // 15 min
            String refreshToken = jwtUtil.generateToken(request.getEmail(), 1000 * 60 * 60 * 24); // 24 hrs
            System.out.println("Auth successful!");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add("Refresh", refreshToken);

            ResponseEntity<?> response = new ResponseEntity(
                    Map.of("accessToken", accessToken, "refreshToken", refreshToken),
                    headers, HttpStatus.OK);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = new User(registerRequest);
            userService.register(user);

            String accessToken = jwtUtil.generateToken(user.getEmail(), 1000 * 60 * 20); // 20 min
            System.out.println("Auth successful!");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);

            ResponseEntity<?> response = new ResponseEntity(
                    Map.of("accessToken", accessToken),
                    headers, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (jwtUtil.isTokenValid(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            String accessToken = jwtUtil.generateToken(username, 1000 * 60 * 15); // new access token
            return ResponseEntity.ok(Map.of("accessToken", accessToken));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }
}


