package com.project.finalproject.company.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.finalproject.company.dto.CompanyApplicationResponse;
import com.project.finalproject.company.service.CompanyService;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.global.jwt.utils.JwtExceptionFilter;
import com.project.finalproject.global.jwt.utils.JwtFilter;
import com.project.finalproject.global.jwt.utils.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
@WebMvcTest(CompanyController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class 지원자_통계_조회 {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean // ApplicantController에서 사용하는 ApplicantService를 MockBean으로 등록
    CompanyService companyService;

    @MockBean
    JwtFilter jwtFilter;

    @MockBean
    JwtExceptionFilter jwtExceptionFilter;

    @MockBean
    JwtUtil jwtUtil;


    @Test
    @DisplayName("지원자 통계 조회 성공")
    @WithMockUser
    public void companyApplicationsStatisticsSuccess() throws Exception {
        HashMap<String, Integer> mockHashMap = new HashMap<>();
        mockHashMap.put("20대", 1);

        CompanyApplicationResponse.StatisticsDTO mockDTO = CompanyApplicationResponse.StatisticsDTO.builder()
                .applicantAgeCount(mockHashMap)
                .applicantGenderCount(new HashMap<String, Integer>())
                .applicantEducationCount(new HashMap<String, Integer>())
                .jobpostTitleCount(new HashMap<String, Integer>())
                .build();

        HashMap<String, String> mockHashMap2 = new HashMap<>();
        mockHashMap2.put("id", "1");

        given(jwtUtil.allInOne(any()))
                .willReturn(mockHashMap2);
        given(companyService.statisticsForApplicationsForCompany(any(Long.class)))
                .willReturn(mockDTO); // given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드.

        MvcResult mvcResult = mockMvc.perform(get("/company/applications/statistics")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);
        assertEquals(200, responseDTO.getStateCode());
        assertEquals(true, responseDTO.isSuccess());
//        assertEquals(mockMyInfoResponseDTO, responseDTO.getData()); //json 형태로 반환되어서 제회

        verify(companyService).statisticsForApplicationsForCompany(any(Long.class));
    }

    @Test
    @DisplayName("지원자 통계 조회 실패 : 지원자가 없습니다.")
    @WithMockUser
    public void companyApplicationsStatisticsFail() throws Exception {
        CompanyApplicationResponse.StatisticsDTO mockDTO = CompanyApplicationResponse.StatisticsDTO.builder()
                .applicantAgeCount(new HashMap<String, Integer>())
                .applicantGenderCount(new HashMap<String, Integer>())
                .applicantEducationCount(new HashMap<String, Integer>())
                .jobpostTitleCount(new HashMap<String, Integer>())
                .build();
        HashMap<String, String> mockHashMap = new HashMap<>();
        mockHashMap.put("id", "1");

        given(jwtUtil.allInOne(any()))
                .willReturn(mockHashMap);
        given(companyService.statisticsForApplicationsForCompany(any(Long.class)))
                .willReturn(mockDTO); // given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드.

        MvcResult mvcResult = mockMvc.perform(get("/company/applications/statistics")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);
        assertEquals(401, responseDTO.getStateCode());
        assertEquals(false, responseDTO.isSuccess());
//        assertEquals(mockMyInfoResponseDTO, responseDTO.getData()); //json 형태로 반환되어서 제회

        verify(companyService).statisticsForApplicationsForCompany(any(Long.class));
    }

}








































































