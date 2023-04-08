package com.project.finalproject.calendar.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarReqDTO {

    private String calendarTitle;
    private String calendarContent;
    private String calendarDate;

}
