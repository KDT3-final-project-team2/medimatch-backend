package com.project.finalproject.company.dto;

import com.project.finalproject.jobpost.entity.Jobpost;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CompanyJobpostResponse {

    //채용 공고 목록 보기
    @Getter
    public static class LongDTO{
        private Long postId; //채용 공고 id
        private String title; //채용 공고 제목
        private LocalDate startDate; //채용 시작 날짜
        private LocalDate dueDate; //채용 마감 날짜
        private LocalDateTime createDate; //채용 공고 생성 날짜
        private LocalDateTime editDate; //채용 공고 수정 날짜
        private String status; //채용 공고 상태

        @Builder
        public LongDTO(Jobpost jobpost){
            this.postId = jobpost.getId();
            this.title= jobpost.getTitle();
            this.startDate = jobpost.getStartDate();
            this.dueDate = jobpost.getDueDate();
            this.createDate = jobpost.getCreateDate();
            this.editDate = jobpost.getEditDate();
            this.status = jobpost.getJobpostStatus().getStatus();
        }
    }

    //채용공고 상세 보기
    @Getter
    public static class ShortDTO {
        private Long postId; // 채용공고 id
        private String title; // 채용공고 제목
        private String sector; // 채용공고 직무
        private String education; // 채용공고 최소 학력
        private String workExperience; // 채용공고 최소 경력
        private String companyNm; // 채용 공고 올린 회사 이름
        private String companyTel; // 채용 공고 연락처
        private LocalDate startDate; // 채용 모집 시작일
        private LocalDate dueDate; // 채용 모집 마감일
        private LocalDateTime createDate; // 채용 공고 생성 시간
        private LocalDateTime editDate; // 채용 공고 수정 시간
        private Integer maxApplicants; //모집 인원
        private String filePath; //채용 공고 파일 경로
        private String status; //채용 공고 상태

        @Builder
        public ShortDTO(Jobpost jobpost){
            this.postId = jobpost.getId();
            this.title = jobpost.getTitle();
            this.sector = jobpost.getSector().getSector();
            this.education = jobpost.getEducation().getEducation();
            this.workExperience = jobpost.getExperience().getWorkExperience();
            this.companyNm = jobpost.getCompany().getName();
            this.companyTel = jobpost.getCompany().getContact();
            this.startDate = jobpost.getStartDate();
            this.dueDate = jobpost.getDueDate();
            this.createDate = jobpost.getCreateDate();
            this.editDate = jobpost.getEditDate();
            this.maxApplicants = jobpost.getMaxApplicants();
            this.filePath = jobpost.getFilepath();
            this.status = jobpost.getJobpostStatus().getStatus();
        }

    }
}
