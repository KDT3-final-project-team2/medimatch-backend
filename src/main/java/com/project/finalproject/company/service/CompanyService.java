package com.project.finalproject.company.service;

import com.project.finalproject.company.dto.CompanyJobpostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {

    //채용공고 목록 조회
    List<CompanyJobpostResponse.ShortDTO> showJobpostList(String companyEmail);

    //채용공고 단건 조회
    CompanyJobpostResponse.LongDTO showJobpostDetail(String companyEmail, Long postId);
}
