package com.krishna.jwt.jwtimplementation.service;

import com.krishna.jwt.jwtimplementation.entity.User;
import com.krishna.jwt.jwtimplementation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(()-> new UsernameNotFoundException("User not found "+username));
        return user.map(MyUserDetail::new).get();
    }
}
