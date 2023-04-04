package com.project.finalproject.applicant.service.Impl;

import com.project.finalproject.applicant.dto.request.InfoUpdateRequestDTO;
import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.applicant.dto.response.MyInfoResponseDTO;
import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.ApplicantWorkExperience;
import com.project.finalproject.applicant.entity.enums.Gender;
import com.project.finalproject.applicant.entity.enums.Sector;
import com.project.finalproject.applicant.repository.ApplicantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
@SpringBootTest
@Import(ApplicantServiceImpl.class)
//@TestPropertySource(locations = "classpath:application-local.properties")
@TestPropertySource(locations = "classpath:application.properties")
public class 내_정보 {


    @Autowired
    ApplicantServiceImpl applicantService;

    @MockBean
    ApplicantRepository applicantRepository;


    @Test
    @DisplayName("내 정보 불러오기 성공")
    @WithMockUser
    public void checkSignupSuccess() {
        Long applicantId = 1L;

        Applicant applicant = Applicant.builder()
                .id(1L)
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

        MyInfoResponseDTO myInfoResponseDTO = applicantService.myInfo(applicantId);

        assertNotNull(myInfoResponseDTO);

        verify(applicantRepository).findById(applicantId);
    }

}