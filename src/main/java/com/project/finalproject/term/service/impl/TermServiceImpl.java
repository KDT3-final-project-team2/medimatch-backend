package com.project.finalproject.term.service.impl;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.exception.CompanyException;
import com.project.finalproject.company.exception.CompanyExceptionType;
import com.project.finalproject.company.repository.CompanyRepository;
import com.project.finalproject.term.dto.TermDetailResponseDTO;
import com.project.finalproject.term.dto.TermFormDTO;
import com.project.finalproject.term.dto.TermResDTO;
import com.project.finalproject.term.entity.Term;
import com.project.finalproject.term.entity.enums.TermStatus;
import com.project.finalproject.term.exception.TermException;
import com.project.finalproject.term.exception.TermExceptionType;
import com.project.finalproject.term.repository.TermRepository;
import com.project.finalproject.term.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TermServiceImpl implements TermService {

    private final TermRepository termRepository;
    private final CompanyRepository companyRepository;

    /**
     * 약관 등록
     * @param email
     * @param registerDTO
     * @return 생성한약관 상세조회
     * @throws IOException
     */
    @Override
    public TermResDTO.TermDetail registerTerm(String email, TermFormDTO.registerDTO registerDTO) throws IOException {

        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        Term createTerm = new Term().createTerm(registerDTO, company);

        return new TermResDTO.TermDetail(termRepository.save(createTerm));
    }

    /**
     * 약관 목록조회
     * @param email
     * @return 약관 목록
     */
    @Override
    public List<TermResDTO.TermList> showTermList(String email) {

        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        List<Term> terms = termRepository.findByCompanyId(company.getId());

        return terms.stream()
                .map(TermResDTO.TermList::new)
                .collect(Collectors.toList());
    }

    /**
     * 약관 상세 조회
     * @param email
     * @param termId
     * @return 약관 상세조회
     */
    @Override
    public TermResDTO.TermDetail showTermDetail(String email, Long termId) {
        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );


        Term term = termRepository.findById(termId).orElseThrow(
                () -> new TermException(TermExceptionType.NOT_FOUND_PAGE)
        );

        return TermResDTO.TermDetail.builder()
                .term(term)
                .build();
    }

    /**
     * 약관 수정
     * @param email
     * @param termId
     * @param updateDTO
     * @return 수정한약관 상세조회
     * @throws IOException
     */
    @Override
    public TermResDTO.TermDetail updateTerm(String email,Long termId,TermFormDTO.updateDTO updateDTO) throws IOException {

        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        Term term = termRepository.findById(termId).orElseThrow(
                () -> new TermException(TermExceptionType.NOT_FOUND_PAGE)
        );

        term.updateTerm(updateDTO,company);

        return new TermResDTO.TermDetail(termRepository.save(term));
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
