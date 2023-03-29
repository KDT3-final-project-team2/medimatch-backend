package com.project.finalproject.applicant.service.Impl;

import com.project.finalproject.applicant.dto.request.InfoUpdateRequestDTO;
import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.repository.ApplicantRepository;
import com.project.finalproject.applicant.service.ApplicantService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;


@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;

    private final String resumeDirectory = "c:/Users/user/"; //이력서 저장 경로


    @Override
    public String signup(SignupRequestDTO signupRequestDTO){
        if(applicantRepository.findByEmail(signupRequestDTO.getApplicantEmail()).isPresent()){
            return "duplicate ID";
        }
        else{
            //TODO: 개인회원 회원가입시 비밀번호 암호화 추가
            applicantRepository.save(signupRequestDTO.toEntity());
            return "success";
        }
    }

    public String checkEmail(String applicantEmail){
        if(applicantRepository.findByEmail(applicantEmail).isPresent()){
            return "duplicate ID";
        }
        else{
            return "success";
        }
    }

    public String infoUpdate(InfoUpdateRequestDTO infoUpdateRequestDTO){
        Long applicantId = 1L;
        Applicant applicant = applicantRepository.findById(applicantId).get();
        applicant.setPassword(infoUpdateRequestDTO.getApplicantPassword());
        applicant.setName(infoUpdateRequestDTO.getApplicantName());
        applicant.setContact(infoUpdateRequestDTO.getApplicantContact());
        applicant.setEducation(infoUpdateRequestDTO.getApplicantEducation());
        applicant.setWorkExperience(infoUpdateRequestDTO.getApplicantWorkExperience());
        applicant.setSector(infoUpdateRequestDTO.getApplicantSector());
        System.out.println(applicant);
        applicantRepository.save(applicant);
        return "success";
    }

    @Override
    public String resumeSave(MultipartFile resume) throws IOException { //이력서 등록, 덮어쓰기
        Long applicantId = 1L; //TODO 개인회원Id 받기
        if (resume.isEmpty()){
            return "empty file";
        }
        String savePath = resumeDirectory + applicantId + ".pdf"; //이력서 저장 경로. 파일명은 개인회원Id.pdf
        resume.transferTo(new File(savePath));
        System.out.println(savePath);
        // 개인회원 filePath 컬럼에 이력서 경로저장
        Applicant applicant = applicantRepository.findById(applicantId).get();
        applicant.setFilePath(savePath);
        applicantRepository.save(applicant);
        return "success";
    }

    @Override
    public ResponseEntity<Resource> resumeDownload() throws IOException{ //이력서 다운로드
        Long applicantId = 1L; //TODO 개인회원Id 받기

        // PDF파일을 가져온다.
        Applicant applicant = applicantRepository.findById(applicantId).orElse(null);
        UrlResource resource = new UrlResource("file:" + applicant.getFilePath());
        String contentDisposition = "attachment; filename=\"" + applicant.getName() + ".pdf\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @Override
    public String resumeDelete() { //이력서 삭제
        Long applicantId = 1L; //개인회원Id 받기

        // 이력서 경로 가져오기
        Applicant applicant = applicantRepository.findById(applicantId).orElse(null);
        String filePath = applicant.getFilePath();
        if (filePath == null) {
            return "file not found";
        }
        // 개인회원 filePath 컬럼에 이력서 경로 null 저장
        applicant.setFilePath(null);
        applicantRepository.save(applicant);

        // 이력서 경로에 해당하는 파일 삭제
        File resume = new File(filePath);
        if (!resume.exists()) { // 이력서가 존재하지 않으면 "File Not Found
            return "file not found";
        }
        if (resume.delete()) { // 이력서 삭제 성공
            return "success";
        } else {
            return "failed";
        }
    }
}
