package com.project.finalproject.company.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.finalproject.applicant.entity.enums.Sector;
import com.project.finalproject.company.dto.CompanyJobpostRequest;
import com.project.finalproject.company.dto.CompanyJobpostResponse;
import com.project.finalproject.company.service.CompanyService;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.jobpost.entity.Jobpost;
import com.project.finalproject.login.dto.LoginResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/jobposts")
    public ResponseDTO<?> createJobpost(@AuthenticationPrincipal LoginResDTO userDetails,
                                        @RequestParam("reqeustDTO") String jsonList, MultipartFile jobpostFile) throws IOException {
        //TODO : 토큰 완성되면 수정하기
        String email = userDetails.getEmail();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        // SerializationFeature : 직렬화 (object -> json)
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false); // WRITE_DATES_AS_TIMSTAMPS(date를 timestamps로 찍는 기능) 해제
        CompanyJobpostRequest.CreateDTO requestDTO = om.readValue(jsonList, CompanyJobpostRequest.CreateDTO.class); //json DTO로 직렬화

        CompanyJobpostResponse.LongDTO newJobpost = companyService.createJobpost(email,requestDTO,jobpostFile);

        return new ResponseDTO<>().ok(newJobpost,"채용 공고 생성 완료");

    }

    @GetMapping("/jobposts")
    public ResponseDTO<?> showJobpostList(@AuthenticationPrincipal LoginResDTO userDetails){
        //TODO : 토큰 완성되면 수정하기
        String email = userDetails.getEmail();

        List<CompanyJobpostResponse.ShortDTO> jobpostList = companyService.showJobpostList(email);

        return new ResponseDTO<>().ok(jobpostList,"리스트 출력 성공");
    }

    @GetMapping("/jobposts/{postId}")
    public ResponseDTO<?> showJobpostDetail(@AuthenticationPrincipal LoginResDTO userDetails, @PathVariable Long postId){
        //TODO : 토큰 완성되면 수정하기
        String email = userDetails.getEmail();

        CompanyJobpostResponse.LongDTO jobpost = companyService.showJobpostDetail(email, postId);

        return new ResponseDTO<>().ok(jobpost,"데이터 출력 성공");
    }

    @GetMapping("/applications/statistics")
    public ResponseDTO companyApplicationsStatistics(){
        //#Todo 회사 ID 가져오기
        ApplicationsForCompanyResponseDTO responseDTO = companyService.statisticsForApplicationsForCompany(1L);
        if(responseDTO == null){
            return new ResponseDTO(401, false, "fail", "지원한 지원자가 없습니다.");
        }
        return new ResponseDTO(200, true, responseDTO, "지원한 지원자 통계입니다.");
    }
}
