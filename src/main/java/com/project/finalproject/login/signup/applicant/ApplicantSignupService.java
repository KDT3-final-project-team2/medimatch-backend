package com.project.finalproject.login.signup.applicant;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.global.jwt.utils.JwtUtil;
import com.project.finalproject.login.dto.ApplicantSignupReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicantSignupService {

    private final ApplicantSignupRepository applicantSignupRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public String signUp(ApplicantSignupReqDTO reqDTO){
        if(applicantSignupRepository.existsApplicantByEmail(reqDTO.getEmail())){
            return reqDTO.getEmail() + "는 이미 존재하는 아이디 입니다.";
        }

        reqDTO.setPassword(passwordEncoding(reqDTO.getPassword()));

        Applicant applicant = reqDTO.toEntity();

        applicantSignupRepository.save(applicant);

        return "회원가입이 완료되었습니다.";
    }

    /**
     * 비밀번호 인코딩
     */
    private String passwordEncoding(String password){
        return passwordEncoder.encode(password);
    }
}
