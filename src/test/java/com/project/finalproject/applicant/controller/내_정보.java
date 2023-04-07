//package com.project.finalproject.applicant.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.finalproject.applicant.dto.response.MyInfoResponseDTO;
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
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
//@WebMvcTest(ApplicantController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//public class 내_정보 {
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
//    @DisplayName("내 정보 불러오기 성공")
//    @WithMockUser
//    public void getMyInfoSuccess() throws Exception {
//        MyInfoResponseDTO mockMyInfoResponseDTO = new MyInfoResponseDTO();
//        HashMap<String, String> mockHashMap = new HashMap<>();
//        mockHashMap.put("id", "1");
//
//        given(jwtUtil.allInOne(any()))
//                .willReturn(mockHashMap);
//        given(applicantService.myInfo(any(Long.class)))
//                .willReturn(mockMyInfoResponseDTO); // given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드.
//
//        MvcResult mvcResult = mockMvc.perform(get("/applicant/info")
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//
//        String responseContent = mvcResult.getResponse().getContentAsString();
//        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);
//        assertEquals(200, responseDTO.getStateCode());
//        assertEquals(true, responseDTO.isSuccess());
////        assertEquals(mockMyInfoResponseDTO, responseDTO.getData()); //json 형태로 반환되어서 제회
//
//        verify(applicantService).myInfo(any(Long.class));
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
