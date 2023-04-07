//package com.project.finalproject.applicant.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.finalproject.applicant.dto.request.JobpostIdRequestDTO;
//import com.project.finalproject.applicant.service.ApplicantService;
//import com.project.finalproject.global.dto.ResponseDTO;
//import com.project.finalproject.global.jwt.utils.JwtExceptionFilter;
//import com.project.finalproject.global.jwt.utils.JwtFilter;
//import com.project.finalproject.global.jwt.utils.JwtUtil;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.util.HashMap;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
//@WebMvcTest(ApplicantController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//public class 지원하기 {
//
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean // ApplicantController에서 사용하는 ApplicantService를 MockBean으로 등록
//    ApplicantService applicantService;
//
//    @MockBean
//    JwtFilter jwtFilter;
//
//    @MockBean
//    JwtExceptionFilter jwtExceptionFilter;
//
//    @MockBean
//    JwtUtil jwtUtil;
//
//
//    @Test
//    @DisplayName("채용공고 지원하기 성공")
//    @WithMockUser
//    public void applySuccess() throws Exception {
//        JobpostIdRequestDTO jobpostIdRequestDTO = new JobpostIdRequestDTO();
//        jobpostIdRequestDTO.setJobpostId(1234L);
//        HashMap<String, String> mockHashMap = new HashMap<>();
//        mockHashMap.put("id", "1");
//
//        given(jwtUtil.allInOne(any()))
//                .willReturn(mockHashMap);
//        given(applicantService.applyJobpost(any(Long.class), any(Long.class)))
//                .willReturn("success");
//
//        MvcResult mvcResult = mockMvc.perform(post("/applicant/apply")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(jobpostIdRequestDTO)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseContent = mvcResult.getResponse().getContentAsString();
//        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);
//
//        assertEquals(200, responseDTO.getStateCode());
//        assertEquals(true, responseDTO.isSuccess());
//        assertEquals("success", responseDTO.getData());
//
//        verify(applicantService).applyJobpost(any(Long.class), any(Long.class));
//    }
//
//
//    @Test
//    @DisplayName("채용공고 지원하기 실패 : 채용공고가 마감됐습니다.")
//    @WithMockUser
//    public void applyFail1() throws Exception {
//        JobpostIdRequestDTO jobpostIdRequestDTO = new JobpostIdRequestDTO();
//        jobpostIdRequestDTO.setJobpostId(1234L);
//        HashMap<String, String> mockHashMap = new HashMap<>();
//        mockHashMap.put("id", "1");
//
//        given(jwtUtil.allInOne(any()))
//                .willReturn(mockHashMap);
//
//        given(applicantService.applyJobpost(any(Long.class), any(Long.class)))
//                .willReturn("due date passed");
//
//        MvcResult mvcResult = mockMvc.perform(post("/applicant/apply")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(jobpostIdRequestDTO)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseContent = mvcResult.getResponse().getContentAsString();
//        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);
//
//        assertEquals(401, responseDTO.getStateCode());
//        assertEquals(false, responseDTO.isSuccess());
//        assertEquals("due date passed", responseDTO.getData());
//
//        verify(applicantService).applyJobpost(any(Long.class), any(Long.class));
//    }
//
//
//    @Test
//    @DisplayName("채용공고 지원하기 실패 : 이미 지원했습니다.")
//    @WithMockUser
//    public void applyFail2() throws Exception {
//        JobpostIdRequestDTO jobpostIdRequestDTO = new JobpostIdRequestDTO();
//        jobpostIdRequestDTO.setJobpostId(1234L);
//        HashMap<String, String> mockHashMap = new HashMap<>();
//        mockHashMap.put("id", "1");
//
//        given(jwtUtil.allInOne(any()))
//                .willReturn(mockHashMap);
//
//        given(applicantService.applyJobpost(any(Long.class), any(Long.class)))
//                .willReturn("applied already");
//
//        MvcResult mvcResult = mockMvc.perform(post("/applicant/apply")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(jobpostIdRequestDTO)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseContent = mvcResult.getResponse().getContentAsString();
//        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);
//
//        assertEquals(402, responseDTO.getStateCode());
//        assertEquals(false, responseDTO.isSuccess());
//        assertEquals("applied already", responseDTO.getData());
//
//        verify(applicantService).applyJobpost(any(Long.class), any(Long.class));
//    }
//
//    @Test
//    @DisplayName("채용공고 지원하기 실패 : 이력서가 없습니다.")
//    @WithMockUser
//    public void applyFail3() throws Exception {
//        JobpostIdRequestDTO jobpostIdRequestDTO = new JobpostIdRequestDTO();
//        jobpostIdRequestDTO.setJobpostId(1234L);
//        HashMap<String, String> mockHashMap = new HashMap<>();
//        mockHashMap.put("id", "1");
//
//        given(jwtUtil.allInOne(any()))
//                .willReturn(mockHashMap);
//
//        given(applicantService.applyJobpost(any(Long.class), any(Long.class)))
//                .willReturn("no resume");
//
//        MvcResult mvcResult = mockMvc.perform(post("/applicant/apply")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(jobpostIdRequestDTO)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseContent = mvcResult.getResponse().getContentAsString();
//        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);
//
//        assertEquals(403, responseDTO.getStateCode());
//        assertEquals(false, responseDTO.isSuccess());
//        assertEquals("no resume", responseDTO.getData());
//
//        verify(applicantService).applyJobpost(any(Long.class), any(Long.class));
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
