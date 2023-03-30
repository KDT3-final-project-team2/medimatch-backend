package com.project.finalproject.applicant.service.Impl;

import com.project.finalproject.applicant.dto.request.InfoUpdateRequestDTO;
import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.applicant.dto.response.MyInfoResponseDTO;
import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.repository.ApplicantRepository;
import com.project.finalproject.applicant.service.ApplicantService;
import com.project.finalproject.application.entity.Application;
import com.project.finalproject.application.entity.repository.ApplicationRepository;
import com.project.finalproject.jobpost.entity.Jobpost;
import com.project.finalproject.jobpost.entity.repository.JobpostRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicationRepository applicationRepository;
    private final JobpostRepository jobpostRepository;

    private final PasswordEncoder passwordEncoder;

//    private final String applicantResumeDirectory = "c:/Users/applicant/"; //이력서 저장 경로
//    private final String jobpostResumeDirectory = "c:/Users/jobpost/resume/"; //이력서 저장 경로
    private final String applicantResumeDirectory = "c:/Users/user/test/applicant/"; //이력서 저장 경로
    private final String jobpostResumeDirectory = "c:/Users/user/test/jobpost/resume/"; //이력서 저장 경로


    public void createDirectoryIfNotExist(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Directory is created!");
            System.out.println(directoryPath);
        }else{
            System.out.println("Directory already exists.");
        }

    }


    @Override
    public String signup(SignupRequestDTO signupRequestDTO){
        if(applicantRepository.findByEmail(signupRequestDTO.getApplicantEmail()).isPresent()){
            return "duplicate ID";
        }
        else{
            //TODO: 개인회원 회원가입시 비밀번호 암호화 추가
            Applicant applicant = signupRequestDTO.toEntity();
            applicant.setPassword(passwordEncoder.encode(signupRequestDTO.getApplicantPassword()));
            applicantRepository.save(applicant);
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
        applicant.setPassword(passwordEncoder.encode(infoUpdateRequestDTO.getApplicantPassword()));
        applicant.setName(infoUpdateRequestDTO.getApplicantName());
        applicant.setContact(infoUpdateRequestDTO.getApplicantContact());
        applicant.setEducation(infoUpdateRequestDTO.getApplicantEducation());
        applicant.setWorkExperience(infoUpdateRequestDTO.getApplicantWorkExperience());
        applicant.setSector(infoUpdateRequestDTO.getApplicantSector());

        applicantRepository.save(applicant);
        return "success";
    }

    public MyInfoResponseDTO myInfo(Long applicantID){
        Applicant applicant = applicantRepository.findById(applicantID).get();
        MyInfoResponseDTO myInfoResponseDTO = new MyInfoResponseDTO(applicant);
        return myInfoResponseDTO;
    }


    @Override
    public String resumeSave(MultipartFile resume) throws IOException { //이력서 등록, 덮어쓰기
        Long applicantId = 1L; //TODO 개인회원Id 받기
        if (resume.isEmpty()){
            return "empty file";
        }
        String savePath = applicantResumeDirectory + applicantId + ".pdf"; //이력서 저장 경로. 파일명은 개인회원Id.pdf
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
        Long applicantId = 1L; //TODO 개인회원Id 받기

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

    public String applyJobpost(Long jobpostId) throws IOException {
        Long applicantId = 1L; //TODO 개인회원Id 받기
        Jobpost jobpost = jobpostRepository.findById(jobpostId).get(); //Jobpost 객체 가져오기

        if (LocalTime.now().isAfter(jobpost.getDueDate().toLocalTime())) { // 채용공고 마감일이 지났을때, 지원 불가
            return "due date passed";
        }

        Application existingApplication = applicationRepository.findByApplicantIdAndJobpostId(applicantId, jobpostId).orElse(null);
        if(existingApplication != null){ //이미 지원한 채용공고일때, 지원 불가
            return "applied already";

        }else{ //지원 가능
            //Application DB에 정보 저장
            Applicant applicant = applicantRepository.findById(applicantId).get(); //Applicant 객체 가져오기
            Application application = new Application(applicant, jobpost, jobpostResumeDirectory + jobpostId + "/resume/" + applicantId + ".pdf"); //Application 객체 생성
            applicationRepository.save(application); //Application 객체 저장

            //Applicant의 이력서를 채용공고의 이력서 폴더로 복사
            Path sourcePath = Paths.get(applicant.getFilePath());
            Path targetPath = Paths.get(jobpostResumeDirectory + applicantId + ".pdf");
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            return "success";
        }
    }

    public String cancelApplyJobpost(Long jobpostId){
        return null;
    }
}
