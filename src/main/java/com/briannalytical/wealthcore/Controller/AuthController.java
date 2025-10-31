package com.briannalytical.wealthcore.Controller;

import com.briannalytical.wealthcore.Dto.AuthResponse;
import com.briannalytical.wealthcore.Dto.RegisterRequest;
import com.briannalytical.wealthcore.Model.Entity.User;
import com.briannalytical.wealthcore.Security.JwtUtil;
import com.briannalytical.wealthcore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Allow frontend to access - adjust in production
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    // register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // check if username exists
            if (userService.existsByUsername(request.getUsername())) {
                return ResponseEntity.badRequest().body("Username already exists");
            }

            // register user
            User user = userService.registerUser(
                    request.getUsername(),
                    request.getPassword(),
                    request.getEmail()
            );

            // generate session token
            String token = jwtUtil.generateToken(user.getUsername());

            // return response with token
            return ResponseEntity.ok(new AuthResponse(token, user.getUsername()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + e.getMessage());
        }
    }
}
