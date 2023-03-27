package com.project.finalproject.login.service;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.global.jwt.utils.JwtProperties;
import com.project.finalproject.global.jwt.utils.JwtUtil;
import com.project.finalproject.login.dto.LoginReqDTO;
import com.project.finalproject.login.dto.LoginResDTO;
import com.project.finalproject.login.repository.CompanyLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CompanyLoginService {

    private final CompanyLoginRepository companyLoginRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;

    /**
     * 기업회원 로그인
     */
    @Transactional(readOnly = true)
    public LoginResDTO companyLogin(LoginReqDTO reqDTO) throws Exception{
        Company company = companyLoginRepository.findCompanyByEmail(reqDTO.getEmail()).orElseThrow(Exception::new);

        if(!passwordCheck(reqDTO.getPassword(), company.getPassword())){
            return LoginResDTO.builder()
                    .role("INVALID")
                    .build();
        }

        String accessToken = jwtUtil.createAccessToken(reqDTO.getEmail(), jwtProperties.getSecretKey(), "COMPANY");
        String refreshToken = jwtUtil.createRefreshToken(reqDTO.getEmail(), jwtProperties.getSecretKey(), "COMPANY");

        return LoginResDTO.builder()
                .email(company.getEmail())
                .role("COMPANY")
                .applicant(null)
                .company(company)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * 비밀번호 일치여부 확인
     */
    private boolean passwordCheck(String inputPassword, String storedPassword) {
        return passwordEncoder.matches(inputPassword, storedPassword);
    }

    /**
     * 비밀번호 인코딩
     */
    private String passwordEncoding(String password) {
        return passwordEncoder.encode(password);
    }

}
