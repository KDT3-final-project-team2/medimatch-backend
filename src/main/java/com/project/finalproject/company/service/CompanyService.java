package com.project.finalproject.company.service;

import com.project.finalproject.company.dto.ApplicationsForCompanyResponseDTO;
import com.project.finalproject.company.dto.CompanyJobpostRequest;
import com.project.finalproject.company.dto.CompanyJobpostResponse;
import com.project.finalproject.jobpost.entity.Jobpost;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface CompanyService {

    //채용공고 생성
    Jobpost createJobpost(String email, CompanyJobpostRequest.CreateDTO createRequestDTO, MultipartFile jobpostFile) throws IOException;

    //채용공고 목록 조회
    List<CompanyJobpostResponse.ShortDTO> showJobpostList(String companyEmail);

    //채용공고 단건 조회
    CompanyJobpostResponse.LongDTO showJobpostDetail(String companyEmail, Long postId);

    public ApplicationsForCompanyResponseDTO statisticsForApplicationsForCompany(Long companyId);

    //채용공고 수정
    Long updateJobpost(String email, CompanyJobpostRequest.UpdateDTO updateRequestDTO);
}
