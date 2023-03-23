package com.project.finalproject.company.service;

import com.project.finalproject.company.dto.request.CompanyInfoUpdateRequestDTO;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.repository.CompanyInfoUpdateRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyInfoUpdateServiceImpl implements CompanyInfoUpdateService{

    private final CompanyInfoUpdateRepository CompanyInfoUpdateRepository;
    public String updateCompanyInfo(CompanyInfoUpdateRequestDTO companyInfoUpdateRequestDTO){
        Company companyEntity = companyInfoUpdateRequestDTO.toEntity();
        //companyEntity.setId(토큰에서 추출한 id 또는 email 값); // id값을 추가함
        CompanyInfoUpdateRepository.updateCompanyInfo(companyEntity);
        return "success";
    }
}
