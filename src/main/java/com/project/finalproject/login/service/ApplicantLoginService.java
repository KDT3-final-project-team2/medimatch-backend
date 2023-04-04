package com.project.finalproject.login.service;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.global.jwt.utils.JwtProperties;
import com.project.finalproject.global.jwt.utils.JwtUtil;
import com.project.finalproject.login.dto.LoginReqDTO;
import com.project.finalproject.login.dto.LoginResDTO;
import com.project.finalproject.login.repository.ApplicantLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicantLoginService {

    private final ApplicantLoginRepository applicantLoginRepository;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인
     */
    @Transactional(readOnly = true)
    public ResponseDTO userLogin(LoginReqDTO req){
        if(!applicantLoginRepository.existsApplicantByEmail(req.getEmail())){
            return new ResponseDTO(401, false, "INVALID_ID", "잘못된 ID입니다.");
        }

        Applicant findUser = applicantLoginRepository.findApplicantByEmail(req.getEmail()).orElseThrow();

        if(!checkPassword(req.getPassword(), findUser.getPassword())){
            return new ResponseDTO(401, false, "INVALID_PASSWORD", "잘못된 비밀번호입니다.");
        }

        String accessToken = jwtUtil.createAccessToken(findUser.getEmail(), jwtProperties.getSecretKey(), "USER", findUser.getId());
        String refreshToken = jwtUtil.createRefreshToken(findUser.getEmail(), jwtProperties.getSecretKey(), "USER", findUser.getId());

        return new ResponseDTO(200, true, LoginResDTO.builder()
                .email(findUser.getEmail())
                .role("USER")
                .name(findUser.getName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build(), "로그인 성공!");
    }

    /**
     * 비밀번호 일치여부 확인
     */
    private boolean checkPassword(String inputPassword, String originalPassword){
        return passwordEncoder.matches(inputPassword, originalPassword);
    }
}
