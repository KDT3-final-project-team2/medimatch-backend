package com.project.finalproject.term.service;

import com.project.finalproject.term.dto.TermResDTO;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TermService {

    // 약관등록
    Long register(TermResDTO termResDTO, String adminEmail);

    // 약관 전체목록 조회
    List<TermResDTO.TermList> showTermList(String adminEmail);

    // 약관 상세조회
    TermResDTO.TermDetail showTermDetail(String adminEmail, Long termId);
}
