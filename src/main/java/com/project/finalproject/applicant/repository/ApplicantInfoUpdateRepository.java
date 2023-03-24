package com.project.finalproject.applicant.repository;

import com.project.finalproject.applicant.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantInfoUpdateRepository extends JpaRepository<Applicant, Long> {

    @Query("UPDATE Applicant a SET a.name = ?1, a.birthDate = ?2, a.contact = ?3, a.education = ?4, a.workExperience = ?5, a.sector = ?6 WHERE a.id = ?7")
    String updateApplicantInfo(Applicant applicant);
}
