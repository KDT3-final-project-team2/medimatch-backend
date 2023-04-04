package com.project.finalproject.term.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.global.jwt.utils.JwtFilter;
import com.project.finalproject.term.dto.TermDetailResponseDTO;
import com.project.finalproject.term.entity.enums.TermStatus;
import com.project.finalproject.term.service.TermService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
@WebMvcTest(TermController.class)
public class 사용중인_약관_불러오기 {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean // ApplicantController에서 사용하는 ApplicantService를 MockBean으로 등록
    TermService termService;

    @MockBean
    JwtFilter jwtFilter;


    @Test
    @DisplayName("사용중인 약관 불러오기 성공")
    @WithMockUser
    public void getRunningTermsSuccess() throws Exception {
        List<TermDetailResponseDTO> termList = Arrays.asList(
                new TermDetailResponseDTO(1L, "서비스이용약관 내용", "서비스이용약관", "1.0", LocalDateTime.now(), LocalDateTime.now().plusDays(7), "사용", 1L),
                new TermDetailResponseDTO(2L, "개인정보처리방침 내용", "개인정보처리방침", "1.0", LocalDateTime.now(), LocalDateTime.now().plusDays(7), "사용", 1L)
        );

        given(termService.getRunningTerms(any(Long.class),any(TermStatus.class)))
                .willReturn(termList); // given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드.


        MvcResult mvcResult = mockMvc.perform(get("/terms/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDTO = objectMapper.readValue(responseContent, ResponseDTO.class);
        assertEquals(200, responseDTO.getStateCode());
        assertEquals(true, responseDTO.isSuccess());
//        assertEquals(termList, responseDTO.getData()); //json 형태로 반환되어서 제회

        verify(termService).getRunningTerms(any(Long.class),any(TermStatus.class));
    }
}








































































