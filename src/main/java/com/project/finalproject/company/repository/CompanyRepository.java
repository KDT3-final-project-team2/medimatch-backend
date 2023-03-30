package com.project.finalproject.company.repository;

import com.project.finalproject.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository< Company, Long > {

    Optional<Company> findByEmail(String email);
}
