package com.krishna.jwt.jwtimplementation.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticateResponse {
    private String jwt;
}
