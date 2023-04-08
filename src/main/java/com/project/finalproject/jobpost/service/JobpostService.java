package com.project.finalproject.jobpost.service;

import com.project.finalproject.login.dto.LoginResDTO;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Transactional
public interface JobpostService {

    //채용공고 삭제
    public String deleteJobpost(Long jobpostId);
}
