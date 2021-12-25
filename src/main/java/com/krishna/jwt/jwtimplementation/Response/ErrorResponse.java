package com.krishna.jwt.jwtimplementation.Response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse {
    private String message;
    private HttpStatus httpStatus;
}
