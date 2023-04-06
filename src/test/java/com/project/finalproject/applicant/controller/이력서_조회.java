package com.project.finalproject.applicant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.finalproject.applicant.service.ApplicantService;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.global.jwt.utils.JwtExceptionFilter;
import com.project.finalproject.global.jwt.utils.JwtFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
@WebMvcTest(ApplicantController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class 이력서_조회 {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean // ApplicantController에서 사용하는 ApplicantService를 MockBean으로 등록
    ApplicantService applicantService;

    @MockBean
    JwtFilter jwtFilter;

    @MockBean
    JwtExceptionFilter jwtExceptionFilter;


    @Test
    @DisplayName("이력서 조회 성공")
    @WithMockUser
    public void resumeDownloadSuccess() throws Exception {
        byte[] mockResumeContent = "test resume content".getBytes();
        ByteArrayResource resource = new ByteArrayResource(mockResumeContent);

        given(applicantService.resumeDownload())
                .willReturn(ResponseEntity.ok()
                        .contentLength(mockResumeContent.length)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource));

        MvcResult mvcResult = mockMvc.perform(get("/applicant/resume")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        byte[] responseContent = mvcResult.getResponse().getContentAsByteArray();

        assertArrayEquals(mockResumeContent, responseContent);

        verify(applicantService).resumeDownload();
    }
}








































































