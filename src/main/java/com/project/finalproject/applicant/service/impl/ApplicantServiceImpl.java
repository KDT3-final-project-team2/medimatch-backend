package com.project.finalproject.applicant.service.impl;


import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.repository.ApplicantRepository;
import com.project.finalproject.applicant.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;


    public Applicant getApplicantInfo(Long id) {
        return applicantRepository.getApplicantInfo(id);
    }

    public String updateApplicantInfo(Applicant applicant) {
        return applicantRepository.updateApplicantInfo(applicant);
    }
}
