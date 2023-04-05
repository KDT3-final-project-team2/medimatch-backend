package com.project.finalproject.company.dto;

import com.project.finalproject.jobpost.entity.Jobpost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@AllArgsConstructor
@Builder
public class ApplicationsForCompanyResponseDTO {


    private HashMap<String, Integer> applicantAgeCount;
    private HashMap<String, Integer> applicantGenderCount;
    private HashMap<String, Integer> applicantEducationCount;
    private HashMap<String, Integer> jobpostTitleCount;


}
