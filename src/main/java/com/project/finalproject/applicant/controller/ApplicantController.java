package com.project.finalproject.applicant.controller;

import com.project.finalproject.applicant.dto.request.InfoUpdateRequestDTO;
import com.project.finalproject.applicant.dto.request.JobpostIdRequestDTO;
import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.applicant.dto.response.AppliedJobpostResponseDTO;
import com.project.finalproject.applicant.service.ApplicantService;
import com.project.finalproject.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applicant")
public class ApplicantController {

    private final ApplicantService applicantService;

    @GetMapping("/test")
    public ResponseDTO test(){
        return new ResponseDTO(200, true, null, "테스트");
    }

    @PostMapping("/checkemail")
    public ResponseDTO checkEmail(@RequestBody SignupRequestDTO signupRequestDTO){
        if(applicantService.checkEmail(signupRequestDTO).equals("duplicate id")){
            return new ResponseDTO(401, false, "duplicate id", "이미 존재하는 이메일입니다.");
        }else{
            return new ResponseDTO(200, true, "success", "사용 가능한 이메일입니다.");
        }
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseDTO signup(@RequestBody SignupRequestDTO signupRequestDTO){
        if(applicantService.signup(signupRequestDTO).equals("duplicate id")){
            return new ResponseDTO(401, false, "duplicate id", "이미 존재하는 이메일입니다.");
        }else{
            return new ResponseDTO(200, true, "success", "회원가입 성공");
        }
    }

    // 내 정보
    @GetMapping("/info")
    public ResponseDTO myInfo(){
        Long applicantId = 1L;
        return new ResponseDTO(200, true, applicantService.myInfo(applicantId), "내 정보");
    }

    // 정보 수정
    @PutMapping("/me")
    public ResponseDTO me(@RequestBody InfoUpdateRequestDTO infoUpdateRequestDTO){
        if (applicantService.infoUpdate(infoUpdateRequestDTO).equals("success")){
            return new ResponseDTO(200, true, "success", "회원정보 수정 성공");
        }else{
            return new ResponseDTO(401, false, "fail", "회원정보 수정 실패");
        }
    }

    // 메인페이지
    @GetMapping("/main")
    public ResponseDTO main(){
        List<AppliedJobpostResponseDTO> appliedJobpostResponseDTOList = applicantService.appliedJobposts();
        if(appliedJobpostResponseDTOList.size() == 0){
            return new ResponseDTO(401, false, null, "지원한 채용공고가 없습니다.");
        }
        return new ResponseDTO(200, true, appliedJobpostResponseDTOList, "지원한 채용공고 목록입니다.");
    }



    // 지원하기
    @PostMapping("/apply")
    public ResponseDTO applyJobpost(@RequestBody JobpostIdRequestDTO jobpostId) throws IOException {
        String message = applicantService.applyJobpost(jobpostId.getJobpostId());
        if(message.equals("due date passed")){
            return new ResponseDTO(401, false, "due date passed", "채용공고가 마감되었습니다.");
        }
        else if (message.equals("applied already")) {
            return new ResponseDTO(402, false, "applied already", "이미 지원했습니다.");
        }
        else if (message.equals("no resume")) {
            return new ResponseDTO(403, false, "no resume", "이력서가 없습니다.");
        }
        return new ResponseDTO(200, true, "success", "지원하기 성공");
    }

    // 지원취소
    @DeleteMapping("/apply")
    public ResponseDTO cancelApplyJobpost(@RequestBody JobpostIdRequestDTO jobpostId) throws IOException {
        String message = applicantService.cancelApplyJobpost(jobpostId.getJobpostId());
        if(message.equals("not applied")){
            return new ResponseDTO(401, false, "not applied", "지원하지 않았습니다.");
        }else{
            return new ResponseDTO(200, true, "success", "지원취소 성공");
        }
    }



    // 이력서 등록
    @PostMapping("/resume")
    public ResponseDTO resumeSave(MultipartFile resume) throws IOException {
        String message = applicantService.resumeSave(resume);
        if (message.equals("empty file")) {
            return new ResponseDTO(401, false, "empty file", "빈 파일입니다.");
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
            return new ResponseDTO(401, false, "file not found", "이력서가 존재하지 않습니다.");
        }else if(message.equals("fail")){
            return new ResponseDTO(402, false, "fail", "이력서 삭제 실패");
        }else{
            return new ResponseDTO(200, true, "success", "이력서 삭제 성공");
        }
    }


    // 채용공고 추천
    @GetMapping("/jobpost/suggest")
    public ResponseDTO jobpostSuggest(){
        return new ResponseDTO(200, true, null, "추천 채용공고");
    }
}
