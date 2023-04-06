package com.project.finalproject.resign.service;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.resign.dto.ResignDTO;
import com.project.finalproject.resign.repository.ApplicantResignRepository;
import com.project.finalproject.resign.repository.ApplicationResigngRepository;
import com.project.finalproject.resign.repository.CompanyResignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class ResignService {
    private final ApplicantResignRepository applicantResignRepository;
    private final CompanyResignRepository companyResignRepository;
    private final ApplicationResigngRepository applicationResigngRepository;

    /**
     * 회원탈퇴(탈퇴 날짜를 저장하고, 이메일만 지운다.)
     */
    public ResponseDTO resign(ResignDTO resignDTO){
        if(resignDTO.getRole().equals("USER")){
            Applicant findApplicant = applicantResignRepository.findApplicantById(resignDTO.getId()).orElseThrow();
            applicationResigngRepository.deleteApplicationByApplicant(findApplicant); //지원서를 먼저 지우고
            findApplicant.applicantResign(null,LocalDate.now());
            applicantResignRepository.save(findApplicant); //email 삭제 및 탈퇴 날짜 입력
            return new ResponseDTO(200, true, null, "회원탈퇴가 정상적으로 처리되었습니다.");
        }
        else if(resignDTO.getRole().equals("COMPANY")){
            Company findCompany = companyResignRepository.findCompanyById(resignDTO.getId()).orElseThrow();
            findCompany.companyResign(null,LocalDate.now());
            companyResignRepository.save(findCompany);
            return new ResponseDTO(200, true, null, "회원탈퇴가 정상적으로 처리되었습니다.");
        }
        else{
            return new ResponseDTO(400, false, null,"관리자는 탈퇴할 수 없습니다.");
        }

    }
}
