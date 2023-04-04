package com.project.finalproject.term.service.impl;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.exception.CompanyException;
import com.project.finalproject.company.exception.CompanyExceptionType;
import com.project.finalproject.company.repository.CompanyRepository;
import com.project.finalproject.term.dto.TermDetailResponseDTO;
import com.project.finalproject.term.dto.TermResDTO;
import com.project.finalproject.term.entity.Term;
import com.project.finalproject.term.entity.enums.TermStatus;
import com.project.finalproject.term.exception.TermException;
import com.project.finalproject.term.exception.TermExceptionType;
import com.project.finalproject.term.repository.TermRepository;
import com.project.finalproject.term.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TermServiceImpl implements TermService {

    private final TermRepository termRepository;
    private final CompanyRepository companyRepository;

    /**
     * 슈퍼관리자(admin) 약관 등록
     * @param termResDTO
     * @param adminEmail
     * @return termId
     */
    @Override
    public Long register(TermResDTO termResDTO, String adminEmail) {

        Optional<Company> company = companyRepository.findByEmail(adminEmail);

        if (company.isEmpty()) {
            return null;
        } else {
            Term term = Term.createTerm(termResDTO,company.get());
            termRepository.save(term);

            return term.getId();
        }
    }

    /**
     * 슈퍼관리자(admin) 약관 전체목록 조회
     * @param adminEmail
     * @return 약관 목록 조회
     */
    @Override
    public List<TermResDTO.TermList> showTermList(String adminEmail) {

        Company company = companyRepository.findByEmail(adminEmail).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        List<Term> terms = termRepository.findByCompanyId(company.getId());

        return terms.stream()
                .map(TermResDTO.TermList::new)
                .collect(Collectors.toList());
    }

    /**
     * 슈퍼관리자(admin) 약관 상세조회
     * @param adminEmail
     * @param termId
     * @return 약관 상세조회
     */
    @Override
    public TermResDTO.TermDetail showTermDetail(String adminEmail, Long termId) {
        Company company = companyRepository.findByEmail(adminEmail).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );


        Term term = termRepository.findById(termId).orElseThrow(
                () -> new TermException(TermExceptionType.NOT_FOUND_PAGE)
        );

        return TermResDTO.TermDetail.builder()
                .term(term)
                .build();
    }

    @Override
    public List<TermDetailResponseDTO> getRunningTerms(Long companyId, TermStatus status) {
        List<Term> terms = termRepository.findByCompanyIdAndStatus(companyId, status);
        for (Term term : terms) {
            if (term.getStatus().equals(TermStatus.USE)) {
                return terms.stream()
                        .map(TermDetailResponseDTO::new)
                        .collect(Collectors.toList());
            }
        }
        return null;
    }
}
