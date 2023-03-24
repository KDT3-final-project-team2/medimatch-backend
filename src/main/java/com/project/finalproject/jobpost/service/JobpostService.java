package com.project.finalproject.jobpost.service;

import com.project.finalproject.jobpost.dto.JobpostResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobpostService {

    List<JobpostResponseDTO.ShortDTO> showJobpostList();

}
