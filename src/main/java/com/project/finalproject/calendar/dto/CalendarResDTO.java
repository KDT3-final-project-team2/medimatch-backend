package com.project.finalproject.calendar.dto;

import com.project.finalproject.calendar.entity.Calendar;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarResDTO {

    private Long calendarId;
    private String calendarTitle;
    private String calendarContent;
    private LocalDate calendarDate;

    public CalendarResDTO(Calendar calendar){
        this.calendarId = calendar.getId();
        this.calendarTitle = calendar.getTitle();
        this.calendarContent = calendar.getContent();
        this.calendarDate = calendar.getDate();
    }

}
