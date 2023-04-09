package com.project.finalproject.company.dto;

import com.project.finalproject.application.entity.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 기업회원이 지원자 정보 관련 입력받는 DTO
 */
public class CompanyApplicationRequest {

    /**
     * 지원자 상태변경에 필요한 DTO
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StatusReqDTO {
        Long applicationId; //지원서 id
        ApplicationStatus status; //변경할 지원자 상태
        LocalDateTime interviewDate; //인터뷰 날짜
        LocalDateTime passDate; //합격 날짜
        String memo;
    }

}
