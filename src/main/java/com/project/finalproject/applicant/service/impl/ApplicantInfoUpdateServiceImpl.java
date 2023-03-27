package com.project.finalproject.applicant.service.impl;


import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.repository.ApplicantInfoUpdateRepository;
import com.project.finalproject.applicant.service.ApplicantInfoUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicantInfoUpdateServiceImpl implements ApplicantInfoUpdateService {

    private final ApplicantInfoUpdateRepository applicantInfoUpdateRepository;


    public Applicant getApplicantInfo(Long id) {
        return applicantInfoUpdateRepository.getApplicantInfo(id);
    }

    public String updateApplicantInfo(Applicant applicant) {
        return applicantInfoUpdateRepository.updateApplicantInfo(applicant);
    }
}
