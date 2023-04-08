package com.project.finalproject.calendar.repository;

import com.project.finalproject.applicant.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantCalendarRepository extends JpaRepository<Applicant, Long> {
}
