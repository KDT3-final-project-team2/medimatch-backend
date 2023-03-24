package com.project.finalproject.admin.repository;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Applicant, Long> {

// get all information of all applicants
    @Query (value = "SELECT * FROM tb_applicant", nativeQuery = true)
    public List<Applicant> findAllApplicant();

// get all information of all companies
    @Query (value = "SELECT * FROM company", nativeQuery = true)
    public List<Company> findAllCompany();
}
