package com.project.finalproject.applicant.service.Impl;

import com.project.finalproject.applicant.dto.request.InfoUpdateRequestDTO;
import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.applicant.dto.response.AppliedJobpostResponseDTO;
import com.project.finalproject.applicant.dto.response.MyInfoResponseDTO;
import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.repository.ApplicantRepository;
import com.project.finalproject.applicant.service.ApplicantService;
import com.project.finalproject.application.entity.Application;
import com.project.finalproject.application.repository.ApplicationRepository;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.jobpost.entity.Jobpost;
import com.project.finalproject.jobpost.repository.JobpostRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicationRepository applicationRepository;
    private final JobpostRepository jobpostRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${applicant.resume.file.path.server}")
    private String APPLICANT_RESUME_FILE_PATH_SERVER;

    @Value("${application.resume.file.path.server}")
    private String APPLICATION_RESUME_FILE_PATH_SERVER;


    @Override
    public String checkEmail(SignupRequestDTO signupRequestDTO){
        String applicantEmail = signupRequestDTO.getApplicantEmail();
        if(applicantRepository.findByEmail(applicantEmail).isPresent()){
            return "duplicate id";
        }
        else{
            return "success";
        }
    }

    @Override
    public String signup(SignupRequestDTO signupRequestDTO){
        if(applicantRepository.findByEmail(signupRequestDTO.getApplicantEmail()).isPresent()){

            return "duplicate id";
        }
        else{
            Applicant applicant = signupRequestDTO.toEntity();
            applicant.setPassword(passwordEncoder.encode(signupRequestDTO.getApplicantPassword()));
            applicantRepository.save(applicant);
            return "success";
        }
    }

    @Override
    public MyInfoResponseDTO myInfo(Long applicantID){
        Applicant applicant = applicantRepository.findById(applicantID).get();
        MyInfoResponseDTO myInfoResponseDTO = new MyInfoResponseDTO(applicant);
        return myInfoResponseDTO;
    }

    @Override
    public String infoUpdate(InfoUpdateRequestDTO infoUpdateRequestDTO, Long applicantId){
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

    @Override
    public List<AppliedJobpostResponseDTO> appliedJobposts(Long applicantId){

        ArrayList<AppliedJobpostResponseDTO> appliedJobpostResponseDTOS = new ArrayList<>();
        List<Application> applications = applicationRepository.findByApplicantId(applicantId);
        for (Application application : applications) {
            Jobpost jobpost = application.getJobpost();
            Company company = jobpost.getCompany();
            AppliedJobpostResponseDTO appliedJobpostResponseDTO = new AppliedJobpostResponseDTO(company, jobpost, application);
            appliedJobpostResponseDTOS.add(appliedJobpostResponseDTO);
        }
        return appliedJobpostResponseDTOS;
    }


    @Override
    public String applyJobpost(Long jobpostId, Long applicantId) throws IOException {
        Jobpost jobpost = jobpostRepository.findById(jobpostId).get(); //Jobpost 객체 가져오기

        if (LocalDate.now().isAfter(jobpost.getDueDate().toLocalDate())) { // 채용공고 마감일이 지났을때, 지원 불가
            return "due date passed";
        }
        Application existingApplication = applicationRepository.findByApplicantIdAndJobpostId(applicantId, jobpostId).orElse(null);
        if(existingApplication != null){ //이미 지원한 채용공고일때, 지원 불가
            return "applied already";
        }
        Applicant applicant = applicantRepository.findById(applicantId).get(); //Applicant 객체 가져오기
        if(applicant.getFilePath()==null){ //이력서가 없을때, 지원 불가
            return "no resume";
        }else{ //지원 가능
            //Application DB에 정보 저장
            Application application = new Application(applicant, jobpost, APPLICATION_RESUME_FILE_PATH_SERVER + jobpostId + "-"  + applicantId + ".pdf"); //Application 객체 생성
            applicationRepository.save(application); //Application 객체 저장

            //Applicant의 이력서를 채용공고의 이력서 폴더로 복사
            Path sourcePath = Paths.get(applicant.getFilePath());
            Path targetPath = Paths.get(APPLICATION_RESUME_FILE_PATH_SERVER + jobpostId + "-" + applicantId + ".pdf");
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            return "success";
        }
    }

    @Override
    public String cancelApplyJobpost(Long jobpostId, Long applicantId) throws IOException {
        Application existingApplication = applicationRepository.findByApplicantIdAndJobpostId(applicantId, jobpostId).orElse(null);
        if(existingApplication == null){//개인회원이 지원한 채용공고인지 확인
            return "not applied";
        }else{
            //Application DB에서 정보 삭제
            Application application = applicationRepository.findByApplicantIdAndJobpostId(applicantId, jobpostId).get();
            applicationRepository.delete(application);

            //Applicant의 이력서를 채용공고의 이력서 폴더에서 삭제
            Path path = Paths.get(APPLICATION_RESUME_FILE_PATH_SERVER + jobpostId + "-"  + applicantId + ".pdf");
            if (path.toFile().exists()) {
                Files.delete(path);
            }
            return "success";
        }
    }



    @Override
    public String resumeSave(MultipartFile resume, Long applicantId) throws IOException { //이력서 등록, 덮어쓰기
        if (resume.isEmpty()){
            return "empty file";
        }
        String savePath = APPLICANT_RESUME_FILE_PATH_SERVER + applicantId + ".pdf"; //이력서 저장 경로. 파일명은 개인회원Id.pdf
        resume.transferTo(new File(savePath));


        // 개인회원 filePath 컬럼에 이력서 경로저장
        Applicant applicant = applicantRepository.findById(applicantId).get();
        applicant.setFilePath(savePath);
        applicantRepository.save(applicant);
        return "success";
    }

    @Override
    public String resumePath(Long applicantId){
        Applicant applicant = applicantRepository.findById(applicantId).orElse(null);
        return applicant.getFilePath();
    }
    @Override
    public ResponseEntity<Resource> resumeDownload(Long applicantId) throws IOException{ //이력서 다운로드

        // PDF파일을 가져온다.
        Applicant applicant = applicantRepository.findById(applicantId).orElse(null);
        UrlResource resource = new UrlResource("file:" + applicant.getFilePath());
        String contentDisposition = "attachment; filename=\"" + applicant.getName() + ".pdf\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @Override
    public String resumeDelete(Long applicantId) { //이력서 삭제

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
