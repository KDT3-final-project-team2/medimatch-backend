package com.project.finalproject.term.repository;

import com.project.finalproject.term.entity.Term;
import com.project.finalproject.term.entity.enums.TermStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermRepository extends JpaRepository<Term, Long> {

    @Query("SELECT t FROM Term t WHERE t.company.id = :companyId")
    List<Term> findByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT t FROM Term t WHERE t.company.id = :companyId AND t.status = :status")
    List<Term> findByCompanyIdAndStatus(@Param("companyId") Long companyId, @Param("status") TermStatus status);

}
