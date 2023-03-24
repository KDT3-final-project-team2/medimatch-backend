package com.project.finalproject.jobpost.dto;

import com.project.finalproject.jobpost.entity.Jobpost;
import com.project.finalproject.jobpost.entity.enums.JobpostStatus;
import lombok.*;

import java.time.LocalDate;

public class JobpostResponseDTO {

    //리스트 조회시 출력하는 DTO (간략한 정보만 출력)
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShortDTO{

        Long postId;

        String title;

        LocalDate startDate;

        LocalDate finishDate;

        int views;

        String companyName;

        JobpostStatus jobpostStatus;

        String contents;

        public ShortDTO(Jobpost jobpost){
            this.postId = jobpost.getJobpostId();
            this.title = jobpost.getTitle();
            this.startDate = jobpost.getStartDate();
            this.finishDate = jobpost.getFinishDate();
            this.views = jobpost.getViews();
            this.companyName = jobpost.getCompany().getName();
            this.jobpostStatus = jobpost.getJobpostStatus();
            this.contents = jobpost.getContentsFilePath();
        }
    }

    //상세 조회시 출력하는 DTO (모든 정보 출력)
    class LongDTO{

    }
}
