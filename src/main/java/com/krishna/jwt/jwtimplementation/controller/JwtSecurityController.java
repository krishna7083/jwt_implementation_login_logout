package com.krishna.jwt.jwtimplementation.controller;

import com.krishna.jwt.jwtimplementation.entity.User;
import com.krishna.jwt.jwtimplementation.handler.CustomLoginFailureHandler;
import com.krishna.jwt.jwtimplementation.handler.CustomLoginSuccessHandler;
import com.krishna.jwt.jwtimplementation.model.AuthenticalRequest;
import com.krishna.jwt.jwtimplementation.model.AuthenticateResponse;
import com.krishna.jwt.jwtimplementation.repository.UserRepository;
import com.krishna.jwt.jwtimplementation.service.MyUserDetailsService;
import com.krishna.jwt.jwtimplementation.service.UserService;
import com.krishna.jwt.jwtimplementation.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtSecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    CustomLoginFailureHandler customLoginFailureHandler;

    @Autowired
    CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/adminhome")
    public String adminHome() {
        return "Welcome to the Admin home page";
    }

    @GetMapping("/userhome")
    public String userHome() {
        return "Welcome to the User home page";
    }

    @GetMapping("/adduser")
    public ResponseEntity<?> addUserToDatabase(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticateToken(@RequestBody AuthenticalRequest authenticalRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticalRequest.getUsername(),
                            authenticalRequest.getPassword()));
        } catch (Exception e) {
            System.out.println("Printing the Exception inside authenticate Method "+e);
            return customLoginFailureHandler.OnFailure(authenticalRequest.getUsername());
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticalRequest.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails);
        customLoginSuccessHandler.onaSuccess(authenticalRequest.getUsername());
        return ResponseEntity.ok(new AuthenticateResponse(jwt));
    }

}
