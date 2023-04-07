package com.project.finalproject.password.repository;

import com.project.finalproject.applicant.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantPasswordRepository extends JpaRepository<Applicant, Long> {

    Optional<Applicant> findApplicantByEmailAndName(String email, String name);
    Optional<Applicant> findApplicantById(Long id);
    Boolean existsApplicantByEmailAndName(String email, String name);
}
