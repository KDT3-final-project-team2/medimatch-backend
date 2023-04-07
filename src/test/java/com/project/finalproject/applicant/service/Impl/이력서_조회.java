package com.project.finalproject.applicant.service.Impl;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.ApplicantWorkExperience;
import com.project.finalproject.applicant.entity.enums.Gender;
import com.project.finalproject.applicant.entity.enums.Sector;
import com.project.finalproject.applicant.repository.ApplicantRepository;
import com.project.finalproject.application.repository.ApplicationRepository;
import com.project.finalproject.jobpost.repository.JobpostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.core.io.Resource;


@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
@SpringBootTest
@Import(ApplicantServiceImpl.class)
//@TestPropertySource(locations = "classpath:application-local.properties")
@TestPropertySource(locations = "classpath:application-test.properties")
public class 이력서_조회 {


    @Autowired
    ApplicantServiceImpl applicantService;

    @MockBean
    ApplicantRepository applicantRepository;

    @MockBean
    JobpostRepository jobpostRepository;

    @MockBean
    ApplicationRepository applicationRepository;


    @Test
    @DisplayName("이력서 다운로드 성공")
    public void testResumeDownload() throws IOException {
        Long applicantId = 1L;

        Applicant applicant = Applicant.builder()
                .id(applicantId)
                .name("John Doe")
                .filePath("/path/to/John Doe.pdf")
                .build();

        String expectedFilename = "John Doe.pdf";
        String expectedContentDisposition = "attachment; filename=\"" + Paths.get(applicant.getFilePath()).getFileName().toString() + "\"";


        when(applicantRepository.findById(applicantId)).thenReturn(Optional.of(applicant));

        ResponseEntity<Resource> response = applicantService.resumeDownload(applicantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Resource responseBody = response.getBody();
        assertNotNull(responseBody);

        assertTrue(responseBody instanceof UrlResource);
        UrlResource responseResource = (UrlResource) responseBody;

        assertEquals(expectedContentDisposition, response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertEquals(expectedFilename, responseResource.getFile().getName());
    }


}