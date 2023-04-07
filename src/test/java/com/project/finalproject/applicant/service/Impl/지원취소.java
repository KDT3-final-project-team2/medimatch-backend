//package com.project.finalproject.applicant.service.Impl;
//
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
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.TestPropertySource;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//
//@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
//@SpringBootTest
//@Import(ApplicantServiceImpl.class)
////@TestPropertySource(locations = "classpath:application-local.properties")
//@TestPropertySource(locations = "classpath:application-test.properties")
//public class 지원취소 {
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
//        String result = applicantService.applyJobpost(jobpostId, applicantId);
//
//        assertEquals("success", result);
//
//        verify(jobpostRepository).findById(jobpostId);
//        verify(applicationRepository).findByApplicantIdAndJobpostId(applicantId, jobpostId);
//        verify(applicantRepository).findById(applicantId);
//    }
//
//    @Test
//    @DisplayName("채용공고 지원 취소 성공")
//    public void cancelApplyJobpostSuccess() throws IOException {
//        Long jobpostId = 1L;
//        Long applicantId = 1L;
//
//        Application application = Application.builder()
//                .id(1L)
//                .applicant(Applicant.builder().id(applicantId).build())
//                .jobpost(Jobpost.builder().id(jobpostId).build())
//                .build();
//
//        when(applicationRepository.findByApplicantIdAndJobpostId(applicantId, jobpostId))
//                .thenReturn(Optional.of(application));
//
//        String result = applicantService.cancelApplyJobpost(jobpostId,applicantId);
//
//        assertEquals("success", result);
//
//        verify(applicationRepository, times(2)).findByApplicantIdAndJobpostId(applicantId, jobpostId);
//        verifyNoInteractions(jobpostRepository);
//    }
//
//
//    @Test
//    @DisplayName("채용공고 지원 취소 실패 : 지원자가 지원한 채용공고가 없습니다")
//    public void cancelApplyJobpostFail() throws IOException {
//        Long jobpostId = 1L;
//        Long applicantId = 1L;
//
//        when(applicationRepository.findByApplicantIdAndJobpostId(applicantId, jobpostId))
//                .thenReturn(Optional.ofNullable(null));
//
//        String result = applicantService.cancelApplyJobpost(jobpostId,applicantId);
//
//        assertEquals("not applied", result);
//
//        verify(applicationRepository).findByApplicantIdAndJobpostId(applicantId, jobpostId);
//
//    }
//}