package com.project.finalproject.applicant.controller;

import com.project.finalproject.applicant.dto.request.ApplicantInfoUpdateRequest;
import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.service.ApplicantInfoUpdateService;
import com.project.finalproject.applicant.service.impl.ApplicantInfoUpdateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applicant")
public class ApplicantController {

    private final ApplicantInfoUpdateServiceImpl applicantInfoUpdateServiceImpl;

    @PutMapping("/me")
    public String updateApplicantInfo(ApplicantInfoUpdateRequest applicantInfoUpdateRequest){
        applicantInfoUpdateServiceImpl.updateApplicantInfo(applicantInfoUpdateRequest.toEntity());
        return "success";
    }

}
