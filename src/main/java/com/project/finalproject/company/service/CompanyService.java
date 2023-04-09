package com.project.finalproject.company.service;

import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.company.dto.*;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.signup.dto.CompanySignupReqDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Service
public interface CompanyService {

    public String checkEmail(CompanySignupReqDTO companySignupReqDTO);

    //채용공고 생성
    CompanyJobpostResponse.LongDTO createJobpost(String email, CompanyJobpostRequest.CreateDTO createRequestDTO, MultipartFile jobpostFile) throws IOException;

    //채용공고 목록 조회
    List<CompanyJobpostResponse.ShortDTO> showJobpostList(String companyEmail);

    //채용공고 단건 조회
    CompanyJobpostResponse.LongDTO showJobpostDetail(String companyEmail, Long postId);

    //채용공고 수정
    CompanyJobpostResponse.LongDTO updateJobpost(String email, Long postId, CompanyJobpostRequest.UpdateDTO updateRequestDTO, MultipartFile jobpostFile) throws IOException;

    //채용공고 삭제
    CompanyJobpostResponse.LongDTO deleteJobpost(String email, Long jobpostId);

    //지원자 통계 출력
    public CompanyApplicationResponse.StatisticsDTO statisticsForApplicationsForCompany(Long companyId);

    //지원자 정보 출력
    List<CompanyApplicationResponse.ApplicantInfoDTO> showApplicantInfo(String companyEmail);

    //지원자 상태 변경
    void changeApplicationStatus(String companyEmail, CompanyApplicationRequest.StatusReqDTO applicationStatus);

    //기업회원 내정보 출력
    CompanyResponse.InfoDTO showCompanyInfo(String companyEmail);

    //기업회원 내정보 수정
    CompanyResponse.InfoDTO updateCompanyInfo(String companyEmail, CompanyRequest.UpdateInfoDTO requestDTO);

    //기업이 지원자에게 합/불 메일 발송
    ResponseDTO sendEmail(EmailReqDTO emailReqDTO) throws MessagingException;
}
