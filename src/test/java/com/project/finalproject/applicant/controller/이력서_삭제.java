//package com.project.finalproject.applicant.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
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
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.HashMap;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
//@WebMvcTest(ApplicantController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//public class 이력서_삭제 {
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
//    @DisplayName("이력서 삭제 성공")
//    @WithMockUser
//    public void resumeDeleteSuccess() throws Exception {
//        HashMap<String, String> mockHashMap = new HashMap<>();
//        mockHashMap.put("id", "1");
//
//        given(jwtUtil.allInOne(any()))
//                .willReturn(mockHashMap);
//        given(applicantService.resumeDelete(any(Long.class)))
//                .willReturn("success");
//
//        MvcResult mvcResult = mockMvc.perform(delete("/applicant/resume")
//                        .with(csrf()))
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
//        verify(applicantService).resumeDelete(any(Long.class));
//    }
//
//    @Test
//    @DisplayName("이력서 삭제 실패")
//    @WithMockUser
//    public void resumeDeleteFail1() throws Exception {
//        HashMap<String, String> mockHashMap = new HashMap<>();
//        mockHashMap.put("id", "1");
//
//        given(jwtUtil.allInOne(any()))
//                .willReturn(mockHashMap);
//        given(applicantService.resumeDelete(any(Long.class)))
//                .willReturn("file not found");
//
//        MvcResult mvcResult = mockMvc.perform(delete("/applicant/resume")
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseContent = mvcResult.getResponse().getContentAsString();
//        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);
//
//        assertEquals(401, responseDTO.getStateCode());
//        assertEquals(false, responseDTO.isSuccess());
//        assertEquals("file not found", responseDTO.getData());
//
//        verify(applicantService).resumeDelete(any(Long.class));
//    }
//
//    @Test
//    @DisplayName("이력서 삭제 실패")
//    @WithMockUser
//    public void resumeDeleteFail2() throws Exception {
//        given(applicantService.resumeDelete(any(Long.class)))
//                .willReturn("fail");
//        HashMap<String, String> mockHashMap = new HashMap<>();
//        mockHashMap.put("id", "1");
//
//        given(jwtUtil.allInOne(any()))
//                .willReturn(mockHashMap);
//        MvcResult mvcResult = mockMvc.perform(delete("/applicant/resume")
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseContent = mvcResult.getResponse().getContentAsString();
//        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);
//
//        assertEquals(402, responseDTO.getStateCode());
//        assertEquals(false, responseDTO.isSuccess());
//        assertEquals("fail", responseDTO.getData());
//
//        verify(applicantService).resumeDelete(any(Long.class));
//    }
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
