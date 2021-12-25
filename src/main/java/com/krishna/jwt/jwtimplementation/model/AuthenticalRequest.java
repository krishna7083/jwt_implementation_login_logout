package com.krishna.jwt.jwtimplementation.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticalRequest {
    private String username;
    private String password;
}
