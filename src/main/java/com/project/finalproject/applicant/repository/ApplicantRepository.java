package com.project.finalproject.applicant.repository;

import com.project.finalproject.applicant.entity.Applicant;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    Optional<Applicant> findByEmailAndPassword(String id, String password);

    Optional<Applicant> findByEmail(String email);

    @Query("select count(a) from Applicant a where a.signUpDate between :startDate and :endDate and year(a.signUpDate) = :year")
    long countAllBySignupDateBetweenAndSignupDateYear(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("year") int year);
    @Query("select count(a) from Applicant a where a.signUpDate between :startDate and :endDate and year(a.signUpDate) = :year and month(a.signUpDate) = :month")
    long countAllBySignupDateBetweenAndSignupDateYearAndSignupDateMonth(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("year")int year, @Param("month")int month);
    @Query("select count(a) from Applicant a where a.signUpDate between :startDate and :endDate and year(a.signUpDate) = :year and month(a.signUpDate) = :month and day(a.signUpDate) = :day")
    long countAllBySignupDateBetweenAndSignupDateYearAndSignupDateMonthAndSignupDateDayOfMonth(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("year")int year, @Param("month")int month, @Param("day")int day);

    @Query("SELECT COUNT(a.id) FROM Applicant a WHERE year(a.signUpDate) = :year and month(a.signUpDate) = :month")
    long countByJoinDateMonth(@Param("year")int year,@Param("month") int month);
}
