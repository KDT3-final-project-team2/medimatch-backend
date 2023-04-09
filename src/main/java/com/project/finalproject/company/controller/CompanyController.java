package com.project.finalproject.company.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.company.dto.*;
import com.project.finalproject.company.service.CompanyService;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.global.jwt.utils.JwtUtil;
import com.project.finalproject.login.dto.LoginResDTO;
import com.project.finalproject.signup.dto.CompanySignupReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    private final JwtUtil jwtutil;





    @PostMapping("/checkemail")
    public ResponseDTO checkEmail(@RequestBody CompanySignupReqDTO companySignupReqDTO){
        if(companyService.checkEmail(companySignupReqDTO).equals("duplicate id")){
            return new ResponseDTO(401, false, "duplicate id", "이미 존재하는 이메일입니다.");
        }else{
            return new ResponseDTO(200, true, "success", "사용 가능한 이메일입니다.");
        }
    }

    /**
     * 채용 공고 생성
     * @param userDetails 토큰
     * @param jsonList 안에 채울 json 데이터
     * @param jobpostFile 채용 공고 pdf 파일
     * @return 채용공고 상세 조회
     * @throws IOException
     */


    @PostMapping("/jobposts")
    public ResponseDTO<?> createJobpost(@AuthenticationPrincipal LoginResDTO userDetails,
                                        @RequestParam("requestDTO") String jsonList, MultipartFile jobpostFile) throws IOException {
        String email = userDetails.getEmail();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        // SerializationFeature : 직렬화 (object -> json)
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false); // WRITE_DATES_AS_TIMSTAMPS(date를 timestamps로 찍는 기능) 해제
        CompanyJobpostRequest.CreateDTO requestDTO = om.readValue(jsonList, CompanyJobpostRequest.CreateDTO.class); //json -> DTO로 직렬화

        CompanyJobpostResponse.LongDTO newJobpost = companyService.createJobpost(email,requestDTO,jobpostFile);

        return new ResponseDTO<>().ok(newJobpost,"채용 공고 생성 완료");
    }

    /**
     * 채용공고 리스트 조회
     * @param userDetails 토큰
     * @return 채용공고 간략한 정보 리스트
     */
    @GetMapping("/jobposts")
    public ResponseDTO<?> showJobpostList(@AuthenticationPrincipal LoginResDTO userDetails){
        String email = userDetails.getEmail();

        List<CompanyJobpostResponse.ShortDTO> jobpostList = companyService.showJobpostList(email);

        return new ResponseDTO<>().ok(jobpostList,"리스트 출력 성공");
    }

    /**
     * 채용공고 상세 조회
     * @param userDetails 토큰
     * @param postId 상세 조회할 게시글 아이디
     * @return 상세 조회 데이터
     */
    @GetMapping("/jobposts/{postId}")
    public ResponseDTO<?> showJobpostDetail(@AuthenticationPrincipal LoginResDTO userDetails, @PathVariable Long postId){
        String email = userDetails.getEmail();

        CompanyJobpostResponse.LongDTO jobpost = companyService.showJobpostDetail(email, postId);

        return new ResponseDTO<>().ok(jobpost,"데이터 출력 성공");
    }

    /**
     * 채용 공고 수정
     * @param userDetails 토큰
     * @param postId 수정할 게시글 아이디
     * @param jsonList 수정할 내용
     * @param jobpostFile 수정할 파일
     * @return 채용공고 상세보기
     * @throws IOException
     */
    @PutMapping("/jobposts/{postId}")
    public ResponseDTO<?> updateJobpost(@AuthenticationPrincipal LoginResDTO userDetails,
                                        @PathVariable Long postId,
                                        @RequestParam("requestDTO") String jsonList, MultipartFile jobpostFile) throws IOException {
        String email = userDetails.getEmail();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        om.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true); // Object로 가져올 때 빈 문자열을 null로 처리.
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);
        CompanyJobpostRequest.UpdateDTO requestDTO = om.readValue(jsonList, CompanyJobpostRequest.UpdateDTO.class);

        CompanyJobpostResponse.LongDTO updateJobpost = companyService.updateJobpost(email,postId,requestDTO,jobpostFile);

        return new ResponseDTO<>().ok(updateJobpost,"수정 성공");
    }

    /**
     * 채용공고 삭제 (실제로 삭제 x 상태만 폐기로 변경)
     * @param userDetails 토큰
     * @param postId 상태 변경할 게시글 아이디
     * @return 변경 완료 메세지만 출력
     */
    @DeleteMapping("/jobposts/{postId}")
    public ResponseDTO<?> deleteJobpost(@AuthenticationPrincipal LoginResDTO userDetails, @PathVariable Long postId){
        String email = userDetails.getEmail();

        CompanyJobpostResponse.LongDTO newJobpost = companyService.deleteJobpost(email, postId);

        return new ResponseDTO<>().ok(newJobpost, "상태 변경 완료");
    }


    @GetMapping("/applications/statistics")
    public ResponseDTO companyApplicationsStatistics(HttpServletRequest request){
        Long companyId = Long.parseLong(jwtutil.allInOne(request.getHeader(HttpHeaders.AUTHORIZATION)).get("id"));
        CompanyApplicationResponse.StatisticsDTO responseDTO = companyService.statisticsForApplicationsForCompany(companyId);
        if(responseDTO.getApplicantAgeCount().size() == 0){
            return new ResponseDTO(401, false, "fail", "지원한 지원자가 없습니다.");
        }
        return new ResponseDTO(200, true, responseDTO, "지원한 지원자 통계입니다.");
    }

    /**
     * 지원자 목록 출력
     * @param userDetail : 토큰
     * @return 지원자 리스트
     */
    @GetMapping("/applications")
    public ResponseDTO<?> showApplicants(@AuthenticationPrincipal LoginResDTO userDetail){
        String email = userDetail.getEmail();

        List<CompanyApplicationResponse.ApplicantInfoDTO> applicantInfoDTOList = companyService.showApplicantInfo(email);

        return new ResponseDTO<>().ok(applicantInfoDTOList,"지원자 목록 출력 완료");
    }

    /**
     * 지원서 상태 변경
     * @param userDetail : 토큰
     * @param statusReqDTO 상태 변경에 필요한 데이터
     * @return 성공 메세지
     */
    @PostMapping("/applications")
    public ResponseDTO<?> changeApplicationStatus(@AuthenticationPrincipal LoginResDTO userDetail, @RequestBody CompanyApplicationRequest.StatusReqDTO statusReqDTO){
        String email = userDetail.getEmail();

        companyService.changeApplicationStatus(email, statusReqDTO);

        return new ResponseDTO<>().ok("지원자 상태 변경 성공");
    }

    /**
     * 기업회원 정보 출력
     * @param userDetail : 토큰
     * @return 기업회원 데이터
     */
    @GetMapping("/me")
    public ResponseDTO<?> showCompanyInfo(@AuthenticationPrincipal LoginResDTO userDetail){
        String email = userDetail.getEmail();

        CompanyResponse.InfoDTO companyInfo = companyService.showCompanyInfo(email);

        return new ResponseDTO<>().ok(companyInfo,"기업회원 정보 출력 완료");
    }

    @PutMapping("/me")
    public ResponseDTO<?> updateCompanyInfo(@AuthenticationPrincipal LoginResDTO userDetail, @RequestBody CompanyRequest.UpdateInfoDTO updateReqDTO){
        String email = userDetail.getEmail();

        CompanyResponse.InfoDTO companyInfo = companyService.updateCompanyInfo(email, updateReqDTO);

        return new ResponseDTO<>().ok(companyInfo,"기업회원 정보 수정 완료");
    }

    /**
     * 합/불 메일발송
     */
    @PostMapping("/result")
    public ResponseDTO sendEmail(@RequestBody EmailReqDTO emailReqDTO) throws MessagingException {
        return companyService.sendEmail(emailReqDTO);
    }
}
