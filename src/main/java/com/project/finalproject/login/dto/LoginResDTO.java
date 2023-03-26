package com.project.finalproject.login.dto;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.company.entity.Company;
import io.jsonwebtoken.Claims;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResDTO {
    private String email;
    private String role;
    private Applicant applicant;
    private Company company;
    private String accessToken;
    private String refreshToken;

    public LoginResDTO(Claims claims){
        this.email = claims.get("userEmail", String.class);
        this.role = claims.get("role", String.class);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

}
