package com.project.finalproject.password.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class PasswordDTO {
    private String email;
    private String password;
    private String role;
    private String name;
    private Long id;
}
