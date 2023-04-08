package com.project.finalproject.jobpost.service.impl;

import com.project.finalproject.jobpost.entity.Jobpost;
import com.project.finalproject.jobpost.repository.JobpostRepository;
import com.project.finalproject.jobpost.service.JobpostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class JobpostServiceImpl implements JobpostService {

    private final JobpostRepository jobpostRepository;

    @Override
    public String remove(Long postId) {

        Jobpost jobpost = jobpostRepository.findById(postId).orElse(null);

        String filePath = jobpost.getFilepath();

        jobpostRepository.deleteById(postId);

        File post = new File(filePath);

        post.delete();

        return "success";
    }
}
