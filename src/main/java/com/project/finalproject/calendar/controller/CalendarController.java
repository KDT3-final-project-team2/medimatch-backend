package com.project.finalproject.calendar.controller;

import com.project.finalproject.calendar.dto.CalendarReqDTO;
import com.project.finalproject.calendar.service.CalendarService;
import com.project.finalproject.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    /**
     * 일정등록
     */
    @PostMapping("/calendar")
    public ResponseDTO makeSchedule(@RequestBody CalendarReqDTO reqDTO, HttpServletRequest request){
        return calendarService.makeSchedule(reqDTO, request);
    }

    /**
     * 모든 일정 조회
     */
    @GetMapping("/calendar")
    public ResponseDTO allSchedules(HttpServletRequest request){
        return calendarService.allSchedules(request);
    }

    /**
     * 일정수정
     */
    @PutMapping("/calendar/{calendarId}")
    public ResponseDTO reviseSchedule(@PathVariable Long calendarId,
                                      @RequestBody CalendarReqDTO reqDTO,
                                      HttpServletRequest request){
        return calendarService.reviseSchedule(calendarId, reqDTO, request);
    }

    /**
     * 일정삭제
     */
    @DeleteMapping("/calendar/{calendarId}")
    public ResponseDTO deleteSchedule(@PathVariable Long calendarId,
                                      HttpServletRequest request){
        return calendarService.deleteSchedule(calendarId, request);
    }


}
