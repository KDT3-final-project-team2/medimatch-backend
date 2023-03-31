package com.project.finalproject.company.controller;

import com.project.finalproject.company.dto.CompanyJobpostResponse;
import com.project.finalproject.company.service.CompanyService;
import com.project.finalproject.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/jobposts")
    public ResponseDTO<?> showJobpostList(){
        //TODO : 토큰 완성되면 수정하기
        String email = "test@mail.com";

        List<CompanyJobpostResponse.ShortDTO> jobpostList = companyService.showJobpostList(email);

        return new ResponseDTO<>().ok(jobpostList,"리스트 출력 성공");
    }

    @GetMapping("/jobposts/{postId}")
    public ResponseDTO<?> showJobpostDetail(@PathVariable Long postId){
        //TODO : 토큰 완성되면 수정하기
        String email = "test@mail.com";

        CompanyJobpostResponse.LongDTO jobpost = companyService.showJobpostDetail(email, postId);

        return new ResponseDTO<>().ok(jobpost,"데이터 출력 성공");
    }
}
