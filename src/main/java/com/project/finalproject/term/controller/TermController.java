package com.project.finalproject.term.controller;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.login.dto.LoginResDTO;
import com.project.finalproject.term.dto.TermDetailResponseDTO;
import com.project.finalproject.term.dto.TermFormDTO;
import com.project.finalproject.term.dto.TermResDTO;
import com.project.finalproject.term.entity.enums.TermStatus;
import com.project.finalproject.term.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TermController {

    private final TermService termService;

    /**
     * 관리자 약관 등록
     * @param userDetails 토큰
     * @param registerDTO 약관등록dto
     * @return 등록한약관 상세조회
     * @throws IOException
     */
    @PostMapping(value = "/admin/term", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO<?> adminRegister(@AuthenticationPrincipal LoginResDTO userDetails, @Valid @RequestBody TermFormDTO.registerDTO registerDTO) throws IOException {

        String email = userDetails.getEmail();

        TermResDTO.TermDetail newTerm = termService.registerTerm(email,registerDTO);

        return new ResponseDTO<>().ok(newTerm, "약관 등록 성공");
    }

    /**
     * 약관목록조회
     */
    @GetMapping(value = "/admin/term/list")
    public ResponseDTO<?> showTermList() {
        //TODO : 토큰 완성되면 약관목록조회 파라미터 수정하기
        String admin = "admin@admin.com";

        List<TermResDTO.TermList> termList = termService.showTermList(admin);

        return new ResponseDTO<>().ok(termList, "termList success");
    }

    /**
     *
     * 약관 상세조회
     */
    @GetMapping(value = "/admin/term/{termId}")
    public ResponseDTO<?> showTermDetail(@PathVariable Long termId) {
        //TODO : 토큰 완성되면 약관상세조회 파라미터 수정하기
        String admin = "admin@admin.com";

        TermResDTO.TermDetail termDetail = termService.showTermDetail(admin, termId);

        return new ResponseDTO<>().ok(termDetail, "termDetail success");
    }

    /*
    * 약관 목록 불러오기
     */
    @GetMapping(value = "/terms/{companyId}")
    public ResponseDTO<?> showTerms(@PathVariable Long companyId) {
        List<TermDetailResponseDTO> termList = termService.getRunningTerms(companyId, TermStatus.USE);
        return new ResponseDTO(200, true, termList, "사용중인 약관 목록");
    }
}