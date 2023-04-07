package com.project.finalproject.term.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.finalproject.term.entity.enums.TermStatus;
import com.project.finalproject.term.entity.enums.TermType;
import lombok.*;


import java.time.LocalDateTime;
public class TermFormDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class registerDTO{
        private String content; // 약관내용
        private TermType type;  // 약관 타입
        private String version; // 약관 버전

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createDate;   //약관등록일
        private LocalDateTime editDate;     //약관수정일

        private TermStatus status;  //약관상태(사용,임시저장,폐기)
    }
}
