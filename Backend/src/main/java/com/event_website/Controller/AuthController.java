package com.event_website.Controller;

import com.event_website.Entity.User;
import com.event_website.Exception.AuthenticationFailureException;
import com.event_website.Logging.LogRequest;
import com.event_website.Request.AuthRequest;
import com.event_website.Request.RegisterRequest;
import com.event_website.Service.UserService;
import com.event_website.Utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {
  @Autowired private JwtUtil jwtUtil;
  @Autowired private AuthenticationManager authManager;

  @Autowired private UserService userService;

    @Operation(
            summary = "Login user",
            description = "Authenticates the user with email and password. Returns a JWT access token.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AuthRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Login example",
                                            value = """
                        {
                          "email": "user@example.com",
                          "password": "password123"
                        }
                        """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful, token returned", content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "{\"accessToken\": \"eyJhbGciOi...\"}")
                            )),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @LogRequest(description = "Login attempt for user")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
            try {
                Authentication auth = authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                );

      String accessToken = jwtUtil.generateToken(request.getEmail(), 1000 * 60 * 260); // 15 min
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer " + accessToken);

      return new ResponseEntity<>(Map.of("accessToken", accessToken), headers, HttpStatus.OK);

    } catch (BadCredentialsException e) {
      throw new AuthenticationFailureException("Invalid email or password");
    } catch (AuthenticationException e) {
      throw new AuthenticationFailureException("Authentication failed: " + e.getMessage());
    }
  }

    @Operation(
            summary = "Register a new user",
            description = "Registers a new user account and returns a JWT access token.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RegisterRequest.class),
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Register example",
                                            value = """
                        {
                          "email": "newuser@example.com",
                          "password": "password123",
                          "name": "New User"
                          "phoneNumber": "+96651234567"
                        }
                        """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully, token returned",  content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{\"accessToken\": \"eyJhbGciOi...\"}")
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid input data (e.g., email taken, password less than 6 chars)"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @LogRequest(description = "Registering a new user")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        User user = new User(registerRequest);
        userService.register(user);

    String accessToken = jwtUtil.generateToken(user.getEmail(), 1000 * 60 * 60); // 20 min
    System.out.println("Auth successful!");
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);

    ResponseEntity<?> response =
        new ResponseEntity(Map.of("accessToken", accessToken), headers, HttpStatus.OK);
    return response;
  }
}
