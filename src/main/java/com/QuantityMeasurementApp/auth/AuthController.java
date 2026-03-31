package com.QuantityMeasurementApp.auth;

import com.QuantityMeasurementApp.model.User;
import com.QuantityMeasurementApp.repository.UserRepository;
import com.QuantityMeasurementApp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // POST /auth/register - Create new user
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody AuthRequest request) {

        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponse(null, null, 
                          "Username already exists!"));
        }

        // Save new user with encrypted password
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                "ROLE_USER"
        );
        userRepository.save(user);

        return ResponseEntity.ok(
                new AuthResponse(null, request.getUsername(), 
                                "User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request) {

        try {
            // Authenticate username and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));

            // Load user details and generate token
            UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(
                    new AuthResponse(token, request.getUsername(),
                                    "Login successful!"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null,
                          "Invalid username or password!"));
        }
    }
    
 // GET /auth/oauth2/success - Called after Google login
 // Returns the JWT token to the user
 @GetMapping("/oauth2/success")
 public ResponseEntity<AuthResponse> oauth2Success(
         @RequestParam String token,
         @RequestParam String username) {

     return ResponseEntity.ok(
             new AuthResponse(token, username, 
                             "Google login successful!"));
 }
}