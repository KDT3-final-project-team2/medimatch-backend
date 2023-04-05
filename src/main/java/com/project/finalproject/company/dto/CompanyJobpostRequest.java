package com.project.finalproject.company.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.finalproject.applicant.entity.enums.Sector;
import com.project.finalproject.jobpost.entity.enums.JobpostEducation;
import com.project.finalproject.jobpost.entity.enums.JobpostStatus;
import com.project.finalproject.jobpost.entity.enums.JobpostWorkExperience;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * 채용공고 관련 입력받는 DTO
 */
public class CompanyJobpostRequest {

    /**
     * 공고 생성시 사용 하는 DTO
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class CreateDTO{
        private String title; //공고 제목
        private LocalDateTime startDate; //공고 시작 날짜
        private LocalDateTime dueDate; //공고 종료 날짜
        private Sector sector; //모집 직무
        private JobpostEducation education; // 학력
        private JobpostWorkExperience workExperience; // 경력
        private Integer recruitNum; // 모집 인원
        private String filePath; // 채용 공고 파일 (== 내용)

    }

    public static class UpdateDTO{
        private String title; //공고 제목
        private LocalDateTime startDate; //공고 시작 날짜
        private LocalDateTime dueDate; //공고 종료 날짜
        private Sector sector; //모집 직무
        private JobpostEducation education; // 학력
        private JobpostWorkExperience workExperience; // 경력
        private Integer recruitNum; // 모집 인원
        private String filePath; // 채용 공고 파일 (== 내용)
        private JobpostStatus jobpostStatus; // 채용 공고 상태 (모집중, 마감, 폐기)

    }

}
