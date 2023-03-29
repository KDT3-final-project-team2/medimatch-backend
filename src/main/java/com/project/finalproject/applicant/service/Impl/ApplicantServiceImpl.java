package com.project.finalproject.applicant.service.Impl;

import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.applicant.repository.ApplicantRepository;
import com.project.finalproject.applicant.service.ApplicantService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;

    @Override
    public String signup(SignupRequestDTO signupRequestDTO){
        if(applicantRepository.findByEmail(signupRequestDTO.getApplicantEmail()).isPresent()){
            return "duplicate ID";
        }
        else{
            //TODO: 개인회원 회원가입시 비밀번호 암호화 추가
            applicantRepository.save(signupRequestDTO.toEntity());
            return "success";
        }
    }
}
