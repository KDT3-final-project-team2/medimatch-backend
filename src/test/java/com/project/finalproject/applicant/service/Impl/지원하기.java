//package com.project.finalproject.applicant.service.Impl;
//
//import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
//import com.project.finalproject.applicant.entity.Applicant;
//import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
//import com.project.finalproject.applicant.entity.enums.ApplicantWorkExperience;
//import com.project.finalproject.applicant.entity.enums.Gender;
//import com.project.finalproject.applicant.entity.enums.Sector;
//import com.project.finalproject.applicant.repository.ApplicantRepository;
//import com.project.finalproject.application.entity.Application;
//import com.project.finalproject.application.repository.ApplicationRepository;
//import com.project.finalproject.company.entity.Company;
//import com.project.finalproject.jobpost.entity.Jobpost;
//import com.project.finalproject.jobpost.entity.enums.JobpostEducation;
//import com.project.finalproject.jobpost.entity.enums.JobpostStatus;
//import com.project.finalproject.jobpost.entity.enums.JobpostWorkExperience;
//import com.project.finalproject.jobpost.repository.JobpostRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.TestPropertySource;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.StandardCopyOption;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.nio.file.Path;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//
//@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
//@SpringBootTest
//@Import(ApplicantServiceImpl.class)
////@TestPropertySource(locations = "classpath:application-local.properties")
//@TestPropertySource(locations = "classpath:application-test.properties")
//public class 지원하기 {
//
//
//    @Autowired
//    ApplicantServiceImpl applicantService;
//
//    @MockBean
//    ApplicantRepository applicantRepository;
//
//    @MockBean
//    JobpostRepository jobpostRepository;
//
//    @MockBean
//    ApplicationRepository applicationRepository;
//
//
//    @Test
//    @DisplayName("채용공고 지원 성공")
//    public void checkApplyJobpostSuccess() throws IOException {
//        Path sourcePath = Files.createTempFile("resume", ".pdf");
//        Files.write(sourcePath, "Test resume content".getBytes());
//
//        Long jobpostId = 1L;
//        Long applicantId = 1L;
//
//        Applicant applicant = Applicant.builder()
//                .id(1L)
//                .email("test@example.com")
//                .password("password123")
//                .name("John Doe")
//                .birthDate(LocalDate.of(1990, 1, 1))
//                .gender(Gender.M)
//                .contact("010-1234-5678")
//                .education(ApplicantEducation.HIGH_SCHOOL)
//                .workExperience(ApplicantWorkExperience.NEWCOMER)
//                .sector(Sector.DOCTOR)
//                .filePath(sourcePath.toString())
//                .signUpDate(LocalDate.now())
//                .disableDate(null)
//                .build();
//
//        Company company = Company.builder()
//                .id(1L)
//                .name("Test Company")
//                .build();
//
//        Jobpost jobpost = Jobpost.builder()
//                .id(1L)
//                .title("Test Job")
//                .education(JobpostEducation.UNIVERSITY)
//                .experience(JobpostWorkExperience.NEWCOMER)
//                .startDate(LocalDateTime.now())
//                .dueDate(LocalDateTime.now().plusDays(7))
//                .createDate(LocalDateTime.now())
//                .editDate(LocalDateTime.now())
//                .status(JobpostStatus.OPEN)
//                .recruitNum(1)
//                .filepath("/path/to/jobpost.pdf")
//                .sector(Sector.DOCTOR)
//                .company(company)
//                .build();
//
//        when(jobpostRepository.findById(jobpostId))
//                .thenReturn(Optional.of(jobpost));
//        when(applicationRepository.findByApplicantIdAndJobpostId(applicantId, jobpostId))
//                .thenReturn(Optional.empty());
//        when(applicantRepository.findById(applicantId))
//                .thenReturn(Optional.of(applicant));
//
//        String result = applicantService.applyJobpost(jobpostId,applicantId);
//
//        assertEquals("success", result);
//
//        verify(jobpostRepository).findById(jobpostId);
//        verify(applicationRepository).findByApplicantIdAndJobpostId(applicantId, jobpostId);
//        verify(applicantRepository).findById(applicantId);
//    }
//
//    @Test
//    @DisplayName("채용공고 지원 실패 : 채용공고 마감")
//    public void checkApplyJobpostFail1() throws IOException {
//        Long jobpostId = 1L;
//        Long applicantId = 1L;
//
//        Company company = Company.builder()
//                .id(1L)
//                .name("Test Company")
//                .build();
//
//        Jobpost jobpost = Jobpost.builder()
//                .id(1L)
//                .title("Test Job")
//                .education(JobpostEducation.UNIVERSITY)
//                .experience(JobpostWorkExperience.NEWCOMER)
//                .startDate(LocalDateTime.now())
//                .dueDate(LocalDateTime.now().minusDays(30)) //채용공고 30일전
//                .createDate(LocalDateTime.now())
//                .editDate(LocalDateTime.now())
//                .status(JobpostStatus.OPEN)
//                .recruitNum(1)
//                .filepath("/path/to/jobpost.pdf")
//                .sector(Sector.DOCTOR)
//                .company(company)
//                .build();
//
//
//        when(jobpostRepository.findById(jobpostId))
//                .thenReturn(Optional.of(jobpost));
//
//
//        String result = applicantService.applyJobpost(jobpostId, applicantId);
//
//        assertEquals("due date passed", result);
//
//        verify(jobpostRepository).findById(jobpostId);
//    }
//
//    @Test
//    @DisplayName("채용공고 지원 실패 : 이미 지원한 채용공고")
//    public void checkApplyJobpostFail2() throws IOException {
//        Path sourcePath = Files.createTempFile("resume", ".pdf");
//        Files.write(sourcePath, "Test resume content".getBytes());
//
//        Long jobpostId = 1L;
//        Long applicantId = 1L;
//
//        Applicant applicant = Applicant.builder()
//                .id(1L)
//                .email("test@example.com")
//                .password("password123")
//                .name("John Doe")
//                .birthDate(LocalDate.of(1990, 1, 1))
//                .gender(Gender.M)
//                .contact("010-1234-5678")
//                .education(ApplicantEducation.HIGH_SCHOOL)
//                .workExperience(ApplicantWorkExperience.NEWCOMER)
//                .sector(Sector.DOCTOR)
//                .filePath(sourcePath.toString())
//                .signUpDate(LocalDate.now())
//                .disableDate(null)
//                .build();
//
//        Company company = Company.builder()
//                .id(1L)
//                .name("Test Company")
//                .build();
//
//        Jobpost jobpost = Jobpost.builder()
//                .id(1L)
//                .title("Test Job")
//                .education(JobpostEducation.UNIVERSITY)
//                .experience(JobpostWorkExperience.NEWCOMER)
//                .startDate(LocalDateTime.now())
//                .dueDate(LocalDateTime.now().plusDays(7))
//                .createDate(LocalDateTime.now())
//                .editDate(LocalDateTime.now())
//                .status(JobpostStatus.OPEN)
//                .recruitNum(1)
//                .filepath("/path/to/jobpost.pdf")
//                .sector(Sector.DOCTOR)
//                .company(company)
//                .build();
//
//        when(jobpostRepository.findById(jobpostId))
//                .thenReturn(Optional.of(jobpost));
//        when(applicationRepository.findByApplicantIdAndJobpostId(applicantId, jobpostId))
//                .thenReturn(Optional.of(new Application()));
//        when(applicantRepository.findById(applicantId))
//                .thenReturn(Optional.of(applicant));
//
//        String result = applicantService.applyJobpost(jobpostId, applicantId);
//
//        assertEquals("applied already", result);
//
//        verify(jobpostRepository).findById(jobpostId);
//        verify(applicationRepository).findByApplicantIdAndJobpostId(applicantId, jobpostId);
//    }
//
//    @Test
//    @DisplayName("채용공고 지원 실패 : 등록된 이력서 없음.")
//    public void checkApplyJobpostFail3() throws IOException {
//
//        Long jobpostId = 1L;
//        Long applicantId = 1L;
//
//        Applicant applicant = Applicant.builder()
//                .id(1L)
//                .email("test@example.com")
//                .password("password123")
//                .name("John Doe")
//                .birthDate(LocalDate.of(1990, 1, 1))
//                .gender(Gender.M)
//                .contact("010-1234-5678")
//                .education(ApplicantEducation.HIGH_SCHOOL)
//                .workExperience(ApplicantWorkExperience.NEWCOMER)
//                .sector(Sector.DOCTOR)
//                .filePath(null)
//                .signUpDate(LocalDate.now())
//                .disableDate(null)
//                .build();
//
//        Company company = Company.builder()
//                .id(1L)
//                .name("Test Company")
//                .build();
//
//        Jobpost jobpost = Jobpost.builder()
//                .id(1L)
//                .title("Test Job")
//                .education(JobpostEducation.UNIVERSITY)
//                .experience(JobpostWorkExperience.NEWCOMER)
//                .startDate(LocalDateTime.now())
//                .dueDate(LocalDateTime.now().plusDays(7))
//                .createDate(LocalDateTime.now())
//                .editDate(LocalDateTime.now())
//                .status(JobpostStatus.OPEN)
//                .recruitNum(1)
//                .filepath("/path/to/jobpost.pdf")
//                .sector(Sector.DOCTOR)
//                .company(company)
//                .build();
//
//        when(jobpostRepository.findById(jobpostId))
//                .thenReturn(Optional.of(jobpost));
//        when(applicationRepository.findByApplicantIdAndJobpostId(applicantId, jobpostId))
//                .thenReturn(Optional.empty());
//        when(applicantRepository.findById(applicantId))
//                .thenReturn(Optional.of(applicant));
//
//        String result = applicantService.applyJobpost(jobpostId, applicantId);
//
//        assertEquals("no resume", result);
//
//        verify(jobpostRepository).findById(jobpostId);
//        verify(applicationRepository).findByApplicantIdAndJobpostId(applicantId, jobpostId);
//        verify(applicantRepository).findById(applicantId);
//    }
//}