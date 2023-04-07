package com.project.finalproject.term.service.impl;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.entity.enums.CompanyType;
import com.project.finalproject.term.dto.TermDetailResponseDTO;
import com.project.finalproject.term.entity.Term;
import com.project.finalproject.term.entity.enums.TermStatus;
import com.project.finalproject.term.entity.enums.TermType;
import com.project.finalproject.term.repository.TermRepository;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
@SpringBootTest
@Import(TermServiceImpl.class)
//@TestPropertySource(locations = "classpath:application-local.properties")
@TestPropertySource(locations = "classpath:application-test.properties")
public class 사용중인_약관_불러오기 {


    @Autowired
    TermServiceImpl termService;

    @MockBean
    TermRepository termRepository;


    @Test
    @DisplayName("사용중인 약관 불러오기 성공")
    @WithMockUser
    public void getRunningTermsSuccess() {
        Long companyId = 1L;
        TermStatus status = TermStatus.USE;

        List<TermDetailResponseDTO> termList = Arrays.asList(
                new TermDetailResponseDTO(1L, "서비스이용약관 내용", "서비스이용약관", "1.0", LocalDateTime.now(), LocalDateTime.now().plusDays(7), "사용", 1L),
                new TermDetailResponseDTO(2L, "개인정보처리방침 내용", "개인정보처리방침", "1.0", LocalDateTime.now(), LocalDateTime.now().plusDays(7), "사용", 1L)
        );
        Company company = new Company(1L, "회사이메일", "회사비밀번호", "회사명", "회사주소", "회사 연락처", "회사 사업자번호", "회사 대표자 이름", "회사 홈페이지", CompanyType.ADMIN, LocalDate.now(), null);

        List<Term> terms = Arrays.asList(
                new Term(1L, "서비스이용약관 내용", TermType.SERVICE, "1.0", LocalDateTime.now(), LocalDateTime.now().plusDays(7), TermStatus.USE, company),
                new Term(2L, "개인정보처리방침 내용", TermType.PRIVACY, "1.0", LocalDateTime.now(), LocalDateTime.now().plusDays(7), TermStatus.USE, company)
        );


        when(termRepository.findByCompanyIdAndStatus(companyId, status))
                .thenReturn(terms);

        when(termService.getRunningTerms(companyId,status))
                .thenReturn(termList);
        when(termRepository.findByCompanyIdAndStatus(companyId, status))
                .thenReturn(terms);

        List<TermDetailResponseDTO> termDetailResponseDTOs = termService.getRunningTerms(companyId, status);

        assertNotNull(termDetailResponseDTOs);
        assertFalse(termDetailResponseDTOs.isEmpty());
        assertEquals(2, termDetailResponseDTOs.size());

        TermDetailResponseDTO termDetailResponseDTO = termDetailResponseDTOs.get(0);
        assertEquals(1L, termDetailResponseDTO.getTermId());
        assertEquals("서비스이용약관 내용", termDetailResponseDTO.getTermContent());
        assertEquals("사용", termDetailResponseDTO.getTermStatus());
        assertEquals(1L, termDetailResponseDTO.getCompanyId());

        verify(termRepository).findByCompanyIdAndStatus(companyId, status);
    }

}