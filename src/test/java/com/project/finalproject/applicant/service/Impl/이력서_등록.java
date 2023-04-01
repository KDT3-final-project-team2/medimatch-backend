package com.project.finalproject.applicant.service.Impl;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.ApplicantWorkExperience;
import com.project.finalproject.applicant.entity.enums.Gender;
import com.project.finalproject.applicant.entity.enums.Sector;
import com.project.finalproject.applicant.repository.ApplicantRepository;
import com.project.finalproject.application.entity.Application;
import com.project.finalproject.application.entity.repository.ApplicationRepository;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.jobpost.entity.Jobpost;
import com.project.finalproject.jobpost.entity.enums.JobpostEducation;
import com.project.finalproject.jobpost.entity.enums.JobpostStatus;
import com.project.finalproject.jobpost.entity.enums.JobpostWorkExperience;
import com.project.finalproject.jobpost.repository.JobpostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
@SpringBootTest
@Import(ApplicantServiceImpl.class)
@TestPropertySource(locations = "classpath:application-local.properties")
public class 이력서_등록 {


    @Autowired
    ApplicantServiceImpl applicantService;

    @MockBean
    ApplicantRepository applicantRepository;

    @MockBean
    JobpostRepository jobpostRepository;

    @MockBean
    ApplicationRepository applicationRepository;


    @Test
    @DisplayName("이력서 등록 성공")
    public void checkResumeSaveSuccess() throws IOException {
        Long applicantId = 1L;

        byte[] content = {0x12, 0x34, 0x56, (byte)0x78}; // 랜덤 내용
        MultipartFile resume = new MockMultipartFile("test-resume.pdf", content);


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
                .filePath(null)
                .signUpDate(LocalDate.now())
                .disableDate(null)
                .build();

        when(applicantRepository.findById(applicantId))
                .thenReturn(Optional.of(applicant));

        String result = applicantService.resumeSave(resume);

        assertEquals("success", result);

        verify(applicantRepository).findById(applicantId);
    }

    @Test
    @DisplayName("이력서 등록 실패 : 빈 파일")
    public void checkResumeSaveFail() throws IOException {
        Long applicantId = 1L;

        MultipartFile resume = new MockMultipartFile("resume.pdf", new byte[]{});

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
                .filePath(null)
                .signUpDate(LocalDate.now())
                .disableDate(null)
                .build();

        when(applicantRepository.findById(applicantId)).thenReturn(Optional.of(applicant));

        String result = applicantService.resumeSave(resume);

        assertEquals("empty file", result);
    }
}