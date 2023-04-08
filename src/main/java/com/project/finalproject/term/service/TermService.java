package com.project.finalproject.term.service;

import com.project.finalproject.term.dto.TermFormDTO;
import com.project.finalproject.term.dto.TermResDTO;
import com.project.finalproject.term.dto.TermTypeList;
import com.project.finalproject.term.entity.enums.TermStatus;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Transactional
public interface TermService {
    // 약관등록
    TermResDTO.TermDetail registerTerm(String email, TermFormDTO.registerDTO registerDTO) throws IOException;

    // 약관 전체목록 조회
    List<TermResDTO.TermList> showTermList(String email);

    // 약관 상세조회
    TermResDTO.TermDetail showTermDetail(String email, Long termId);

    // 약관 수정
    TermResDTO.TermDetail updateTerm(String email,Long termId,TermFormDTO.updateDTO updateDTO) throws IOException;

    //사용중인 회사의 약관들 가져오기
//    public List<TermDetailResponseDTO> getRunningTerms(Long companyId, TermStatus status);
    public TermTypeList getRunningTerms(Long companyId, TermStatus status);
}

