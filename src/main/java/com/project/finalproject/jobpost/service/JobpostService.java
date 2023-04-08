package com.project.finalproject.jobpost.service;

import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface JobpostService {

    //채용공고 삭제
    public String remove(Long postId);
}
