package com.krishna.jwt.jwtimplementation.handler;

import com.krishna.jwt.jwtimplementation.entity.User;
import com.krishna.jwt.jwtimplementation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String userName = request.getParameter("userName");
        User user = userService.getByUserName(userName);

        if(user.getFailedAttempt()> 0) {
            userService.resetFailedAttempts(user.getUsername());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    public void onaSuccess(String userName) {
        userService.resetFailedAttempts(userName);
    }
}
