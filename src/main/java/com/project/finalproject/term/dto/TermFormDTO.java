package com.project.finalproject.term.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.finalproject.term.entity.enums.TermStatus;
import com.project.finalproject.term.entity.enums.TermType;
import lombok.*;


import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
public class TermFormDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class registerDTO{
        @NotNull(message = "약관내용이 전달되지 않았습니다.")
        private String content; // 약관내용
        @NotNull(message = "약관타입이 전달되지 않았습니다.")
        private TermType type;  // 약관 타입
        @NotNull(message = "약관버전이 전달되지 않았습니다.")
        private String version; // 약관 버전

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createDate;   //약관등록일
        private LocalDateTime editDate;     //약관수정일

        @NotNull(message = "약관상태가 전달되지 않았습니다.")
        private TermStatus status;  //약관상태(사용,임시저장,폐기)
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class updateDTO{
        private String content; // 약관내용
        private TermType type;  // 약관 타입
        private String version; // 약관 버전
        private TermStatus status;  //약관상태(사용,임시저장,폐기)
    }
}
