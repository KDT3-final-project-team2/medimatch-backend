package com.project.finalproject.calendar.repository;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.calendar.entity.Calendar;
import com.project.finalproject.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findAllByApplicant(Applicant applicant);
    List<Calendar> findAllByCompany(Company company);
    Optional<Calendar> findByIdAndApplicant(Long calendarId, Applicant applicant);
    Optional<Calendar> findByIdAndCompany(Long calendarId, Company company);
    void deleteCalendarByIdAndApplicant(Long id, Applicant applicant);
    void deleteCalendarByIdAndCompany(Long id, Company company);
    Boolean existsByIdAndApplicant(Long calendarId, Applicant applicant);
    Boolean existsByIdAndCompany(Long calendarId, Company company);

}
