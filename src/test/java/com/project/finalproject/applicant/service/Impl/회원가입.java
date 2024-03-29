//package com.project.finalproject.applicant.service.Impl;
//
//import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
//import com.project.finalproject.applicant.entity.Applicant;
//import com.project.finalproject.applicant.repository.ApplicantRepository;
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
//
//@AutoConfigureMockMvc(addFilters = false) //SecurityConfig를 무시하고 테스트
//@SpringBootTest
//@Import(ApplicantServiceImpl.class)
////@TestPropertySource(locations = "classpath:application-local.properties")
//@TestPropertySource(locations = "classpath:application-test.properties")
//public class 회원가입 {
//
//
//    @Autowired
//    ApplicantServiceImpl applicantService;
//
//    @MockBean
//    ApplicantRepository applicantRepository;
//
//
//    @Test
//    @DisplayName("회원가입 성공")
//    @WithMockUser
//    public void checkSignupSuccess() {
//        SignupRequestDTO signupRequestDTO = new SignupRequestDTO();
//        signupRequestDTO.setApplicantEmail("test@example.com");
//        signupRequestDTO.setApplicantPassword("password1");
//
//        when(applicantRepository.findByEmail(any(String.class)))
//                .thenReturn(Optional.ofNullable(null));
//
//        String result = applicantService.signup(signupRequestDTO);
//
//        assertEquals("success", result);
//
//        verify(applicantRepository).findByEmail(any(String.class));
//    }
//
//    @Test
//    @DisplayName("이메일 중복체크 실패 : 중복 아이디")
//    @WithMockUser
//    public void checkSignupFail() {
//        SignupRequestDTO signupRequestDTO = new SignupRequestDTO();
//        signupRequestDTO.setApplicantEmail("test@example.com");
//
//        when(applicantRepository.findByEmail(any(String.class)))
//                .thenReturn(Optional.ofNullable(new Applicant()));
//
//        String result = applicantService.signup(signupRequestDTO);
//
//        assertEquals("duplicate id", result);
//
//        verify(applicantRepository).findByEmail(any(String.class));
//    }
//
//}