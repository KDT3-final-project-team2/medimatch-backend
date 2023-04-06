package com.project.finalproject.resign.repository;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationResigngRepository extends JpaRepository<Application, Long> {

    void deleteApplicationByApplicant(Applicant applicant);

}
