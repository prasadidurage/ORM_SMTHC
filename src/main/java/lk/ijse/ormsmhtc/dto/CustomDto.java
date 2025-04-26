package lk.ijse.ormsmhtc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Setter
public class CustomDto {
    private String day;
    private String availableTimeList;

    private String therapistId;
    private String Day;
    public LocalTime start;
    public LocalTime end;
    public Time startTime;
    public Time endTime;
    public Date date;
    public String time;

    public java.util.Date dates;
    public long count;

    public CustomDto(java.util.Date dates, long count) {
        this.dates = dates;
        this.count = count;
    }

    public CustomDto(Date date, String time) {
        this.date = date;
        this.time = time;
    }

    public CustomDto(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public CustomDto(String time, Date date) {
        this.time = time;
        this.date = date;
    }

    public CustomDto(String day, String availableTimeList) {
        this.day = day;
        this.availableTimeList = availableTimeList;
    }

    public CustomDto(LocalTime end, LocalTime start, String day, String therapistId) {
        this.end = end;
        this.start = start;
        this.Day = day;
        this.therapistId = therapistId;
    }

    public CustomDto(LocalTime start, LocalTime end, String day) {
        this.start = start;
        this.end = end;
        this.day = day;
    }
    public CustomDto(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return start.format(formatter) + " - " + end.format(formatter);
    }

//    public String getDay() {
//        // You can implement a method to return the day of the week if needed
//        return new SimpleDateFormat("EEEE").format(date);
//    }
}
