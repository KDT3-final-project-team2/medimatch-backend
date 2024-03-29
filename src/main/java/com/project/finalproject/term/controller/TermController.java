package com.project.finalproject.term.controller;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.login.dto.LoginResDTO;
import com.project.finalproject.term.dto.TermFormDTO;
import com.project.finalproject.term.dto.TermResDTO;
import com.project.finalproject.term.dto.TermTypeList;
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
     * @param userDetail 토큰
     * @param registerDTO 약관등록DTO
     * @return 등록한약관 상세조회
     * @throws IOException
     */
    @PostMapping(value = "/admin/term", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO<?> adminRegister(@AuthenticationPrincipal LoginResDTO userDetail, @Valid @RequestBody TermFormDTO.registerDTO registerDTO) throws IOException {

        if(!userDetail.getRole().equals("ADMIN")){

            return new ResponseDTO(401, false,"Unauthorized", "접근권한이 없습니다.");

        } else {
            String email = userDetail.getEmail();

            TermResDTO.TermDetail newTerm = termService.registerTerm(email, registerDTO);

            return new ResponseDTO<>().ok(newTerm, "약관 등록 성공");
        }
    }

    /**
     * 기업회원 약관 등록
     * @param userDetail 토큰
     * @param registerDTO
     * @return 등록한 약관 상세조회
     * @throws IOException
     */
    @PostMapping(value = "/company/term", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO<?> comapnyRegister(@AuthenticationPrincipal LoginResDTO userDetail, @Valid @RequestBody TermFormDTO.registerDTO registerDTO) throws IOException {

        String email = userDetail.getEmail();

        TermResDTO.TermDetail newTerm = termService.registerTerm(email,registerDTO);

        return new ResponseDTO<>().ok(newTerm, "약관 등록 성공");
    }

    /**
     * 관리자 약관 목록조회
     * @param userDetail
     * @return 약관 목록
     */
    @GetMapping(value = "/admin/term/list")
    public ResponseDTO<?> adminTermList(@AuthenticationPrincipal LoginResDTO userDetail) {
        if(!userDetail.getRole().equals("ADMIN")){

            return new ResponseDTO(401, false,"Unauthorized", "접근권한이 없습니다.");

        } else {
            String email = userDetail.getEmail();

            List<TermResDTO.TermList> termList = termService.showTermList(email);

            return new ResponseDTO<>().ok(termList, "약관 조회 성공");
        }
    }

    /**
     * 기업회원 약관 목록조회
     * @param userDetail
     * @return 약관 목록
     */
    @GetMapping(value = "/company/term/list")
    public ResponseDTO<?> companyTermList(@AuthenticationPrincipal LoginResDTO userDetail) {

        String email = userDetail.getEmail();

        List<TermResDTO.TermList> termList = termService.showTermList(email);

        return new ResponseDTO<>().ok(termList, "약관 조회 성공");
    }

    /**
     * 관리자 약관 상세 조회
     * @param userDetail
     * @param termId
     * @return 약관 상세 조회
     */
    @GetMapping(value = "/admin/term/{termId}")
    public ResponseDTO<?> adminTermDetail(@AuthenticationPrincipal LoginResDTO userDetail, @PathVariable Long termId) {
        if(!userDetail.getRole().equals("ADMIN")){

            return new ResponseDTO(401, false,"Unauthorized", "접근권한이 없습니다.");

        } else {
            String email = userDetail.getEmail();

            TermResDTO.TermDetail termDetail = termService.showTermDetail(email, termId);

            return new ResponseDTO<>().ok(termDetail, "termDetail success");
        }
    }

    /**
     * 기업회원 약관 상세 조회
     * @param userDetail
     * @param termId
     * @return 약관 상세 조회
     */
    @GetMapping(value = "/company/term/{termId}")
    public ResponseDTO<?> companyTermDetail(@AuthenticationPrincipal LoginResDTO userDetail, @PathVariable Long termId) {

        String email = userDetail.getEmail();

        TermResDTO.TermDetail termDetail = termService.showTermDetail(email, termId);

        return new ResponseDTO<>().ok(termDetail, "termDetail success");
    }

    /**
     * 관리자 약관 수정
     * @param userDetail
     * @param termId
     * @param updateDTO
     * @return 수정한 약관 상세조회
     * @throws IOException
     */
    @PutMapping(value = "/admin/term/{termId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO<?> adminUpdateTerm(@AuthenticationPrincipal LoginResDTO userDetail,
                                     @PathVariable Long termId,
                                     @Valid @RequestBody TermFormDTO.updateDTO updateDTO) throws IOException {
        if(!userDetail.getRole().equals("ADMIN")){

            return new ResponseDTO(401, false,"Unauthorized", "접근권한이 없습니다.");

        } else {
            String email = userDetail.getEmail();

            TermResDTO.TermDetail updateTerm = termService.updateTerm(email, termId, updateDTO);

            return new ResponseDTO<>().ok(updateTerm, "약관 수정 성공");
        }
    }

    /**
     * 기업회원 약관 수정
     * @param userDetail
     * @param termId
     * @param updateDTO
     * @return 수정한 약관 상세조회
     * @throws IOException
     */
    @PutMapping(value = "/company/term/{termId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO<?> companyUpdateTerm(@AuthenticationPrincipal LoginResDTO userDetail,
                                     @PathVariable Long termId,
                                     @Valid @RequestBody TermFormDTO.updateDTO updateDTO) throws IOException {
        String email = userDetail.getEmail();

        TermResDTO.TermDetail updateTerm = termService.updateTerm(email,termId,updateDTO);

        return new ResponseDTO<>().ok(updateTerm, "약관 수정 성공");
    }

    /*
    * 약관 목록 불러오기
     */
    @GetMapping(value = "/terms/{companyId}")
    public ResponseDTO<?> showTerms(@PathVariable Long companyId) {
        TermTypeList termList = termService.getRunningTerms(companyId, TermStatus.USE);
        return new ResponseDTO(200, true, termList, "사용중인 약관 목록");
    }
}