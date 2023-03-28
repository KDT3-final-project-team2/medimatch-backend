package com.project.finalproject.applicant.repository;

import com.project.finalproject.applicant.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    Optional<Applicant> findByEmailAndPassword(String id, String password);

    Optional<Applicant> findByEmail(String email);
}
