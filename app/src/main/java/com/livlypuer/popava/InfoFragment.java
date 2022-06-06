package com.livlypuer.popava;

import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.timepicker.TimeFormat;
import com.livlypuer.popava.db.DBManager;
import com.livlypuer.popava.models.Course;
import com.livlypuer.popava.models.Timetable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;


public class InfoFragment extends Fragment {
    CalendarView calendarView;
    Spinner spinner;
    Calendar calendar;
    ListView listView;
    DBManager mDBConnector;
    String[] change = new String[]{"Все курсы", "Мои курсы"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        mDBConnector = new DBManager(getActivity());

        // Inflate the layout for this fragment
        listView = view.findViewById(R.id.schedule_list);
        calendarView = view.findViewById(R.id.calendar);
        calendar = Calendar.getInstance();
        spinner = view.findViewById(R.id.spinner);


        LocalDate date = LocalDate.now();
        int dayOfWeek = date.get(ChronoField.DAY_OF_WEEK);
        ArrayList<Course> courses = mDBConnector.selectAllCourse();
        viewTimetable(dayOfWeek, courses);

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, change);
        // Привяжем массив через адаптер к ListView
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                if (change[selectedItemPosition] == change[0]){
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                    if (dayOfWeek == 0) {
                        dayOfWeek = 7;
                    }
                    ArrayList<Course> courses = mDBConnector.selectAllCourseName();
                    viewTimetable(dayOfWeek, courses);
                }else if (change[selectedItemPosition] == change[1]){
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                    if (dayOfWeek == 0) {
                        dayOfWeek = 7;
                    }
                    List<Course> courses = mDBConnector.selectStudentData(true).getCourses();
                    viewTimetable(dayOfWeek, courses);
                }
                Toast toast = Toast.makeText(getContext(),
                        "Ваш выбор: " + change[selectedItemPosition], Toast.LENGTH_SHORT);
                toast.show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner.setSelection(0);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                calendar.set(year, month, dayOfMonth);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                if (dayOfWeek == 0) {
                    dayOfWeek = 7;
                }
                Log.d("MY", spinner.getSelectedItemPosition() + "");
                if (spinner.getSelectedItemPosition() == 0){
                    ArrayList<Course> courses = mDBConnector.selectAllCourse();
                    viewTimetable(dayOfWeek, courses);
                }else{
                    List<Course> courses = mDBConnector.selectStudentData(true).getCourses();
                    viewTimetable(dayOfWeek, courses);
                }
            }
        });
        return view;
    }

    protected void viewTimetable(int dayOfWeek, List<Course> courses) {
        ArrayList<String> coursesName = new ArrayList<>();
        Log.d("MY", "DayOfWeek " + dayOfWeek + "");
        for (int i = 0; i < courses.size(); i++) {
            Timetable timetable = courses.get(i).getTimetable();
            if (dayOfWeek == 1 && timetable.mon) {
                coursesName.add(timetable.time_mon.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " - " + timetable.time_end_mon.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " " + courses.get(i).getTitle());
            }
            if (dayOfWeek == 2 && timetable.tue) {
                coursesName.add(timetable.time_tue.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " - " + timetable.time_end_tue.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " " + courses.get(i).getTitle());
            }
            if (dayOfWeek == 3 && timetable.web) {
                coursesName.add(timetable.time_web.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " - " + timetable.time_end_web.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " " + courses.get(i).getTitle());
            }
            if (dayOfWeek == 4 && timetable.thu) {
                coursesName.add(timetable.time_thu.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " - " + timetable.time_end_thu.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " " + courses.get(i).getTitle());
            }
            if (dayOfWeek == 5 && timetable.fri) {
                coursesName.add(timetable.time_fri.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " - " + timetable.time_end_fri.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " " + courses.get(i).getTitle());
            }
            if (dayOfWeek == 6 && timetable.sat) {
                coursesName.add(timetable.time_sat.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " - " + timetable.time_end_sat.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " " + courses.get(i).getTitle());
            }
            if (dayOfWeek == 7 && timetable.sun) {
                coursesName.add(timetable.time_sun.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " - " + timetable.time_end_sun.format(DateTimeFormatter.ofPattern(Timetable.PATTERN))
                        + " " + courses.get(i).getTitle());
            }
        }
        // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, coursesName);
        // Привяжем массив через адаптер к ListView
        listView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}