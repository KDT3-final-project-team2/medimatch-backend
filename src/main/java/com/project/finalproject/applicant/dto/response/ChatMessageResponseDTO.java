package com.project.finalproject.applicant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessageResponseDTO<T> {
    private String httpMethod;
    private String page;
    private String message;

}