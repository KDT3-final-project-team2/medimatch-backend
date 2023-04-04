package com.project.finalproject.login.repository;

import com.project.finalproject.applicant.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantLoginRepository extends JpaRepository<Applicant, Long> {

    Optional<Applicant> findApplicantByEmail(String email);
    Boolean existsApplicantByEmail(String email);
}
