package src.main.java.com.krishna.jwt.jwtimplementation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtSecurityController {

    @GetMapping("/adminhome")
    public String adminHome() {
        return "Welcome to the Admin home page";
    }

    @GetMapping("/userhome")
    public String userHome() {
        return "Welcome to the User home page";
    }

//    @GetMapping("/adduser")
//    public ResponseEntity<?> addUserToDatabase(RequestBody User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return ResponseEntity.ok(userRepository.save(user));
//    }



}
