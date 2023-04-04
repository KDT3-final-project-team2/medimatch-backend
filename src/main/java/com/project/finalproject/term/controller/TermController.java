package com.project.finalproject.term.controller;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.term.dto.TermResDTO;
import com.project.finalproject.term.entity.Term;
import com.project.finalproject.term.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TermController {

    private final TermService termService;

    /**
     * 약관등록
     */
    @PostMapping(value = "/admin/term", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO<?> register(@Valid @RequestBody TermResDTO termResDTO) {
        //Todo token생성 후 약관등록 파라미터 수정하기
        String admin = "admin@admin.com";
        Long termId = termService.register(termResDTO, admin);

        if (termId == null) {
            return new ResponseDTO<>().ok("유요하지않은 Id입니다");
        } else {
            return new ResponseDTO<>().ok(termId, "생성완료된 약관Id");
        }
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

}