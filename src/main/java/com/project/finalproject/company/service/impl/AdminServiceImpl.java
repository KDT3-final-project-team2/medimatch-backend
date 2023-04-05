package com.project.finalproject.company.service.impl;

import com.project.finalproject.company.dto.AdminCompanyRes;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.entity.enums.CompanyType;
import com.project.finalproject.company.exception.CompanyException;
import com.project.finalproject.company.exception.CompanyExceptionType;
import com.project.finalproject.company.repository.CompanyRepository;
import com.project.finalproject.company.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    @Override
    public List<AdminCompanyRes.CompanyListDTO> showCompanyList(CompanyType companyType) {
        return companyRepository.findByCompanyType(companyType).stream()
                .map(AdminCompanyRes.CompanyListDTO::new).collect(Collectors.toList());
    }
}
