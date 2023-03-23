package com.project.finalproject.company.repository;

import com.project.finalproject.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyInfoUpdateRepository extends JpaRepository<Company, Long> {

    @Query("UPDATE Company c SET c.name = ?1, c.email = ?2, c.address = ?3, c.contact = ?4, c.password = ?5, c.regNum = ?6, c.representativeName = ?7, c.url = ?8 WHERE c.id = ?9")
    public String updateCompanyInfo(Company company);
}
