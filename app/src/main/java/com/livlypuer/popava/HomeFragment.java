package com.livlypuer.popava;

import android.media.Image;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.livlypuer.popava.db.DBManager;
import com.livlypuer.popava.elems.EventsCard;
import com.livlypuer.popava.models.Course;
import com.livlypuer.popava.models.EventModel;
import com.livlypuer.popava.models.Timetable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends Fragment {
    DBManager mDBConnector;
    LinearLayout eventsCont;
    ImageView nowCourseImage;
    TextView nowCourseTitle;
    TextView nowCourseWeek;
    TextView nowCourseTime;
    HashMap<String, String> months = new HashMap<>();
    String[] week = new String[]{"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
    private Object EventComparator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        months.put("JANUARY", "ЯНВАРЯ");
        months.put("FEBRUARY", "ФЕВРАЛЯ");
        months.put("MARCH", "МАРТА");
        months.put("APRIL", "АПРЕЛЯ");
        months.put("MAY", "МАЯ");
        months.put("JUNE", "ИЮНЯ");
        months.put("JULY", "ИЮЛЯ");
        months.put("AUGUST", "АВГУСТА");
        months.put("SEPTEMBER", "СЕНТЯБРЯ");
        months.put("OCTOBER", "ОКТЯБРЯ");
        months.put("NOVEMBER", "НОЯБРЯ");
        months.put("DECEMBER", "ДЕКАБРЯ");

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        nowCourseImage = view.findViewById(R.id.nowCourseImage);
        nowCourseTitle = view.findViewById(R.id.nowCourseTitle);
        nowCourseWeek = view.findViewById(R.id.nowCourseWeek);
        nowCourseTime = view.findViewById(R.id.nowCourseTime);
        mDBConnector = new DBManager(getActivity());

        LocalDate date = LocalDate.now();
        int dayOfWeek = date.get(ChronoField.DAY_OF_WEEK) - 1;
        nowCourseWeek.setText(week[dayOfWeek]);
        List<Course> courses = mDBConnector.selectStudentData(true).getCourses();
        for (int index = 0; index < courses.size(); index++) {
            Course course = courses.get(index);
            Timetable timetable = course.getTimetable();
            Log.d("MY", "DOW " + timetable.isWeek(dayOfWeek));
            if (timetable.isWeek(dayOfWeek)) {
                nowCourseImage.setImageBitmap(course.getBitmapImage());
                nowCourseTitle.setText(course.getTitle());

                nowCourseTime.setText(""+ timetable.time_mon.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + "\n" + timetable.time_end_mon.format(DateTimeFormatter.ofPattern(Timetable.PATTERN)));
            }
        }
        eventsCont = view.findViewById(R.id.event_cont);
        ArrayList<EventModel> events = mDBConnector.selectAllOpenEvents();
        events.sort(new EventComparator());
        Collections.reverse(events);
        for (int i = 0; i < events.size(); i++) {
            EventsCard ec = new EventsCard(view.getContext());
            ec.SetDayText(Integer.toString(events.get(i).getDate().getDayOfMonth()));
            ec.SetMonthText(months.get(events.get(i).getDate().getMonth().toString()));
            ec.SetTitleText(events.get(i).getTitle());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dp130));
            lp.setMargins(
                    getResources().getDimensionPixelSize(R.dimen.dp10),
                    getResources().getDimensionPixelSize(R.dimen.dp10),
                    getResources().getDimensionPixelSize(R.dimen.dp10),
                    0
            );
            ec.setLayoutParams(lp);
            ec.SetDescriptionText(events.get(i).getDescription());
            eventsCont.addView(ec);
        }
        return view;
    }


}