package com.project.finalproject.jobpost.service.impl;

import com.project.finalproject.jobpost.repository.JobpostRepository;
import com.project.finalproject.jobpost.service.JobpostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobpostServiceImpl implements JobpostService {

    private final JobpostRepository jobpostRepository;

    @Override
    public String deleteJobpost(Long jobpostId) {

        jobpostRepository.deleteById(jobpostId);
        return "success";
    }
}
