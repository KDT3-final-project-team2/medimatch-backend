package com.project.finalproject.login.signup.applicant;

import com.project.finalproject.applicant.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantSignupRepository extends JpaRepository<Applicant, Long> {

    Optional<Applicant> findApplicantByEmail(String email);
    Boolean existsApplicantByEmail(String email);
    void deleteApplicantByEmail(String email);
}
