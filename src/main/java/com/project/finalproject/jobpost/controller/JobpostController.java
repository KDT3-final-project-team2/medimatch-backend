package com.project.finalproject.jobpost.controller;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.jobpost.service.JobpostService;
import com.project.finalproject.login.dto.LoginResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class JobpostController {

    private final JobpostService jobpostService;


    /**
     * 채용공고 삭제(관리자)
     * @param userDetail
     * @param postId
     * @return "success"
     */
    @DeleteMapping(value = "/jobposts/posts/{postId}")
    public ResponseDTO<?> remove(@AuthenticationPrincipal LoginResDTO userDetail,
                                 @PathVariable Long postId) {
        if(!userDetail.getRole().equals("ADMIN")){
            return new ResponseDTO(401, false,"Unauthorized", "권한이 없습니다.");
        } else {

            jobpostService.remove(postId);

            return new ResponseDTO(200, true, "success", "deleteJobpost success");
        }
    }

}
