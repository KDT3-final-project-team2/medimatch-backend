package com.project.finalproject.applicant.controller;

import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.applicant.service.ApplicantService;
import com.project.finalproject.global.dto.ResponseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applicant")
public class ApplicantController {

    private final ApplicantService applicantService;

    @GetMapping("/test")
    public ResponseDTO test(){
        return new ResponseDTO(200, true, null, "테스트");
    }
    // 로그인
    @PostMapping("/login")
    public ResponseDTO login(){
        return new ResponseDTO(200, true, null, "로그인 성공");
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseDTO signup(@RequestBody SignupRequestDTO signupRequestDTO){
        if(applicantService.signup(signupRequestDTO).equals("duplicate ID")){
            return new ResponseDTO(401, false, "duplicate ID", "이미 존재하는 이메일입니다.");
        }else{
            return new ResponseDTO(200, true, "success", "회원가입 성공");
        }
    }

    // 메인페이지
    @GetMapping("/main")
    public ResponseDTO main(){
        return new ResponseDTO(200, true, null, "메인페이지");
    }

    // 지원하기
    @PostMapping("/apply")
    public ResponseDTO apply(){
        return new ResponseDTO(200, true, "success", "지원하기 성공");
    }

    // 지원취소
    @DeleteMapping("/apply")
    public ResponseDTO applyCancel(){
        return new ResponseDTO(200, true, "success", "지원취소 성공");
    }

    // 정보 수정
    @PutMapping("/me")
    public ResponseDTO me(){
        return new ResponseDTO(200, true, "success", "회원정보 수정 성공");
    }

    // 이력서 등록
    @PostMapping("/resume")
    public ResponseDTO resumeSave(MultipartFile resume) throws IOException {
        String message = applicantService.resumeSave(resume);
        if (message.equals("empty file")) {
            return new ResponseDTO(401, true, "empty file", "빈 파일입니다.");
        } else {
            return new ResponseDTO(200, true, "success", "이력서 등록 성공");
        }
    }

    // 이력서 조회
    @GetMapping("/resume")
    public ResponseEntity<Resource> resumeDownload() throws IOException {
        return applicantService.resumeDownload();
    }

    //이력서 수정
    @PutMapping("/resume")
    public ResponseDTO resumeUpdate(MultipartFile resume) throws IOException {
        String message = applicantService.resumeSave(resume);
        if (message.equals("empty file")) {
            return new ResponseDTO(401, true, "empty file", "빈 파일입니다.");
        } else {
            return new ResponseDTO(200, true, "success", "이력서 덮어쓰기 성공");
        }
    }

    // 이력서 삭제
    @DeleteMapping("/resume")
    public ResponseDTO resumeDelete() throws IOException {
        String message = applicantService.resumeDelete();
        if (message.equals("file not found")) {
            return new ResponseDTO(401, true, "file not found", "이력서가 존재하지 않습니다.");
        }else if(message.equals("fail")){
            return new ResponseDTO(401, true, "delete fail", "이력서 삭제 실패");
        }else{
            return new ResponseDTO(200, true, "success", "이력서 삭제 성공");
        }
    }

    // 내 정보
    @GetMapping("/info")
    public ResponseDTO myInfo(){
        return new ResponseDTO(200, true, null, "회사정보");
    }

    // 채용공고 추천
    @GetMapping("/jobpost/suggest")
    public ResponseDTO jobpostSuggest(){
        return new ResponseDTO(200, true, null, "추천 채용공고");
    }
}
