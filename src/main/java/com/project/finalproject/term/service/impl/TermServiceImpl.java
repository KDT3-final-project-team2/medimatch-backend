package com.project.finalproject.term.service.impl;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.repository.CompanyRepository;
import com.project.finalproject.term.dto.TermResDTO;
import com.project.finalproject.term.entity.Term;
import com.project.finalproject.term.repository.TermRepository;
import com.project.finalproject.term.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

}
