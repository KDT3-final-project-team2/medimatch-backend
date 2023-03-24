package com.project.finalproject.admin.service;

import com.project.finalproject.admin.repository.AdminRepository;
import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.company.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    public List<Applicant> getAllApplicants(){
        return adminRepository.findAllApplicant();
    }
    public List<Company> getAllCompanies(){
        return adminRepository.findAllCompany();
    }
}
