package com.project.finalproject.searchpost.repository;

import com.project.finalproject.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchCompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findCompanyByName(String name);
}
