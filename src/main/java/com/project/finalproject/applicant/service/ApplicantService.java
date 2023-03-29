package com.project.finalproject.applicant.service;

import com.project.finalproject.applicant.dto.request.InfoUpdateRequestDTO;
import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


public interface ApplicantService {

    public String signup(SignupRequestDTO signupRequestDTO);

    public String infoUpdate(InfoUpdateRequestDTO infoUpdateRequestDTO);

    public String resumeSave(MultipartFile files) throws IOException;

    public ResponseEntity<Resource> resumeDownload() throws IOException;

    public String resumeDelete() throws IOException;
}
