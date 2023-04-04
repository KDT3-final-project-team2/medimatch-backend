package com.project.finalproject.term.service;

import com.project.finalproject.term.dto.TermDetailResponseDTO;
import com.project.finalproject.term.dto.TermResDTO;
import com.project.finalproject.term.entity.enums.TermStatus;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Transactional
public interface TermService {
    // 약관등록
    Long register(TermResDTO termResDTO, String adminEmail);

    // 약관 전체목록 조회
    List<TermResDTO.TermList> showTermList(String adminEmail);

    // 약관 상세조회
    TermResDTO.TermDetail showTermDetail(String adminEmail, Long termId);

    //사용중인 회사의 약관들 가져오기
    public List<TermDetailResponseDTO> getRunningTerms(Long companyId, TermStatus status);
}
