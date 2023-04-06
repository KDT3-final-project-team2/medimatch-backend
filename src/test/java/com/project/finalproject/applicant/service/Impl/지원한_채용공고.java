package com.project.finalproject.applicant.service.Impl;

import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.applicant.dto.response.AppliedJobpostResponseDTO;
import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.repository.ApplicantRepository;
import com.project.finalproject.application.entity.Application;
import com.project.finalproject.application.entity.enums.ApplicationStatus;
import com.project.finalproject.application.repository.ApplicationRepository;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.jobpost.entity.Jobpost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
@SpringBootTest
@Import(ApplicantServiceImpl.class)
//@TestPropertySource(locations = "classpath:application-local.properties")
@TestPropertySource(locations = "classpath:application-test.properties")
public class 지원한_채용공고 {


    @Autowired
    ApplicantServiceImpl applicantService;

    @MockBean
    ApplicantRepository applicantRepository;

    @MockBean
    ApplicationRepository applicationRepository;


    @Test
    @DisplayName("지원한 채용공고 불러오기 성공")
    @WithMockUser
    public void checkAppliedJobpostsSuccess() {
        Long applicantId = 1L;

        Company company = Company.builder()
                .id(1L)
                .name("Test Company")
                .build();

        Jobpost jobpost = Jobpost.builder()
                .id(1L)
                .title("Test Job")
                .company(company)
                .build();

        Application application = Application.builder()
                        .id(1L)
                        .status(ApplicationStatus.APPLY)
                        .interviewDate(LocalDateTime.now())
                        .filepath("/path/to/resume.pdf")
                        .memo("Test Memo")
                        .applyDate(LocalDateTime.now())
                        .passDate(LocalDateTime.now())
                        .applicant(Applicant.builder().id(applicantId).build())
                        .jobpost(jobpost)
                        .build();


        when(applicationRepository.findByApplicantId(applicantId))
                .thenReturn(Collections.singletonList(application));

        List<AppliedJobpostResponseDTO> appliedJobpostResponseDTOS = applicantService.appliedJobposts();

        assertNotNull(appliedJobpostResponseDTOS);
        assertFalse(appliedJobpostResponseDTOS.isEmpty());
        assertEquals(1, appliedJobpostResponseDTOS.size());

        AppliedJobpostResponseDTO appliedJobpostResponseDTO = appliedJobpostResponseDTOS.get(0);
        assertEquals(company.getName(), appliedJobpostResponseDTO.getCompanyName());
        assertEquals(jobpost.getTitle(), appliedJobpostResponseDTO.getJobpostTitle());
        assertEquals(application.getId(), appliedJobpostResponseDTO.getApplicationId());

        verify(applicationRepository).findByApplicantId(applicantId);
    }


}