package com.project.finalproject.login.signup.company;

import com.project.finalproject.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanySignupRepository extends JpaRepository<Company, Long> {

    Optional<Company> findCompanyByEmail(String email);
    Boolean existsCompanyByEmail(String email);
    void deleteCompanyByEmail(String email);
}
