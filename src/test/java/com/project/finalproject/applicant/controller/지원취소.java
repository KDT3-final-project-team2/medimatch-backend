package com.project.finalproject.applicant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.finalproject.applicant.dto.request.JobpostIdRequestDTO;
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
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
@WebMvcTest(ApplicantController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class 지원취소 {

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
    @DisplayName("채용공고 지원취소하기 성공")
    @WithMockUser
    public void applySuccess() throws Exception {
        JobpostIdRequestDTO jobpostIdRequestDTO = new JobpostIdRequestDTO();
        jobpostIdRequestDTO.setJobpostId(1234L);

        given(applicantService.cancelApplyJobpost(any(Long.class)))
                .willReturn("success");

        MvcResult mvcResult = mockMvc.perform(delete("/applicant/apply")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobpostIdRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);

        assertEquals(200, responseDTO.getStateCode());
        assertEquals(true, responseDTO.isSuccess());
        assertEquals("success", responseDTO.getData());

        verify(applicantService).cancelApplyJobpost(any(Long.class));
    }

    @Test
    @DisplayName("채용공고 지원취소하기 실패 : 지원하지 않았습니다.")
    @WithMockUser
    public void applyFail() throws Exception {
        JobpostIdRequestDTO jobpostIdRequestDTO = new JobpostIdRequestDTO();
        jobpostIdRequestDTO.setJobpostId(1234L);

        given(applicantService.cancelApplyJobpost(any(Long.class)))
                .willReturn("not applied");

        MvcResult mvcResult = mockMvc.perform(delete("/applicant/apply")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobpostIdRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);

        assertEquals(401, responseDTO.getStateCode());
        assertEquals(false, responseDTO.isSuccess());
        assertEquals("not applied", responseDTO.getData());

        verify(applicantService).cancelApplyJobpost(any(Long.class));
    }

}








































































