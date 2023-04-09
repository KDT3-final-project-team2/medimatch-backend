package com.project.finalproject.company.dto;

import lombok.*;

/**
 * 합/불 메일발송
 */
@Getter
@Setter
@Builder
public class EmailReqDTO {
    private String email;
    private String title;
    private String content;
}
