package com.project.finalproject.application.entity.repository;

import com.project.finalproject.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByApplicantIdAndJobpostId(Long applicantId, Long jobpostId);
}
