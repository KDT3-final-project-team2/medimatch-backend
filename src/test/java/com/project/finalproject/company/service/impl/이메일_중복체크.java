//package com.project.finalproject.company.service.impl;//package com.project.finalproject.applicant.service.Impl;
//
//import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
//import com.project.finalproject.applicant.entity.Applicant;
//import com.project.finalproject.applicant.repository.ApplicantRepository;
//import com.project.finalproject.applicant.service.Impl.ApplicantServiceImpl;
//import com.project.finalproject.company.entity.Company;
//import com.project.finalproject.company.repository.CompanyRepository;
//import com.project.finalproject.signup.dto.CompanySignupReqDTO;
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
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//
//@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
//@SpringBootTest
//@Import(CompanyServiceImpl.class)
////@TestPropertySource(locations = "classpath:application-local.properties")
//@TestPropertySource(locations = "classpath:application-test.properties")
//public class 이메일_중복체크 {
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
//    @DisplayName("이메일 중복체크 성공")
//    @WithMockUser
//    public void checkEmailSuccess() {
//        CompanySignupReqDTO companySignupReqDTO = new CompanySignupReqDTO();
//        companySignupReqDTO.setCompanyEmail("test@example.com");
//
//        when(companyRepository.findByEmail(any(String.class)))
//                .thenReturn(Optional.ofNullable(null));
//
//        String result = companyService.checkEmail(companySignupReqDTO);
//
//        assertEquals("success", result);
//
//        verify(companyRepository).findByEmail(any(String.class));
//    }
//
//    @Test
//    @DisplayName("이메일 중복체크 실패 : 중복 아이디")
//    @WithMockUser
//    public void checkEmailFail() {
//        CompanySignupReqDTO companySignupReqDTO = new CompanySignupReqDTO();
//        companySignupReqDTO.setCompanyEmail("test@example.com");
//
//        when(companyRepository.findByEmail(any(String.class)))
//                .thenReturn(Optional.ofNullable(new Company()));
//
//        String result = companyService.checkEmail(companySignupReqDTO);
//
//        assertEquals("duplicate id", result);
//
//        verify(companyRepository).findByEmail(any(String.class));
//    }
//
//}