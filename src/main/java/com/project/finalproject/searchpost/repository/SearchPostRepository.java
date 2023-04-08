package com.project.finalproject.searchpost.repository;

import com.project.finalproject.applicant.entity.enums.Sector;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.jobpost.entity.Jobpost;
import com.project.finalproject.jobpost.entity.enums.JobpostEducation;
import com.project.finalproject.jobpost.entity.enums.JobpostWorkExperience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SearchPostRepository extends JpaRepository<Jobpost, Long> {

    Page<Jobpost> findAll(Pageable pageable);
    Page<Jobpost> findAllBySector(Sector sector, Pageable pageable);
    Page<Jobpost> findAllByEducation(JobpostEducation education, Pageable pageable);
    Page<Jobpost> findAllByExperience(JobpostWorkExperience experience, Pageable pageable);
    Page<Jobpost> findAllByTitleContains(String title, Pageable pageable);
    Page<Jobpost> findAllByCompany(Company company, Pageable pageable);

    @Override
    Optional<Jobpost> findById(Long postId);
}
