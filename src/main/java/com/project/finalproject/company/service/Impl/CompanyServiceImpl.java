package com.project.finalproject.company.service.Impl;


import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.repository.CompanyRepository;
import com.project.finalproject.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public Company getCompanyInfo(Long id) {
        return companyRepository.getCompanyInfo(id);
    }
}
