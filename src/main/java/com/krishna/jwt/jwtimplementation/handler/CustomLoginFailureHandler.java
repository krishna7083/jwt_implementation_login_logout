package com.krishna.jwt.jwtimplementation.handler;

import com.krishna.jwt.jwtimplementation.Response.ErrorResponse;
import com.krishna.jwt.jwtimplementation.entity.User;
import com.krishna.jwt.jwtimplementation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            System.out.println("Parameter Name: " + paramName + " Value: " + request.getParameter(paramName));
        }
        String userName = request.getParameter(("userName"));
        User user = userService.getByUserName(userName);
        if (user != null) {
            if (user.isActive() && user.isAccountNonLocked()) {
                if (user.getFailedAttempt() < UserService.MAX_FAILED_ATTEMPTS - 1) {
                    userService.increaseFailedAttempts(user);
                } else {
                    userService.lock(user);
                    exception = new LockedException("your Account has been locked due to " +
                            "3 failed attempts It willl be unlocked after 24 Hours");

                }
            } else if (!user.isAccountNonLocked()) {
                if (userService.unlockWhenTimeExpired(user)) {
                    exception = new LockedException("You Account has been unlocked. Please try to login again");
                }
            }
        } else {
            exception = new UsernameNotFoundException("Username not found");
        }

        super.onAuthenticationFailure(request, response, exception);
    }

    public ResponseEntity<?> OnFailure(String userName) {

        User user = userService.getByUserName(userName);
        ErrorResponse errorResponse = new ErrorResponse();
        if (user != null) {
            if (user.isActive() && user.isAccountNonLocked()) {
                if (user.getFailedAttempt() < UserService.MAX_FAILED_ATTEMPTS - 1) {
                    userService.increaseFailedAttempts(user);
                    errorResponse.setMessage(String.format("You have %s attempts left in order to make" +
                            "a successfull login to your account", 3 - user.getFailedAttempt()));
                    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
                } else {
                    userService.lock(user);
                    errorResponse.setMessage("you Account has been locked due to 3 failed Attempts" +
                            "It will be get unlocked after 24 Hours");
                    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
                }
            } else if (!user.isAccountNonLocked()) {
                if (userService.unlockWhenTimeExpired(user)) {
                    errorResponse.setMessage("Your Account has been unlocked, please try to login again");
                    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
                }
            }

        }
        errorResponse.setMessage("you have entered the wrong username");
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
