package com.project.finalproject.jobpost.repository;

import com.project.finalproject.jobpost.entity.Jobpost;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobpostRepository extends JpaRepository< Jobpost, Long > {

    @Query("SELECT jp FROM Jobpost jp WHERE jp.company.id = :companyId")
    List<Jobpost> findByCompanyId(@Param("companyId") Long companyId);

}
