package com.project.finalproject.company.service.impl;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.repository.ApplicantRepository;
import com.project.finalproject.company.dto.AdminApplicantRes;
import com.project.finalproject.company.dto.AdminCompanyRes;
import com.project.finalproject.company.entity.enums.CompanyType;
import com.project.finalproject.company.repository.CompanyRepository;
import com.project.finalproject.company.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CompanyRepository companyRepository;
    private final ApplicantRepository applicantRepository;

    /**
     * 기업회원 목록조회
     * @param companyType 'COMPANY'
     * @return 기업회원 목록
     */
    @Transactional(readOnly = true)
    @Override
    public List<AdminCompanyRes.CompanyListDTO> showCompanyList(CompanyType companyType) {
        return companyRepository.findByCompanyType(companyType).stream()
                .map(AdminCompanyRes.CompanyListDTO::new).collect(Collectors.toList());
    }

    /**
     * 개인회원 목록조회
     * @return 개인회원 목록
     */
    @Transactional(readOnly = true)
    @Override
    public List<AdminApplicantRes.ApplicantListDTO> showApplicantList() {
        List<Applicant> applicants = applicantRepository.findAll();
        return applicants.stream()
                .map(AdminApplicantRes.ApplicantListDTO::new).collect(Collectors.toList());
    }

    /**
     * Total 기업회원 수
     * @param companyType 'COMPANY'
     * @return 기업회원 total 수
     */
    @Transactional(readOnly = true)
    @Override
    public long getTotalCompanyCount(CompanyType companyType) {
        return companyRepository.countAllByCompanyType(CompanyType.COMPANY);
    }

    /**
     * 당해연도 기업회원 수
     * @param companyType
     * @return 당해연도 기업회원 수
     */
    @Transactional(readOnly = true)
    @Override
    public long getYearlyCompanyCount(CompanyType companyType) {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(),Month.JANUARY,1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), Month.DECEMBER, 31);
        return companyRepository.countAllBySignupDateBetweenAndSignupDateYearAndCompanyType(CompanyType.COMPANY,startDate, endDate,LocalDate.now().getYear());
    }

    /**
     * 당월 기업회원 수
     * @param companyType 'COMPANY'
     * @return 당월 기업회원 수
     */
    @Transactional(readOnly = true)
    @Override
    public long getMonthlyCompanyCount(CompanyType companyType) {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().lengthOfMonth());
        return companyRepository.countAllBySignupDateBetweenAndSignupDateYearAndSignupDateMonthAndCompanyType(CompanyType.COMPANY,startDate, endDate, LocalDate.now().getYear(), LocalDate.now().getMonthValue());
    }

    /**
     * 당일 기업회원 수
     * @param companyType 'COMPANY'
     * @return 당일 기업회원 수
     */
    @Transactional(readOnly = true)
    @Override
    public long getDailyCompanyCount(CompanyType companyType) {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth());
        LocalDate endDate = LocalDate.now().plusDays(1);
        return companyRepository.countAllBySignupDateBetweenAndSignupDateYearAndSignupDateMonthAndSignupDateDayOfMonthAndCompanyType(CompanyType.COMPANY,startDate, endDate, LocalDate.now().getYear(), LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth());
    }

    /**
     * 기업회원 당해연도 월별 인원 수
     * @param companyType 'COMPANY'
     * @param month
     * @return 기업회원 월별 인원 수
     */
    @Transactional(readOnly = true)
    @Override
    public long getMonthlyCompanyCountByJoinDateMonth(CompanyType companyType, int month) {
        LocalDate now = LocalDate.now();
        return companyRepository.countByJoinDateMonth(CompanyType.COMPANY,now.getYear(), month);
    }

    /**
     * 개인회원 total
     * @return 개인회원 Total 수
     */
    @Transactional(readOnly = true)
    @Override
    public long getTotalApplicantCount() {
        return applicantRepository.count();
    }

    /**
     * 개인회원 당해연도 가입인원
     * @return 개인회원 당해연도 가입인원
     */
    @Transactional(readOnly = true)
    @Override
    public long getYearlyApplicantCount() {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), Month.JANUARY,1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), Month.DECEMBER, 31);
        return applicantRepository.countAllBySignupDateBetweenAndSignupDateYear(startDate, endDate,LocalDate.now().getYear());
    }

    /**
     * 개인회원 당월 가입인원
     * @return 개인회원 당월 가입인원
     */
    @Transactional(readOnly = true)
    @Override
    public long getMonthlyApplicantCount() {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().lengthOfMonth());
        return applicantRepository.countAllBySignupDateBetweenAndSignupDateYearAndSignupDateMonth(startDate, endDate, LocalDate.now().getYear(), LocalDate.now().getMonthValue());
    }

    /**
     * 개인회원 당일 가입인원
     * @return 개인회원 당일 가입인원
     */
    @Transactional(readOnly = true)
    @Override
    public long getDailyApplicantCount() {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth());
        LocalDate endDate = LocalDate.now().plusDays(1);
        return applicantRepository.countAllBySignupDateBetweenAndSignupDateYearAndSignupDateMonthAndSignupDateDayOfMonth(startDate, endDate, LocalDate.now().getYear(), LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth());
    }

    /**
     * 개인회원 월별 가입인원
     * @param month
     * @return 개인회원 월별 가입인원
     */
    @Transactional(readOnly = true)
    @Override
    public long getMonthlyApplicantCountByJoinDateMonth(int month) {
        LocalDate now = LocalDate.now();
        return applicantRepository.countByJoinDateMonth(now.getYear(), month);
    }

    /**
     * 개인회원 직무별인원
     * @return 개인회원 직무별인원
     */
    public Map<String, Long> getSectorCount() {
        List<Applicant> applicants = applicantRepository.findAll();

        Map<String, Long> sectorCount = new HashMap<>();
        for (Applicant applicant : applicants) {
            String sector = applicant.getSector().toString();
            sectorCount.put(sector, sectorCount.getOrDefault(sector, 0L) + 1);
        }

        return sectorCount;
    }

}
