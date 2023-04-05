package com.project.finalproject.company.repository;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.entity.enums.CompanyType;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository< Company, Long > {

    Optional<Company> findByEmail(String email);

    @Query("SELECT c FROM Company c WHERE c.companyType = 'COMPANY'")
    List<Company> findByCompanyType(@Param("companyType") CompanyType companyType);

}
