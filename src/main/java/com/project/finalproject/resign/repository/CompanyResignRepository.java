package com.project.finalproject.resign.repository;

import com.project.finalproject.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyResignRepository extends JpaRepository<Company, Long> {

    Optional<Company> findCompanyById(Long id);

}
