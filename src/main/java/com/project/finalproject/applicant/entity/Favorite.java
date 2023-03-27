package com.project.finalproject.applicant.entity;

import com.project.finalproject.jobpost.entity.Jobpost;

import javax.persistence.*;

@Entity
@Table(name = "tb_favorite")
public class Favorite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_serial_number")
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobpost_serial_number")
    private Jobpost jobpost;
}
