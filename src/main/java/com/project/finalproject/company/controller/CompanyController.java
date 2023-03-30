package com.project.finalproject.company.controller;

import com.project.finalproject.company.dto.CompanyJobpostResponse;
import com.project.finalproject.company.service.CompanyService;
import com.project.finalproject.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/jobposts")
    public ResponseDTO<?> showJobpostList(){
        //todo : 토큰 완성되면 수정하기
        String email = "test@mail.com";

        List<CompanyJobpostResponse.ShortDTO> jobpostList = companyService.showJobpostList(email);

        return new ResponseDTO<>().ok(jobpostList,"리스트 출력 성공");
    }
}
