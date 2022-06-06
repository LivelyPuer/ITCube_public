package com.livlypuer.popava.models;

import android.util.Log;

import java.sql.Array;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Timetable extends BaseModel {
    //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m:s")
//            .withZone(ZoneId.systemDefault());
//    Instant instant = Instant.parse("2022-02-15T08:35:24.00Z");
//    String formattedInstant = formatter.format(instant);
    public static String PATTERN = "HH:mm:ss";
    public Boolean mon;
    public Boolean tue;
    public Boolean web;
    public Boolean thu;
    public Boolean fri;
    public Boolean sat;
    public Boolean sun;
    public LocalTime time_mon;
    public LocalTime time_tue;
    public LocalTime time_web;
    public LocalTime time_thu;
    public LocalTime time_fri;
    public LocalTime time_sat;
    public LocalTime time_sun;
    public LocalTime time_end_mon;
    public LocalTime time_end_tue;
    public LocalTime time_end_web;
    public LocalTime time_end_thu;
    public LocalTime time_end_fri;
    public LocalTime time_end_sat;
    public LocalTime time_end_sun;

    public Timetable(long id, Boolean mon, Boolean tue, Boolean web, Boolean thu, Boolean fri, Boolean sat, Boolean sun,
                     LocalTime time_mon,
                     LocalTime time_tue,
                     LocalTime time_web,
                     LocalTime time_thu,
                     LocalTime time_fri,
                     LocalTime time_sat,
                     LocalTime time_sun,
                     LocalTime time_end_mon,
                     LocalTime time_end_tue,
                     LocalTime time_end_web,
                     LocalTime time_end_thu,
                     LocalTime time_end_fri,
                     LocalTime time_end_sat,
                     LocalTime time_end_sun) {
        super(id);
        this.mon = mon;
        this.tue = tue;
        this.web = web;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        this.time_mon = time_mon;
        this.time_tue = time_tue;
        this.time_web = time_web;
        this.time_thu = time_thu;
        this.time_fri = time_fri;
        this.time_sat = time_sat;
        this.time_sun = time_sun;
        this.time_end_mon = time_end_mon;
        this.time_end_tue = time_end_tue;
        this.time_end_web = time_end_web;
        this.time_end_thu = time_end_thu;
        this.time_end_fri = time_end_fri;
        this.time_end_sat = time_end_sat;
        this.time_end_sun = time_end_sun;
    }

    public Timetable(long id, Boolean[] weeks, LocalTime[] times, LocalTime[] endTimes) {
        super(id);
        this.mon = weeks[0];
        this.tue = weeks[1];
        this.web = weeks[2];
        this.thu = weeks[3];
        this.fri = weeks[4];
        this.sat = weeks[5];
        this.sun = weeks[6];
        this.time_mon = times[0];
        this.time_tue = times[1];
        this.time_web = times[2];
        this.time_thu = times[3];
        this.time_fri = times[4];
        this.time_sat = times[5];
        this.time_sun = times[6];
        this.time_end_mon = endTimes[0];
        this.time_end_tue = endTimes[1];
        this.time_end_web = endTimes[2];
        this.time_end_thu = endTimes[3];
        this.time_end_fri = endTimes[4];
        this.time_end_sat = endTimes[5];
        this.time_end_sun = endTimes[6];
    }

    public Boolean[] GetWeeks() {
        Boolean[] weeks = new Boolean[7];
        weeks[0] = this.mon;
        weeks[1] = this.tue;
        weeks[2] = this.web;
        weeks[3] = this.thu;
        weeks[4] = this.fri;
        weeks[5] = this.sat;
        weeks[6] = this.sun;
        return weeks;
    }
    public Boolean isWeek(int weekOfDay){
        Boolean[] weeks = GetWeeks();
        Log.d("MY", weekOfDay + " " + weeks[weekOfDay]);
        return weeks[weekOfDay];
    }

    public LocalTime[] GetTimes() {
        LocalTime[] weeks = new LocalTime[7];
        weeks[0] = this.time_mon;
        weeks[1] = this.time_tue;
        weeks[2] = this.time_web;
        weeks[3] = this.time_thu;
        weeks[4] = this.time_fri;
        weeks[5] = this.time_sat;
        weeks[6] = this.time_sun;
        return weeks;
    }
    public LocalTime[] GetEndTimes() {
        LocalTime[] weeks = new LocalTime[7];
        weeks[0] = this.time_end_mon;
        weeks[1] = this.time_end_tue;
        weeks[2] = this.time_end_web;
        weeks[3] = this.time_end_thu;
        weeks[4] = this.time_end_fri;
        weeks[5] = this.time_end_sat;
        weeks[6] = this.time_end_sun;
        return weeks;
    }


}
