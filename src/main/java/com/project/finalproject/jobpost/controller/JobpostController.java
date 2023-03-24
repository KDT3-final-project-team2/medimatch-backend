package com.project.finalproject.jobpost.controller;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.jobpost.dto.JobpostResponseDTO;
import com.project.finalproject.jobpost.service.JobpostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobposts")
public class JobpostController {

    private final JobpostService jobpostService;

    @GetMapping("/posts")
    public ResponseDTO<?> showJobpostList(){
        List<JobpostResponseDTO.ShortDTO> jobpostDTOs = jobpostService.showJobpostList();

        return new ResponseDTO<>().ok(jobpostDTOs,"리스트 출력 성공");
    }

}
