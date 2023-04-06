package com.project.finalproject.password.repository;

import com.project.finalproject.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyPasswordRepository extends JpaRepository<Company, Long> {

    Optional<Company> findCompanyByEmailAndName(String email, String name);
    Optional<Company> findCompanyById(Long id);
    Boolean existsCompanyByEmailAndName(String email, String name);
}
