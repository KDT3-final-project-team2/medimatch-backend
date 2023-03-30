package com.project.finalproject.jobpost.repository;

import com.project.finalproject.jobpost.entity.Jobpost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobpostRepository extends JpaRepository< Jobpost, Long > {
}
