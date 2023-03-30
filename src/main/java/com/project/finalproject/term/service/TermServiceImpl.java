package com.project.finalproject.term.service;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.repository.CompanyRepository;
import com.project.finalproject.term.dto.TermFormDTO;
import com.project.finalproject.term.entity.Term;
import com.project.finalproject.term.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class TermServiceImpl implements TermService{

    private final TermRepository termRepository;
    private final CompanyRepository companyRepository;

    /**
     * 약관 등록
     */
    @Override
    public Long register(TermFormDTO termFormDTO, String adminEmail) {

        Optional<Company> company = companyRepository.findByEmail(adminEmail);

        if (company.isEmpty()) {
            return null;
        } else {
            Term term = Term.createTerm(termFormDTO,company.get());
            termRepository.save(term);

            return term.getId();
        }
    }

}
