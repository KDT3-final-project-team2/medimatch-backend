package com.project.finalproject.term.service;

import com.project.finalproject.term.dto.TermResDTO;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TermService {

    // 약관등록
    Long register(TermResDTO termResDTO, String adminEmail);
}
