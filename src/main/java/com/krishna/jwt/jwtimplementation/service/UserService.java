package com.krishna.jwt.jwtimplementation.service;

import com.krishna.jwt.jwtimplementation.entity.User;
import com.krishna.jwt.jwtimplementation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    public static final int MAX_FAILED_ATTEMPTS = 3;

    private static final long LOCK_TIME_DURATION = 24*60*60*1000; // 24 hours

    @Autowired
    private UserRepository userRepository;

    public User getByUserName(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        if(!user.isEmpty()) {
            return user.get();
        }
        return null;
    }

    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt()+1;
        userRepository.updateFailedAttempts(newFailAttempts, user.getUsername());
    }

    public void resetFailedAttempts(String username) {
        userRepository.updateFailedAttempts(0,username);
    }

    public void lock(User user) {
        user.setAccountNonLocked(false);
        userRepository.save(user);
    }

    public boolean unlockWhenTimeExpired(User user) {
        long currentTimeInMillis = System.currentTimeMillis();
        if(LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            user.setFailedAttempt(0);
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
