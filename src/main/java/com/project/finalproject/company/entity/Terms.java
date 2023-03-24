package com.project.finalproject.company.entity;

import com.project.finalproject.company.entity.enums.TermsStatus;
import com.project.finalproject.company.entity.enums.TermsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

//기업 약관
@Entity
@Builder
@Table(name = "terms")
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor
@AllArgsConstructor
public class Terms {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_serial_number")
    private Long id; //PK

    @Column(name = "terms_type")
    @Enumerated(EnumType.STRING)
    private TermsType type; //약관 타입

    @Column(name = "terms_version")
    private String version; //약관 버전

    @CreatedDate
    @Column(name = "terms_create_date",updatable = false)
    private LocalDateTime createDate; //약관 생성 날짜

    @LastModifiedDate
    @Column(name = "terms_edit_date")
    private LocalDateTime editDate; //약관 수정 날짜

    @Column(name = "terms_title")
    private String title; //약관 제목

    @Column(name = "terms_contents", columnDefinition = "TEXT")
    private String contents; //약관 내용

    @Column(name = "terms_status")
    @Enumerated(EnumType.STRING)
    private TermsStatus termsStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_serial_number")
    private Company company; //약관을 작성한 기업

}
