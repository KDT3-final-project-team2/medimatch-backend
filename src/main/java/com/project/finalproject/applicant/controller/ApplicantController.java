package com.project.finalproject.applicant.controller;

import com.project.finalproject.applicant.dto.request.ApplicantInfoUpdateRequest;
import com.project.finalproject.applicant.service.impl.ApplicantServiceImpl;
import com.project.finalproject.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applicant")
public class ApplicantController {

    private final ApplicantServiceImpl applicantInfoUpdateServiceImpl;


    @GetMapping("/info")
    public ResponseDTO getApplicantInfo(){
        Long applicantId = 1L; //Todo
        return new ResponseDTO(200,true,applicantInfoUpdateServiceImpl.getApplicantInfo(applicantId),"success");
    }

    @PutMapping("/me")
    public String updateApplicantInfo(ApplicantInfoUpdateRequest applicantInfoUpdateRequest){
        applicantInfoUpdateServiceImpl.updateApplicantInfo(applicantInfoUpdateRequest.toEntity());
        return "success";
    }

}
