package com.project.finalproject.term.entity;

import com.project.finalproject.company.entity.Company;
import com.project.finalproject.term.entity.enums.TermStatus;
import com.project.finalproject.term.entity.enums.TermType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 약관
 */
@Entity
@Table(name = "tb_term")
public class Term {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private Long id; //PK

    @Column(name = "term_content", columnDefinition = "TEXT")
    private String content; //약관 내용

    @Column(name = "term_type")
    @Enumerated(EnumType.STRING)
    private TermType type; //약관 타입

    @Column(name = "term_version")
    private String version; //약관 버전

    @CreatedDate
    @Column(name = "term_create_date",updatable = false)
    private LocalDateTime createDate; //약관 생성 날짜

    @LastModifiedDate
    @Column(name = "term_edit_date")
    private LocalDateTime editDate; //약관 수정 날짜

    @Column(name = "term_status")
    @Enumerated(EnumType.STRING)
    private TermStatus status;  // 약관상태(사용,임시저장,폐기)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

}
