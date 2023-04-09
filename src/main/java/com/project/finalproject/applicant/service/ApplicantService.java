package com.project.finalproject.applicant.service;

import com.project.finalproject.applicant.dto.request.InfoUpdateRequestDTO;
import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.applicant.dto.response.AppliedJobpostResponseDTO;
import com.project.finalproject.applicant.dto.response.MyInfoResponseDTO;
import com.project.finalproject.applicant.dto.response.ChatMessageResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


public interface ApplicantService {

    public String checkEmail(SignupRequestDTO signupRequestDTO);

    public String signup(SignupRequestDTO signupRequestDTO);

    public MyInfoResponseDTO myInfo(Long applicantID);

    public String infoUpdate(InfoUpdateRequestDTO infoUpdateRequestDTO, Long applicantId);

    public List<AppliedJobpostResponseDTO> appliedJobposts(Long applicantId);

    public String applyJobpost(Long jobpostId, Long applicantId) throws IOException;

    public String cancelApplyJobpost(Long jobpostId, Long applicantId) throws IOException;


    public String resumeSave(MultipartFile files, Long applicantId) throws IOException;

    public String resumePath(Long applicantId);
    public ResponseEntity<Resource> resumeDownload(Long applicantId) throws IOException;

    public String resumeDelete(Long applicantId) throws IOException;

    public ChatMessageResponseDTO doIntentAction(String message, Long applicantId) throws Exception;

}
