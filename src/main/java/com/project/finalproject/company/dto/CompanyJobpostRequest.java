package com.project.finalproject.company.dto;

import com.project.finalproject.applicant.entity.enums.Sector;
import com.project.finalproject.jobpost.entity.enums.JobpostEducation;
import com.project.finalproject.jobpost.entity.enums.JobpostStatus;
import com.project.finalproject.jobpost.entity.enums.JobpostWorkExperience;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 채용공고 관련 입력받는 DTO
 */
public class CompanyJobpostRequest {

    /**
     * 공고 생성시 사용 하는 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateDTO{

        @NotNull
        private String title; //공고 제목

        private LocalDateTime startDate; //공고 시작 날짜
        private LocalDateTime dueDate; //공고 종료 날짜

        @NotNull(message = "직무가 전달되지 않았습니다.")
        private Sector sector; //모집 직무

        @NotNull(message = "학력이 전달되지 않았습니다.")
        private JobpostEducation education; // 학력

        @NotNull(message = "경력이 전달되지 않았습니다.")
        private JobpostWorkExperience workExperience; // 경력

        private Integer recruitNum; // 모집 인원
        private String filePath; // 채용 공고 파일 (== 내용)

        public void setFilePath(String filePath){
            this.filePath = filePath;
        }

    }

    /**
     * 공고 수정시 사용하는 DTO
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class UpdateDTO{
        private String title; //공고 제목
        private LocalDateTime startDate; //공고 시작 날짜
        private LocalDateTime dueDate; //공고 종료 날짜
        private Sector sector; //모집 직무
        private JobpostEducation education; // 학력
        private JobpostWorkExperience workExperience; // 경력
        private Integer recruitNum; // 모집 인원
        private JobpostStatus jobpostStatus; // 채용 공고 상태 (모집중, 마감, 폐기)

    }

}
