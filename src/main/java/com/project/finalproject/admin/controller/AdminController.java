package com.project.finalproject.admin.controller;

import com.project.finalproject.admin.service.AdminService;
import com.project.finalproject.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/applicants")
    public ResponseDTO getAllApplicants(){
        return new ResponseDTO(200, true, adminService.getAllApplicants(), "All applicants");
    }

    @GetMapping("/companies")
    public ResponseDTO getAllCompanies(){
        return new ResponseDTO(200, true, adminService.getAllCompanies(), "All companies");
    }
}
