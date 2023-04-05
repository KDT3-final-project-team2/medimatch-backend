package com.project.finalproject.company.service.impl;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.repository.ApplicantRepository;
import com.project.finalproject.company.dto.AdminApplicantRes;
import com.project.finalproject.company.dto.AdminCompanyRes;
import com.project.finalproject.company.entity.enums.CompanyType;
import com.project.finalproject.company.repository.CompanyRepository;
import com.project.finalproject.company.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CompanyRepository companyRepository;
    private final ApplicantRepository applicantRepository;

    @Transactional(readOnly = true)
    @Override
    public List<AdminCompanyRes.CompanyListDTO> showCompanyList(CompanyType companyType) {
        return companyRepository.findByCompanyType(companyType).stream()
                .map(AdminCompanyRes.CompanyListDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AdminApplicantRes.ApplicantListDTO> showApplicantList() {
        List<Applicant> applicants = applicantRepository.findAll();
        return applicants.stream()
                .map(AdminApplicantRes.ApplicantListDTO::new).collect(Collectors.toList());
    }
}
