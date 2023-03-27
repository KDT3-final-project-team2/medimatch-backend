package com.project.finalproject.login.repository;

import com.project.finalproject.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyLoginRepository extends JpaRepository<Company, Long> {
    Optional<Company> findCompanyByEmail(String email);
}
