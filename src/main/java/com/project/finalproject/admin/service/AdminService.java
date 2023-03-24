package com.project.finalproject.admin.service;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.company.entity.Company;

import java.util.List;

public interface AdminService {

    public List<Applicant> getAllApplicants();
    public List<Company> getAllCompanies();


}
