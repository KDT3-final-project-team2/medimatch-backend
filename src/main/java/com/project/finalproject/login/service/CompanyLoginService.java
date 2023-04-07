package com.project.finalproject.login.service;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.entity.enums.CompanyType;
import com.project.finalproject.global.dto.ResponseDTO;
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
    private final JwtProperties jwtProperties;
    private final PasswordEncoder passwordEncoder;

    /**
     * 기업회원 로그인
     */
    @Transactional(readOnly = true)
    public ResponseDTO companyLogin(LoginReqDTO req){

        if(!companyLoginRepository.existsCompanyByEmail(req.getEmail())){
            return new ResponseDTO(401, false, "INVALID_ID", "잘못된 ID입니다.");
        }

        Company findCompany = companyLoginRepository.findCompanyByEmail(req.getEmail()).orElseThrow();


        if(!checkPassword(req.getPassword(), findCompany.getPassword())){
            return new ResponseDTO(401, false, "INVALID_PASSWORD", "잘못된 비밀번호입니다.");
        }

        String accessToken = jwtUtil.createAccessToken(findCompany.getEmail(), jwtProperties.getSecretKey(), "COMPANY", findCompany.getId());
        String refreshToken = jwtUtil.createRefreshToken(findCompany.getEmail(), jwtProperties.getSecretKey(), "COMPANY", findCompany.getId());

        return new ResponseDTO(200, true, LoginResDTO.builder()
                .email(findCompany.getEmail())
                .role("COMPANY")
                .name(findCompany.getName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build(), "로그인 성공!");
    }

    /**
     * 관리자 로그인
     */
    @Transactional(readOnly = true)
    public ResponseDTO adminLogin(LoginReqDTO req){

        if(!companyLoginRepository.existsCompanyByEmail(req.getEmail())){
            return new ResponseDTO(401, false, "INVALID_ID", "잘못된 ID입니다.");
        }

        Company findCompany = companyLoginRepository.findCompanyByEmail(req.getEmail()).orElseThrow();
        if (findCompany.getCompanyType() != CompanyType.ADMIN) {
            return new ResponseDTO(401, false, "UNAUTHORIZED", "접근이 불가한 계정입니다.");
        }


        if(!checkPassword(req.getPassword(), findCompany.getPassword())){
            return new ResponseDTO(401, false, "INVALID_PASSWORD", "잘못된 비밀번호입니다.");
        }

        String accessToken = jwtUtil.createAccessToken(findCompany.getEmail(), jwtProperties.getSecretKey(), "ADMIN", findCompany.getId());
        String refreshToken = jwtUtil.createRefreshToken(findCompany.getEmail(), jwtProperties.getSecretKey(), "ADMIN", findCompany.getId());

        return new ResponseDTO(200, true, LoginResDTO.builder()
                .email(findCompany.getEmail())
                .role("ADMIN")
                .name(findCompany.getName())
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
