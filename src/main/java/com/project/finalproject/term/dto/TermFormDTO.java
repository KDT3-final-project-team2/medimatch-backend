package com.project.finalproject.term.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.finalproject.term.entity.Term;
import com.project.finalproject.term.entity.enums.TermStatus;
import com.project.finalproject.term.entity.enums.TermType;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TermFormDTO {
    private String content;

    private TermType type;
    private String version;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createDate;
    private LocalDateTime editDate;

    private TermStatus status;

}
