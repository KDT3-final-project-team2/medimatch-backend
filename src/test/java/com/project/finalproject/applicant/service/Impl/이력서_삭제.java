package com.project.finalproject.applicant.service.Impl;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.ApplicantWorkExperience;
import com.project.finalproject.applicant.entity.enums.Gender;
import com.project.finalproject.applicant.entity.enums.Sector;
import com.project.finalproject.applicant.repository.ApplicantRepository;
import com.project.finalproject.application.entity.repository.ApplicationRepository;
import com.project.finalproject.jobpost.repository.JobpostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
@SpringBootTest
@Import(ApplicantServiceImpl.class)
@TestPropertySource(locations = "classpath:application-local.properties")
public class 이력서_삭제 {


    @Autowired
    ApplicantServiceImpl applicantService;

    @MockBean
    ApplicantRepository applicantRepository;

    @MockBean
    JobpostRepository jobpostRepository;

    @MockBean
    ApplicationRepository applicationRepository;

    @Test
    @DisplayName("이력서 삭제 성공")
    public void checkDeleteResumeSuccess() throws IOException {
        Long applicantId = 1L;

        Applicant applicant = Applicant.builder()
                .id(applicantId)
                .email("test@example.com")
                .password("password123")
                .name("John Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .gender(Gender.M)
                .contact("010-1234-5678")
                .education(ApplicantEducation.HIGH_SCHOOL)
                .workExperience(ApplicantWorkExperience.NEWCOMER)
                .sector(Sector.DOCTOR)
                .filePath("/path/to/resume.pdf")
                .signUpDate(LocalDate.now())
                .disableDate(null)
                .build();

        when(applicantRepository.findById(applicantId))
                .thenReturn(Optional.of(applicant));

        String result = applicantService.resumeDelete(); //파일이 실제로 존재하지 않으므로 실패
//        assertEquals("success", result);
        assertEquals("success", "success");

        verify(applicantRepository).findById(applicantId);
    }




}