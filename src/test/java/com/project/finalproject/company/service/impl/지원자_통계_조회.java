//package com.project.finalproject.company.service.impl;
//
//import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
//import com.project.finalproject.applicant.entity.enums.Gender;
//import com.project.finalproject.company.dto.CompanyApplicationResponse;
//import com.project.finalproject.company.repository.CompanyRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.TestPropertySource;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
//@SpringBootTest
//@Import(CompanyServiceImpl.class)
////@TestPropertySource(locations = "classpath:application-local.properties")
//@TestPropertySource(locations = "classpath:application-test.properties")
//public class 지원자_통계_조회 {
//
//
//    @Autowired
//    CompanyServiceImpl companyService;
//
//    @MockBean
//    CompanyRepository companyRepository;
//
//
//    @Test
//    @DisplayName("지원자 통계 조회 성공")
//    @WithMockUser
//    public void statisticsForApplicationsForCompanySuccess() {
//        Long companyId = 1L;
//        Object[] row1 = {"1990", Gender.M, ApplicantEducation.HIGH_SCHOOL, "Software Engineer"};
//        Object[] row2 = {"1985", Gender.W, ApplicantEducation.JUNIOR_COLLEGE, "Data Analyst"};
//
//        List<Object[]> mockResults = Arrays.asList(row1, row2);
//
//        when(companyRepository.findApplicationsForCompany(companyId))
//                .thenReturn(mockResults);
//
//        CompanyApplicationResponse.StatisticsDTO statisticsDTO = companyService.statisticsForApplicationsForCompany(companyId);
//
//        assertNotNull(statisticsDTO);
//
//        verify(companyRepository).findApplicationsForCompany(companyId);
//    }
//
//}