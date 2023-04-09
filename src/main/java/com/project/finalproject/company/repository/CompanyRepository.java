package com.project.finalproject.company.repository;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.entity.enums.CompanyType;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository< Company, Long > {

    Optional<Company> findByEmail(String email);

    @Query("SELECT c FROM Company c WHERE c.companyType = 'COMPANY'")
    List<Company> findByCompanyType(@Param("companyType") CompanyType companyType);

    @Query("SELECT a.birthDate, a.gender, a.education, j.title " +
            "FROM Application ap " +
            "INNER JOIN Jobpost j ON ap.jobpost.id = j.id " +
            "INNER JOIN Company c ON j.company.id = c.id " +
            "INNER JOIN Applicant a ON ap.applicant.id = a.id " +
            "WHERE c.id = :companyId")
    List<Object[]> findApplicationsForCompany(@Param("companyId") Long companyId);

    @Query("select count(c) from Company c where c.companyType = :companyType")
    long countAllByCompanyType(CompanyType companyType);
    @Query("select count(c) from Company c where c.companyType = :companyType and c.signupDate between :startDate and :endDate and year(c.signupDate) = :year")
    long countAllBySignupDateBetweenAndSignupDateYearAndCompanyType(@Param("companyType") CompanyType companyType, @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("year") int year);
    @Query("select count(c) from Company c where c.companyType = :companyType and c.signupDate between :startDate and :endDate and year(c.signupDate) = :year and month(c.signupDate) = :month")
    long countAllBySignupDateBetweenAndSignupDateYearAndSignupDateMonthAndCompanyType(@Param("companyType") CompanyType companyType,@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("year")int year, @Param("month")int month);
    @Query("select count(c) from Company c where c.companyType = :companyType and c.signupDate between :startDate and :endDate and year(c.signupDate) = :year and month(c.signupDate) = :month and day(c.signupDate) = :day")
    long countAllBySignupDateBetweenAndSignupDateYearAndSignupDateMonthAndSignupDateDayOfMonthAndCompanyType(@Param("companyType") CompanyType companyType,@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("year")int year, @Param("month")int month, @Param("day")int day);

    @Query("SELECT COUNT(c.id) FROM Company c WHERE c.companyType = :companyType and year(c.signupDate) = :year and month(c.signupDate) = :month")
    long countByJoinDateMonth(@Param("companyType") CompanyType companyType, @Param("year")int year, @Param("month") int month);

}
