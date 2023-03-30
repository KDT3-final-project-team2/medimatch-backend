package com.project.finalproject.term.service;

import com.project.finalproject.term.dto.TermFormDTO;
import com.project.finalproject.term.entity.Term;

import javax.transaction.Transactional;

@Transactional
public interface TermService {

    Long register(TermFormDTO termDTO,String adminEmail);


}
