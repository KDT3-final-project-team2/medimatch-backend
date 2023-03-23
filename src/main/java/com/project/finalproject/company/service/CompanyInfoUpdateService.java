package com.project.finalproject.company.service;

import com.project.finalproject.company.dto.request.CompanyInfoUpdateRequestDTO;
import com.project.finalproject.company.entity.Company;

public interface CompanyInfoUpdateService {

    public String updateCompanyInfo(CompanyInfoUpdateRequestDTO companyInfoUpdateRequestDTO);
}
