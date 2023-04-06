package com.project.finalproject.resign.repository;

import com.project.finalproject.applicant.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantResignRepository extends JpaRepository<Applicant, Long> {

    Optional<Applicant> findApplicantById(Long id);

}
