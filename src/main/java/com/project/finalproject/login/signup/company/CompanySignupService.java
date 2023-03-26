package com.project.finalproject.login.signup.company;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.global.jwt.utils.JwtUtil;
import com.project.finalproject.login.dto.CompanySignupReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanySignupService {

    private final CompanySignupRepository companySignupRepository;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public String signUp(CompanySignupReqDTO reqDTO){
        if(companySignupRepository.existsCompanyByEmail(reqDTO.getEmail())){
            return reqDTO.getEmail() + "는 이미 존재하는 아이디 입니다.";
        }

        reqDTO.setPassword(passwordEncoding(reqDTO.getPassword()));

        Company company = reqDTO.toEntity();

        companySignupRepository.save(company);

        return "회원가입이 완료되었습니다.";
    }

    /**
     * 비밀번호 인코딩
     */
    private String passwordEncoding(String password){
        return passwordEncoder.encode(password);
    }

}
