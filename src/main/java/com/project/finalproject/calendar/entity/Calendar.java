package com.project.finalproject.calendar.entity;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.company.entity.Company;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@Table(name = "tb_calendar")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id", nullable = false, unique = true)
    private Long id; //pk

    @Column(name = "calendar_title")
    private String title;

    @Column(name = "calendar_content")
    private String content;

    @Column(name = "calendar_date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}
