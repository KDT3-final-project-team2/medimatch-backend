package com.project.finalproject.calendar.repository;

import com.project.finalproject.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyCalendarRepository extends JpaRepository<Company, Long> {
}
