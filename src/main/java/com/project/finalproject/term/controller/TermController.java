package com.project.finalproject.term.controller;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.term.dto.TermResDTO;
import com.project.finalproject.term.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TermController {

    private final TermService termService;

    /**
     * 약관등록
     */
    @PostMapping(value = "/admin/term",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO<?> register(@Valid @RequestBody TermResDTO termResDTO) {
        //Todo token생성 후 약관등록 파라미터 수정하기
        String admin = "admin@admin.com";
        Long termId =termService.register(termResDTO,admin);

        if (termId == null) {
            return new ResponseDTO<>().ok("유요하지않은 Id입니다");
        }else {
            return new ResponseDTO<>().ok(termId, "생성완료된 약관Id");
        }
    }

}
