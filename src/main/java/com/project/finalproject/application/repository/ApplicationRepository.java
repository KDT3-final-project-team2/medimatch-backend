package com.project.finalproject.application.repository;

import com.project.finalproject.application.entity.Application;
import com.project.finalproject.company.entity.Company;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByApplicantIdAndJobpostId(Long applicantId, Long jobpostId);

    List<Application> findByApplicantId(Long applicantId);

    @Query("SELECT a.birthDate, a.gender, a.education, j.title " +
            "FROM Application ap " +
            "INNER JOIN Jobpost j ON ap.jobpost.id = j.id " +
            "INNER JOIN Company c ON j.company.id = c.id " +
            "INNER JOIN Applicant a ON ap.applicant.id = a.id " +
            "WHERE c.id = :companyId")
    List<Company> findByCompanyId(@Param("companyId") Long companyId);
}
