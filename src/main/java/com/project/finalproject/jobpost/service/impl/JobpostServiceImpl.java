package com.project.finalproject.jobpost.service.impl;

import com.project.finalproject.jobpost.dto.JobpostResponseDTO;
import com.project.finalproject.jobpost.entity.Jobpost;
import com.project.finalproject.jobpost.repository.JobpostRepository;
import com.project.finalproject.jobpost.service.JobpostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobpostServiceImpl implements JobpostService {

    private final JobpostRepository jobpostRepository;

    @Override
    public List<JobpostResponseDTO.ShortDTO> showJobpostList() {
        List<Jobpost> jobposts = jobpostRepository.findAll();

        return jobposts.stream()
                .map(JobpostResponseDTO.ShortDTO::new)
                .collect(Collectors.toList());
    }
}
