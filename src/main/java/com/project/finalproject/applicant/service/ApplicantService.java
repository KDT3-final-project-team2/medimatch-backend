package com.project.finalproject.applicant.service;

import com.project.finalproject.applicant.entity.Applicant;

public interface ApplicantService {

    public Applicant getApplicantInfo(Long id);

    public String updateApplicantInfo(Applicant applicant);
}
