package com.project.finalproject.calendar.service;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.calendar.entity.Calendar;
import com.project.finalproject.calendar.dto.CalendarReqDTO;
import com.project.finalproject.calendar.dto.CalendarResDTO;
import com.project.finalproject.calendar.repository.ApplicantCalendarRepository;
import com.project.finalproject.calendar.repository.CalendarRepository;
import com.project.finalproject.calendar.repository.CompanyCalendarRepository;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.global.exception.GlobalException;
import com.project.finalproject.global.exception.GlobalExceptionType;
import com.project.finalproject.global.jwt.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final ApplicantCalendarRepository applicantCalendarRepository;
    private final CompanyCalendarRepository companyCalendarRepository;
    private final JwtUtil jwtUtil;

    /**
     * 일정등록
     */
    public ResponseDTO makeSchedule(CalendarReqDTO reqDTO, HttpServletRequest request){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        String role = jwtUtil.getRole(accessToken);
        Long id = jwtUtil.getId(accessToken);

        switch (role){
            case "USER":
                calendarRepository.save(Calendar.builder()
                                .title(reqDTO.getCalendarTitle())
                                .content(reqDTO.getCalendarContent())
                                .date(LocalDate.parse(reqDTO.getCalendarDate()))
                                .applicant(applicantCalendarRepository.findById(id).orElseThrow(()-> new GlobalException(GlobalExceptionType.NOT_FOUND)))
                        .build());
                return new ResponseDTO(200, true, null, "일정이 등록되었습니다.");
            case "COMPANY":
                calendarRepository.save(Calendar.builder()
                        .title(reqDTO.getCalendarTitle())
                        .content(reqDTO.getCalendarContent())
                        .date(LocalDate.parse(reqDTO.getCalendarDate()))
                        .company(companyCalendarRepository.findById(id).orElseThrow(()-> new GlobalException(GlobalExceptionType.NOT_FOUND)))
                        .build());
                return new ResponseDTO(200, true, null, "일정이 등록되었습니다.");
            default:
                return new ResponseDTO(404, false, null, "잘못된 접근입니다.");
        }
    }

    /**
     * 모든 일정 조회
     */
    public ResponseDTO allSchedules(HttpServletRequest request){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        String role = jwtUtil.getRole(accessToken);
        Long id = jwtUtil.getId(accessToken);

        List<CalendarResDTO> temp = new ArrayList<>();

        switch (role){
            case "USER":
                Applicant findApplicant = applicantCalendarRepository.findById(id).orElseThrow(()-> new GlobalException(GlobalExceptionType.NOT_FOUND));
                temp = calendarRepository.findAllByApplicant(findApplicant).stream().map(CalendarResDTO::new).collect(Collectors.toList());
                return new ResponseDTO(200, true, temp, "모든 일정입니다.");
            case "COMPANY":
                Company findCompany = companyCalendarRepository.findById(id).orElseThrow(()-> new GlobalException(GlobalExceptionType.NOT_FOUND));
                temp = calendarRepository.findAllByCompany(findCompany).stream().map(CalendarResDTO::new).collect(Collectors.toList());
                return new ResponseDTO(200, true, temp,"모든 일정입니다.");
            default:
                return new ResponseDTO(404, false, null, "잘못된 접근입니다.");
        }
    }

    /**
     * 일정수정
     */
    public ResponseDTO reviseSchedule(Long calendarId, CalendarReqDTO reqDTO, HttpServletRequest request){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        String role = jwtUtil.getRole(accessToken);
        Long id = jwtUtil.getId(accessToken);

        switch (role){
            case "USER":
                Applicant findApplicant = applicantCalendarRepository.findById(id).orElseThrow(()->new GlobalException(GlobalExceptionType.NOT_FOUND));
                calendarRepository.findByIdAndApplicant(calendarId, findApplicant).map(calendar -> {calendar.setTitle(reqDTO.getCalendarTitle());
                    calendar.setContent(reqDTO.getCalendarContent());
                    calendar.setDate(LocalDate.parse(reqDTO.getCalendarDate()));
                    return calendarRepository.save(calendar);}).orElseThrow(()->new GlobalException(GlobalExceptionType.NOT_FOUND));
                return new ResponseDTO(200, true, null,"일정이 수정되었습니다.");
            case "COMPANY":
                Company findCompany = companyCalendarRepository.findById(id).orElseThrow(()->new GlobalException(GlobalExceptionType.NOT_FOUND));
                calendarRepository.findByIdAndCompany(calendarId, findCompany).map(calendar -> {calendar.setTitle(reqDTO.getCalendarTitle());
                    calendar.setContent(reqDTO.getCalendarContent());
                    calendar.setDate(LocalDate.parse(reqDTO.getCalendarDate()));
                    return calendarRepository.save(calendar);}).orElseThrow(()->new GlobalException(GlobalExceptionType.NOT_FOUND));
                return new ResponseDTO(200, true, null,"일정이 수정되었습니다.");
            default:
                return new ResponseDTO(404, true, null, "잘못된 접근입니다.");
        }
    }

    /**
     * 일정삭제
     */
    public ResponseDTO deleteSchedule(Long calendarId, HttpServletRequest request){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = jwtUtil.extractToken(authorizationHeader);
        String role = jwtUtil.getRole(accessToken);
        Long id = jwtUtil.getId(accessToken);

        switch (role){
            case "USER":
                Applicant findApplicant = applicantCalendarRepository.findById(id).orElseThrow(()->new GlobalException(GlobalExceptionType.NOT_FOUND));
                if(!calendarRepository.existsByIdAndApplicant(calendarId, findApplicant)){
                    return new ResponseDTO(404, false, null, "사용자가 등록한 일정이 아닙니다.");
                }
                calendarRepository.deleteCalendarByIdAndApplicant(calendarId, findApplicant);
                return new ResponseDTO(200, true, null, "일정이 삭제되었습니다.");
            case "COMPANY":
                Company findCompany = companyCalendarRepository.findById(id).orElseThrow(()->new GlobalException(GlobalExceptionType.NOT_FOUND));
                if(!calendarRepository.existsByIdAndCompany(calendarId, findCompany)){
                    return new ResponseDTO(404, false, null, "사용자가 등록한 일정이 아닙니다.");
                }
                calendarRepository.deleteCalendarByIdAndCompany(calendarId, findCompany);
                return new ResponseDTO(200, true, null, "일정이 삭제되었습니다.");
            default:
                return new ResponseDTO(404, false, null, "잘못된 접근입니다.");
        }
    }

}
