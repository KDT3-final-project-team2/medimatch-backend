package com.project.finalproject.applicant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.ApplicantWorkExperience;
import com.project.finalproject.applicant.entity.enums.Gender;
import com.project.finalproject.applicant.entity.enums.Sector;
import com.project.finalproject.applicant.repository.ApplicantRepository;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
@WebMvcTest(ApplicantController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class 회원가입 {

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
    @DisplayName("회원가입 성공")
    @WithMockUser
    public void signupSuccess() throws Exception {

        given(applicantService.signup(any(SignupRequestDTO.class)))
                .willReturn("success"); // given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드.

        MvcResult mvcResult = mockMvc.perform(post("/applicant/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignupRequestDTO())))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);
        assertEquals(200, responseDTO.getStateCode());
        assertEquals(true, responseDTO.isSuccess());
        assertEquals("success", responseDTO.getData());

        verify(applicantService).signup(any(SignupRequestDTO.class));
    }

    @Test
    @DisplayName("회원가입 실패 : 중복된 이메일")
    @WithMockUser
    public void signupFail() throws Exception {

        given(applicantService.signup(any(SignupRequestDTO.class)))
                .willReturn("duplicate id"); // given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드.

        MvcResult mvcResult = mockMvc.perform(post("/applicant/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignupRequestDTO())))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);
        assertEquals(401, responseDTO.getStateCode());
        assertEquals(false, responseDTO.isSuccess());
        assertEquals("duplicate id", responseDTO.getData());

        verify(applicantService).signup(any(SignupRequestDTO.class));
    }
}








































































