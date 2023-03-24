package com.project.finalproject.company.service.Impl;


import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyService companyService;

    public Company getCompanyInfo(Long id) {
        return companyService.getCompanyInfo(id);
    }
}
