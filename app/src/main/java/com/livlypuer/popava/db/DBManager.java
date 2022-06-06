package com.livlypuer.popava.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.livlypuer.popava.models.Course;
import com.livlypuer.popava.models.EventModel;
import com.livlypuer.popava.models.Person;
import com.livlypuer.popava.models.Timetable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class DBManager {
    //<editor-fold desc="init database">
    // Данные базы данных и таблиц
    private static final String DATABASE_NAME = "itcube.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME_STUDENT_DATA = "Student_data";
    private static final String TABLE_NAME_COURSES = "Courses";
    private static final String TABLE_NAME_TIMETABLE = "Timetable";
    private static final String TABLE_NAME_TEACHERS = "Teachers";
    private static final String TABLE_NAME_EVENTS = "Events";
    //</editor-fold>

    ////<editor-fold desc="Student_data">
    // Название столбцов
    private static final String COLUMN_STUDENT_DATA_ID = "_id";
    private static final String COLUMN_STUDENT_DATA_NAME = "firstname";
    private static final String COLUMN_STUDENT_DATA_LASTNAME = "lastname";
    private static final String COLUMN_STUDENT_DATA_COURSES = "courses";
    private static final String COLUMN_STUDENT_DATA_I = "it_me";
    private static final String COLUMN_STUDENT_DATA_USERNAME = "username";
    private static final String COLUMN_STUDENT_DATA_EMAIL = "email";


    // Номера столбцов
    private static final int NUM_COLUMN_STUDENT_DATA_ID = 0;
    private static final int NUM_COLUMN_STUDENT_DATA_NAME = 1;
    private static final int NUM_COLUMN_STUDENT_DATA_LASTNAME = 2;
    private static final int NUM_COLUMN_STUDENT_DATA_COURSES = 3;
    private static final int NUM_COLUMN_STUDENT_DATA_I = 4;
    private static final int NUM_COLUMN_STUDENT_DATA_USERNAME = 5;
    private static final int NUM_COLUMN_STUDENT_DATA_EMAIL = 6;
    //</editor-fold>

    // <editor-fold desc="Teachers">
    // Название столбцов
    private static final String COLUMN_TEACHERS_ID = "_id";
    private static final String COLUMN_TEACHERS_FIRSTNAME = "firstname";
    private static final String COLUMN_TEACHERS_LASTNAME = "lastname";
    private static final String COLUMN_TEACHERS_USERNAME = "username";
    private static final String COLUMN_TEACHERS_EMAIL = "email";
    private static final String COLUMN_TEACHERS_PHOTO = "photo";

    // Номера столбцов
    private static final int NUM_COLUMN_TEACHERS_ID = 0;
    private static final int NUM_COLUMN_TEACHERS_FIRSTNAME = 1;
    private static final int NUM_COLUMN_TEACHERS_LASTNAME = 2;
    private static final int NUM_COLUMN_TEACHERS_USERNAME = 3;
    private static final int NUM_COLUMN_TEACHERS_EMAIL = 4;
    private static final int NUM_COLUMN_TEACHERS_PHOTO = 5;
    // </editor-fold>

    // <editor-fold desc="Events">
    // Название столбцов
    private static final String COLUMN_EVENTS_ID = "_id";
    private static final String COLUMN_EVENTS_TITLE = "title";
    private static final String COLUMN_EVENTS_DATE = "date";
    private static final String COLUMN_EVENTS_DESCRIPTION = "description";
    private static final String COLUMN_EVENTS_IS_OPEN = "is_open";

    // Номера столбцов
    private static final int NUM_COLUMN_EVENTS_ID = 0;
    private static final int NUM_COLUMN_EVENTS_TITLE = 1;
    private static final int NUM_COLUMN_EVENTS_DATE = 2;
    private static final int NUM_COLUMN_EVENTS_DESCRIPTION = 3;
    private static final int NUM_COLUMN_EVENTS_IS_OPEN = 4;
    // </editor-fold>

    // <editor-fold desc="Timetable">
    // Название столбцов
    private static final String COLUMN_TIMETABLE_ID = "_id";
    private static final String COLUMN_TIMETABLE_MON = "mon";
    private static final String COLUMN_TIMETABLE_TUE = "tue";
    private static final String COLUMN_TIMETABLE_WEB = "web";
    private static final String COLUMN_TIMETABLE_THU = "thu";
    private static final String COLUMN_TIMETABLE_FRI = "fri";
    private static final String COLUMN_TIMETABLE_SAT = "sat";
    private static final String COLUMN_TIMETABLE_SUN = "sun";
    private static final String COLUMN_TIMETABLE_TIME_MON = "time_mon";
    private static final String COLUMN_TIMETABLE_TIME_TUE = "time_tue";
    private static final String COLUMN_TIMETABLE_TIME_WEB = "time_web";
    private static final String COLUMN_TIMETABLE_TIME_THU = "time_thu";
    private static final String COLUMN_TIMETABLE_TIME_FRI = "time_fri";
    private static final String COLUMN_TIMETABLE_TIME_SAT = "time_sat";
    private static final String COLUMN_TIMETABLE_TIME_SUN = "time_sun";
    private static final String COLUMN_TIMETABLE_TIME_END_MON = "time_end_mon";
    private static final String COLUMN_TIMETABLE_TIME_END_TUE = "time_end_tue";
    private static final String COLUMN_TIMETABLE_TIME_END_WEB = "time_end_web";
    private static final String COLUMN_TIMETABLE_TIME_END_THU = "time_end_thu";
    private static final String COLUMN_TIMETABLE_TIME_END_FRI = "time_end_fri";
    private static final String COLUMN_TIMETABLE_TIME_END_SAT = "time_end_sat";
    private static final String COLUMN_TIMETABLE_TIME_END_SUN = "time_end_sun";

    // Номера столбцов
    private static final int NUM_COLUMN_TIMETABLE_ID = 0;
    private static final int NUM_COLUMN_TIMETABLE_MON = 1;
    private static final int NUM_COLUMN_TIMETABLE_TUE = 2;
    private static final int NUM_COLUMN_TIMETABLE_WEB = 3;
    private static final int NUM_COLUMN_TIMETABLE_THU = 4;
    private static final int NUM_COLUMN_TIMETABLE_FRI = 5;
    private static final int NUM_COLUMN_TIMETABLE_SAT = 6;
    private static final int NUM_COLUMN_TIMETABLE_SUN = 7;
    private static final int NUM_COLUMN_TIMETABLE_TIME_MON = 8;
    private static final int NUM_COLUMN_TIMETABLE_TIME_TUE = 9;
    private static final int NUM_COLUMN_TIMETABLE_TIME_WEB = 10;
    private static final int NUM_COLUMN_TIMETABLE_TIME_THU = 11;
    private static final int NUM_COLUMN_TIMETABLE_TIME_FRI = 12;
    private static final int NUM_COLUMN_TIMETABLE_TIME_SAT = 13;
    private static final int NUM_COLUMN_TIMETABLE_TIME_SUN = 14;
    private static final int NUM_COLUMN_TIMETABLE_TIME_END_MON = 15;
    private static final int NUM_COLUMN_TIMETABLE_TIME_END_TUE = 16;
    private static final int NUM_COLUMN_TIMETABLE_TIME_END_WEB = 17;
    private static final int NUM_COLUMN_TIMETABLE_TIME_END_THU = 18;
    private static final int NUM_COLUMN_TIMETABLE_TIME_END_FRI = 19;
    private static final int NUM_COLUMN_TIMETABLE_TIME_END_SAT = 20;
    private static final int NUM_COLUMN_TIMETABLE_TIME_END_SUN = 21;
    // </editor-fold>

    //<editor-fold desc="Courses">
    // Название столбцов
    private static final String COLUMN_COURSE_ID = "_id";
    private static final String COLUMN_COURSE_TITLE = "title";
    private static final String COLUMN_COURSE_TIMETABLE_ID = "timetable_id";
    private static final String COLUMN_COURSE_DESCRIPTION = "description";
    private static final String COLUMN_COURSE_TEACHER_ID = "teacher_id";
    private static final String COLUMN_COURSE_IMAGE = "image";
    private static final String COLUMN_COURSE_IS_OPEN = "is_open";

    // Номера столбцов
    private static final int NUM_COLUMN_COURSE_ID = 0;
    private static final int NUM_COLUMN_COURSE_TITLE = 1;
    private static final int NUM_COLUMN_COURSE_TIMETABLE_ID = 2;
    private static final int NUM_COLUMN_COURSE_DESCRIPTION = 3;
    private static final int NUM_COLUMN_COURSE_TEACHER_ID = 4;
    private static final int NUM_COLUMN_COURSE_IMAGE = 5;
    private static final int NUM_COLUMN_COURSE_IS_OPEN = 6;
    //</editor-fold>


    private final SQLiteDatabase mDataBase;

    public DBManager(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
//        mDataBase.get
    }

    private long insertStudentData(Person person, boolean i) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENT_DATA_ID, person.getId());
        cv.put(COLUMN_STUDENT_DATA_NAME, person.getName());
        cv.put(COLUMN_STUDENT_DATA_LASTNAME, person.getLastname());
        cv.put(COLUMN_STUDENT_DATA_USERNAME, person.getUsername());
        cv.put(COLUMN_STUDENT_DATA_EMAIL, person.getEmail());
        cv.put(COLUMN_STUDENT_DATA_COURSES, person.getIdCoursesStrings());
        cv.put(COLUMN_STUDENT_DATA_I, i ? 1 : 0);
        return mDataBase.insert(TABLE_NAME_STUDENT_DATA, null, cv);
    }

    private long insertTimetable(Timetable timetable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Timetable.PATTERN)
                .withZone(ZoneId.systemDefault());
        ContentValues cv = new ContentValues();
        Boolean[] weeks = timetable.GetWeeks();
        LocalTime[] times = timetable.GetTimes();
        LocalTime[] endTimes = timetable.GetEndTimes();

        cv.put(COLUMN_TIMETABLE_ID, timetable.getId());
        cv.put(COLUMN_TIMETABLE_MON, weeks[0]);
        cv.put(COLUMN_TIMETABLE_TUE, weeks[1]);
        cv.put(COLUMN_TIMETABLE_WEB, weeks[2]);
        cv.put(COLUMN_TIMETABLE_THU, weeks[3]);
        cv.put(COLUMN_TIMETABLE_FRI, weeks[4]);
        cv.put(COLUMN_TIMETABLE_SAT, weeks[5]);
        cv.put(COLUMN_TIMETABLE_SUN, weeks[6]);
        cv.put(COLUMN_TIMETABLE_TIME_MON, formatter.format(times[0]));
        cv.put(COLUMN_TIMETABLE_TIME_TUE, formatter.format(times[1]));
        cv.put(COLUMN_TIMETABLE_TIME_WEB, formatter.format(times[2]));
        cv.put(COLUMN_TIMETABLE_TIME_THU, formatter.format(times[3]));
        cv.put(COLUMN_TIMETABLE_TIME_FRI, formatter.format(times[4]));
        cv.put(COLUMN_TIMETABLE_TIME_SAT, formatter.format(times[5]));
        cv.put(COLUMN_TIMETABLE_TIME_SUN, formatter.format(times[6]));
        cv.put(COLUMN_TIMETABLE_TIME_END_MON, formatter.format(endTimes[0]));
        cv.put(COLUMN_TIMETABLE_TIME_END_TUE, formatter.format(endTimes[1]));
        cv.put(COLUMN_TIMETABLE_TIME_END_WEB, formatter.format(endTimes[2]));
        cv.put(COLUMN_TIMETABLE_TIME_END_THU, formatter.format(endTimes[3]));
        cv.put(COLUMN_TIMETABLE_TIME_END_FRI, formatter.format(endTimes[4]));
        cv.put(COLUMN_TIMETABLE_TIME_END_SAT, formatter.format(endTimes[5]));
        cv.put(COLUMN_TIMETABLE_TIME_END_SUN, formatter.format(endTimes[6]));
        return mDataBase.insert(TABLE_NAME_TIMETABLE, null, cv);
    }

    private long insertTeacher(Person person) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TEACHERS_ID, person.getId());
        cv.put(COLUMN_TEACHERS_FIRSTNAME, person.getName());
        cv.put(COLUMN_TEACHERS_LASTNAME, person.getLastname());
        cv.put(COLUMN_TEACHERS_USERNAME, person.getUsername());
        cv.put(COLUMN_TEACHERS_EMAIL, person.getEmail());
        cv.put(COLUMN_TEACHERS_PHOTO, person.getPhoto());
        return mDataBase.insert(TABLE_NAME_TEACHERS, null, cv);
    }

    private long insertEvent(EventModel event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(EventModel.PATTERN)
                .withZone(ZoneId.systemDefault());
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EVENTS_ID, event.getId());
        cv.put(COLUMN_EVENTS_TITLE, event.getTitle());
        cv.put(COLUMN_EVENTS_DATE, formatter.format(event.getDate()));
        cv.put(COLUMN_EVENTS_DESCRIPTION, event.getDescription());
        cv.put(COLUMN_EVENTS_IS_OPEN, event.isOpen()? 1: 0);
        return mDataBase.insert(TABLE_NAME_EVENTS, null, cv);
    }

    private long insertCourse(Course course) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COURSE_ID, course.getId());
        cv.put(COLUMN_COURSE_TITLE, course.getTitle());
        cv.put(COLUMN_COURSE_TIMETABLE_ID, course.getTimetable().getId());
        cv.put(COLUMN_COURSE_DESCRIPTION, course.getDescription());
        cv.put(COLUMN_COURSE_TEACHER_ID, course.getTeacher().getId());
        cv.put(COLUMN_COURSE_IMAGE, course.getImage());
        cv.put(COLUMN_COURSE_IS_OPEN, course.isOpen());
        return mDataBase.insert(TABLE_NAME_COURSES, null, cv);
    }

    private long insertNativeCourse(long id, String title, long timetable_id, String description, long teacher_id, String image, boolean isOpen) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COURSE_ID, id);
        cv.put(COLUMN_COURSE_TITLE, title);
        cv.put(COLUMN_COURSE_TIMETABLE_ID, timetable_id);
        cv.put(COLUMN_COURSE_DESCRIPTION, description);
        cv.put(COLUMN_COURSE_TEACHER_ID, teacher_id);
        cv.put(COLUMN_COURSE_IMAGE, image);
        cv.put(COLUMN_COURSE_IS_OPEN, isOpen);
        return mDataBase.insert(TABLE_NAME_COURSES, null, cv);
    }

    public long safeInsertStudentData(Person person, boolean i) {
        if (existStudentData(person.getId())) {
            return updateStudentData(person);
        }
        return insertStudentData(person, i);
    }

    public long safeInsertTimetable(Timetable timetable) {
        if (existTimetable(timetable.getId())) {
            return updateTimetable(timetable);
        }
        return insertTimetable(timetable);
    }

    public long safeNativeInsertCourse(long id, String title, long timetable_id,
                                       String description, long teacher_id, String image, boolean isOpen) {
        if (existCourse(id)) {
            return updateNativeCourse(id, title, timetable_id, description, teacher_id,
                    image, isOpen);
        }
        return insertNativeCourse(id, title, timetable_id, description, teacher_id,
                image, isOpen);
    }

    public long safeInsertTeacher(Person person) {
        if (existTeacher(person.getId())) {
            return updateTeacher(person);
        }
        return insertTeacher(person);
    }

    public long safeInsertEvent(EventModel event) {
        if (existEvent(event.getId())) {
            return updateEvent(event);
        }
        return insertEvent(event);
    }

    public long safeInsertCourse(Course course) {
        if (existCourse(course.getId())) {
            return updateCourse(course);
        }
        return insertCourse(course);
    }

    public long updateStudentData(Person person) {
        Log.d("MY", "Person: " + person.getName());
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENT_DATA_NAME, person.getName());
        cv.put(COLUMN_STUDENT_DATA_LASTNAME, person.getLastname());
        cv.put(COLUMN_STUDENT_DATA_USERNAME, person.getUsername());
        cv.put(COLUMN_STUDENT_DATA_EMAIL, person.getEmail());
        cv.put(COLUMN_STUDENT_DATA_COURSES, person.getIdCoursesStrings());
        Log.d("MY", "UPDATE: " + cv.get(COLUMN_STUDENT_DATA_NAME).toString());

        return mDataBase.update(TABLE_NAME_STUDENT_DATA, cv, COLUMN_STUDENT_DATA_ID + " = ?", new String[]{String.valueOf(person.getId())});
    }

    public long updateTimetable(Timetable timetable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Timetable.PATTERN)
                .withZone(ZoneId.systemDefault());
        ContentValues cv = new ContentValues();
        Boolean[] weeks = timetable.GetWeeks();
        LocalTime[] times = timetable.GetTimes();
        LocalTime[] endTimes = timetable.GetEndTimes();

        cv.put(COLUMN_TIMETABLE_MON, weeks[0]);
        cv.put(COLUMN_TIMETABLE_TUE, weeks[1]);
        cv.put(COLUMN_TIMETABLE_WEB, weeks[2]);
        cv.put(COLUMN_TIMETABLE_THU, weeks[3]);
        cv.put(COLUMN_TIMETABLE_FRI, weeks[4]);
        cv.put(COLUMN_TIMETABLE_SAT, weeks[5]);
        cv.put(COLUMN_TIMETABLE_SUN, weeks[6]);
        cv.put(COLUMN_TIMETABLE_TIME_MON, formatter.format(times[0]));
        cv.put(COLUMN_TIMETABLE_TIME_TUE, formatter.format(times[1]));
        cv.put(COLUMN_TIMETABLE_TIME_WEB, formatter.format(times[2]));
        cv.put(COLUMN_TIMETABLE_TIME_THU, formatter.format(times[3]));
        cv.put(COLUMN_TIMETABLE_TIME_FRI, formatter.format(times[4]));
        cv.put(COLUMN_TIMETABLE_TIME_SAT, formatter.format(times[5]));
        cv.put(COLUMN_TIMETABLE_TIME_SUN, formatter.format(times[6]));
        cv.put(COLUMN_TIMETABLE_TIME_END_MON, formatter.format(endTimes[0]));
        cv.put(COLUMN_TIMETABLE_TIME_END_TUE, formatter.format(endTimes[1]));
        cv.put(COLUMN_TIMETABLE_TIME_END_WEB, formatter.format(endTimes[2]));
        cv.put(COLUMN_TIMETABLE_TIME_END_THU, formatter.format(endTimes[3]));
        cv.put(COLUMN_TIMETABLE_TIME_END_FRI, formatter.format(endTimes[4]));
        cv.put(COLUMN_TIMETABLE_TIME_END_SAT, formatter.format(endTimes[5]));
        cv.put(COLUMN_TIMETABLE_TIME_END_SUN, formatter.format(endTimes[6]));
        return mDataBase.update(TABLE_NAME_TIMETABLE, cv, COLUMN_TIMETABLE_ID + " = ?", new String[]{String.valueOf(timetable.getId())});
    }

    public long updateTeacher(Person person) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TEACHERS_FIRSTNAME, person.getName());
        cv.put(COLUMN_TEACHERS_LASTNAME, person.getLastname());
        cv.put(COLUMN_TEACHERS_USERNAME, person.getUsername());
        cv.put(COLUMN_TEACHERS_EMAIL, person.getEmail());
        cv.put(COLUMN_TEACHERS_PHOTO, person.getPhoto());
        return mDataBase.update(TABLE_NAME_TEACHERS, cv, COLUMN_TEACHERS_ID + " = ?", new String[]{String.valueOf(person.getId())});
    }

    public long updateEvent(EventModel event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(EventModel.PATTERN)
                .withZone(ZoneId.systemDefault());
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EVENTS_TITLE, event.getTitle());
        cv.put(COLUMN_EVENTS_DATE, formatter.format(event.getDate()));
        cv.put(COLUMN_EVENTS_DESCRIPTION, event.getDescription());
        cv.put(COLUMN_EVENTS_IS_OPEN, event.isOpen()? 1: 0);
        return mDataBase.update(TABLE_NAME_EVENTS, cv, COLUMN_EVENTS_ID + " = ?", new String[]{String.valueOf(event.getId())});
    }

    public long updateCourse(Course course) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COURSE_TITLE, course.getTitle());
        cv.put(COLUMN_COURSE_TIMETABLE_ID, course.getTimetable().getId());
        cv.put(COLUMN_COURSE_DESCRIPTION, course.getDescription());
        cv.put(COLUMN_COURSE_TEACHER_ID, course.getTeacher().getId());
        cv.put(COLUMN_COURSE_IMAGE, course.getImage());
        cv.put(COLUMN_COURSE_IS_OPEN, course.isOpen());
        return mDataBase.update(TABLE_NAME_COURSES, cv, COLUMN_COURSE_ID + " = ?", new String[]{String.valueOf(course.getId())});
    }
    public long updateNativeCourse(long id, String title, long timetable_id,
                                   String description, long teacher_id, String image, boolean isOpen){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COURSE_ID, id);
        cv.put(COLUMN_COURSE_TITLE, title);
        cv.put(COLUMN_COURSE_TIMETABLE_ID, timetable_id);
        cv.put(COLUMN_COURSE_DESCRIPTION, description);
        cv.put(COLUMN_COURSE_TEACHER_ID, teacher_id);
        cv.put(COLUMN_COURSE_IMAGE, image);
        cv.put(COLUMN_COURSE_IS_OPEN, isOpen);
        return mDataBase.update(TABLE_NAME_COURSES, cv, COLUMN_COURSE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAll() {

        mDataBase.delete(TABLE_NAME_STUDENT_DATA, null, null);
        mDataBase.delete(TABLE_NAME_TEACHERS, null, null);
        mDataBase.delete(TABLE_NAME_COURSES, null, null);
        mDataBase.delete(TABLE_NAME_EVENTS, null, null);
        mDataBase.delete(TABLE_NAME_TIMETABLE, null, null);
    }
    public void deleteAllWithoutUser() {

        mDataBase.delete(TABLE_NAME_TEACHERS, null, null);
        mDataBase.delete(TABLE_NAME_COURSES, null, null);
        mDataBase.delete(TABLE_NAME_EVENTS, null, null);
        mDataBase.delete(TABLE_NAME_TIMETABLE, null, null);
    }

    public void deleteStudentData(long id) {
        mDataBase.delete(TABLE_NAME_STUDENT_DATA, COLUMN_STUDENT_DATA_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteTimetable(long id) {
        mDataBase.delete(TABLE_NAME_TIMETABLE, COLUMN_TIMETABLE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteTeacher(long id) {
        mDataBase.delete(TABLE_NAME_TEACHERS, COLUMN_TEACHERS_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteEvent(long id) {
        mDataBase.delete(TABLE_NAME_EVENTS, COLUMN_EVENTS_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteCourse(long id) {
        mDataBase.delete(TABLE_NAME_COURSES, COLUMN_COURSE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Person selectStudentData(long id) {
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.query(TABLE_NAME_STUDENT_DATA, null, COLUMN_STUDENT_DATA_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        String name = mCursor.getString(NUM_COLUMN_STUDENT_DATA_NAME);
        String lastname = mCursor.getString(NUM_COLUMN_STUDENT_DATA_LASTNAME);
        String username = mCursor.getString(NUM_COLUMN_STUDENT_DATA_USERNAME);
        String email = mCursor.getString(NUM_COLUMN_STUDENT_DATA_EMAIL);
        String coursesText = mCursor.getString(NUM_COLUMN_STUDENT_DATA_COURSES);
        String[] l_courses = coursesText.split(",");
        ArrayList<Course> courses = new ArrayList<>();
        if (coursesText.length() != 0) {
            for (int i = 0; i < Arrays.stream(l_courses).count(); i++) {
                courses.add(selectCourse(Long.parseLong(l_courses[i])));
            }
        }
        return new Person(id, name, lastname, username, email, courses);
    }

    public Person selectStudentData(boolean me) {
        Cursor mCursor = mDataBase.query(TABLE_NAME_STUDENT_DATA, null, COLUMN_STUDENT_DATA_I + " = ?", new String[]{String.valueOf(me ? 1 : 0)}, null, null, null);
        mCursor.moveToFirst();
        long id = mCursor.getLong(NUM_COLUMN_STUDENT_DATA_ID);
        String name = mCursor.getString(NUM_COLUMN_STUDENT_DATA_NAME);
        String lastname = mCursor.getString(NUM_COLUMN_STUDENT_DATA_LASTNAME);
        String username = mCursor.getString(NUM_COLUMN_STUDENT_DATA_USERNAME);
        String email = mCursor.getString(NUM_COLUMN_STUDENT_DATA_EMAIL);
        String coursesText = mCursor.getString(NUM_COLUMN_STUDENT_DATA_COURSES);
        String[] l_courses = coursesText.split(",");
        ArrayList<Course> courses = new ArrayList<>();
        if (coursesText.length() != 0) {
            for (int i = 0; i < Arrays.stream(l_courses).count(); i++) {
                courses.add(selectCourse(Long.parseLong(l_courses[i])));
            }
        }
        return new Person(id, name, lastname, username, email, courses);
    }

    public Timetable selectTimetable(long id) {
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.query(TABLE_NAME_TIMETABLE, null, COLUMN_TIMETABLE_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Timetable.PATTERN)
                .withZone(ZoneId.systemDefault());
        Boolean mon = mCursor.getInt(NUM_COLUMN_TIMETABLE_MON) == 1;
        Boolean tue = mCursor.getInt(NUM_COLUMN_TIMETABLE_TUE) == 1;
        Boolean web = mCursor.getInt(NUM_COLUMN_TIMETABLE_WEB) == 1;
        Boolean thu = mCursor.getInt(NUM_COLUMN_TIMETABLE_THU) == 1;
        Boolean fri = mCursor.getInt(NUM_COLUMN_TIMETABLE_FRI) == 1;
        Boolean sat = mCursor.getInt(NUM_COLUMN_TIMETABLE_SAT) == 1;
        Boolean sun = mCursor.getInt(NUM_COLUMN_TIMETABLE_SUN) == 1;

        LocalTime time_mon = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_MON), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_tue = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_TUE), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_web = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_WEB), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_thu = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_THU), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_fri = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_FRI), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_sat = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_SAT), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_sun = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_SUN), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_end_mon = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_MON), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_end_tue = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_TUE), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_end_web = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_WEB), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_end_thu = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_THU), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_end_fri = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_FRI), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_end_sat = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_SAT), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        LocalTime time_end_sun = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_SUN), DateTimeFormatter.ofPattern(Timetable.PATTERN));
        return new Timetable(id, mon, tue, web, thu, fri, sat, sun, time_mon, time_tue, time_web, time_thu, time_fri, time_sat, time_sun,
                time_end_mon,
                time_end_tue,
                time_end_web,
                time_end_thu,
                time_end_fri,
                time_end_sat,
                time_end_sun);
    }

    public Person selectTeacher(long id) {
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.query(TABLE_NAME_TEACHERS, null, COLUMN_TEACHERS_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        String name = mCursor.getString(NUM_COLUMN_TEACHERS_FIRSTNAME);
        String lastname = mCursor.getString(NUM_COLUMN_TEACHERS_LASTNAME);
        String username = mCursor.getString(NUM_COLUMN_TEACHERS_USERNAME);
        String email = mCursor.getString(NUM_COLUMN_TEACHERS_EMAIL);
        String photo = mCursor.getString(NUM_COLUMN_TEACHERS_PHOTO);
        return new Person(id, name, lastname, username, email, photo);
    }

    public EventModel selectEvent(long id) {
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.query(TABLE_NAME_EVENTS, null, COLUMN_EVENTS_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        String title = mCursor.getString(NUM_COLUMN_EVENTS_TITLE);
        LocalDate date = LocalDate.parse(mCursor.getString(NUM_COLUMN_EVENTS_DATE), DateTimeFormatter.ofPattern(EventModel.PATTERN));
        String description = mCursor.getString(NUM_COLUMN_EVENTS_DESCRIPTION);
        Boolean isOpen = mCursor.getInt(NUM_COLUMN_EVENTS_IS_OPEN) == 1;
        return new EventModel(id, title, date, description, isOpen);
    }

    public Course selectCourse(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME_COURSES, null, COLUMN_COURSE_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        String title = mCursor.getString(NUM_COLUMN_COURSE_TITLE);
        Timetable date = selectTimetable(mCursor.getInt(NUM_COLUMN_COURSE_TIMETABLE_ID));
        String description = mCursor.getString(NUM_COLUMN_COURSE_DESCRIPTION);
        Person teacher = selectTeacher(mCursor.getInt(NUM_COLUMN_COURSE_TEACHER_ID));
        String image = mCursor.getString(NUM_COLUMN_COURSE_IMAGE);
        boolean is_open = mCursor.getInt(NUM_COLUMN_COURSE_IS_OPEN) == 1;
        return new Course(id, title, date, description, teacher, image, is_open);
    }

    public Boolean existStudentData(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME_STUDENT_DATA, null, COLUMN_STUDENT_DATA_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        return mCursor.getCount() != 0;
    }

    public Boolean existStudentData(boolean me) {
        Cursor mCursor = mDataBase.query(TABLE_NAME_STUDENT_DATA, null, COLUMN_STUDENT_DATA_I + " = ?", new String[]{String.valueOf(me ? 1 : 0)}, null, null, null);
        return mCursor.getCount() != 0;
    }

    public Boolean existTimetable(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME_TIMETABLE, null, COLUMN_TIMETABLE_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        return mCursor.getCount() != 0;
    }

    public Boolean existTeacher(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME_TEACHERS, null, COLUMN_TEACHERS_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        return mCursor.getCount() != 0;
    }

    public Boolean existEvent(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME_EVENTS, null, COLUMN_EVENTS_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        return mCursor.getCount() != 0;
    }

    public Boolean existCourse(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME_COURSES, null, COLUMN_COURSE_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        return mCursor.getCount() != 0;
    }


    public ArrayList<Timetable> selectAllTimetable() {
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.query(TABLE_NAME_TIMETABLE, null, null, null, null, null, null);
        ArrayList<Timetable> arr = new ArrayList<Timetable>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_TIMETABLE_ID);
                Boolean mon = mCursor.getInt(NUM_COLUMN_TIMETABLE_MON) == 1;
                Boolean tue = mCursor.getInt(NUM_COLUMN_TIMETABLE_TUE) == 1;
                Boolean web = mCursor.getInt(NUM_COLUMN_TIMETABLE_WEB) == 1;
                Boolean thu = mCursor.getInt(NUM_COLUMN_TIMETABLE_THU) == 1;
                Boolean fri = mCursor.getInt(NUM_COLUMN_TIMETABLE_FRI) == 1;
                Boolean sat = mCursor.getInt(NUM_COLUMN_TIMETABLE_SAT) == 1;
                Boolean sun = mCursor.getInt(NUM_COLUMN_TIMETABLE_SUN) == 1;
                LocalTime time_mon = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_MON), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_tue = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_TUE), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_web = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_WEB), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_thu = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_THU), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_fri = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_FRI), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_sat = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_SAT), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_sun = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_SUN), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_end_mon = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_MON), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_end_tue = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_TUE), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_end_web = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_WEB), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_end_thu = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_THU), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_end_fri = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_FRI), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_end_sat = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_SAT), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                LocalTime time_end_sun = LocalTime.parse(mCursor.getString(NUM_COLUMN_TIMETABLE_TIME_END_SUN), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                arr.add(new Timetable(id, mon, tue, web, thu, fri, sat, sun, time_mon, time_tue, time_web, time_thu, time_fri, time_sat, time_sun,
                        time_end_mon,
                        time_end_tue,
                        time_end_web,
                        time_end_thu,
                        time_end_fri,
                        time_end_sat,
                        time_end_sun));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    public ArrayList<EventModel> selectAllEvents() {
        Cursor mCursor = mDataBase.query(TABLE_NAME_EVENTS, null, null, null, null, null, null);
        ArrayList<EventModel> arr = new ArrayList<>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_EVENTS_ID);
                String title = mCursor.getString(NUM_COLUMN_EVENTS_TITLE);
                LocalDate date = LocalDate.parse(mCursor.getString(NUM_COLUMN_EVENTS_DATE), DateTimeFormatter.ofPattern(EventModel.PATTERN));
                String description = mCursor.getString(NUM_COLUMN_EVENTS_DESCRIPTION);
                Boolean isOpen = mCursor.getInt(NUM_COLUMN_EVENTS_IS_OPEN) == 1;
                arr.add(new EventModel(id, title, date, description, isOpen));
            } while (mCursor.moveToNext());
        }
        return arr;
    }
    public ArrayList<EventModel> selectAllOpenEvents() {
        Cursor mCursor = mDataBase.query(TABLE_NAME_EVENTS, null, COLUMN_EVENTS_IS_OPEN + " = 1", null, null, null, null);
        ArrayList<EventModel> arr = new ArrayList<>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_EVENTS_ID);
                String title = mCursor.getString(NUM_COLUMN_EVENTS_TITLE);
                LocalDate date = LocalDate.parse(mCursor.getString(NUM_COLUMN_EVENTS_DATE), DateTimeFormatter.ofPattern(EventModel.PATTERN));
                String description = mCursor.getString(NUM_COLUMN_EVENTS_DESCRIPTION);
                Boolean isOpen = mCursor.getInt(NUM_COLUMN_EVENTS_IS_OPEN) == 1;
                arr.add(new EventModel(id, title, date, description, isOpen));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    public ArrayList<Person> selectAllTeachers() {
        Cursor mCursor = mDataBase.query(TABLE_NAME_TEACHERS, null, null, null, null, null, null);
        ArrayList<Person> arr = new ArrayList<Person>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_TEACHERS_ID);
                String name = mCursor.getString(NUM_COLUMN_TEACHERS_FIRSTNAME);
                String lastname = mCursor.getString(NUM_COLUMN_TEACHERS_LASTNAME);
                String username = mCursor.getString(NUM_COLUMN_TEACHERS_USERNAME);
                String email = mCursor.getString(NUM_COLUMN_TEACHERS_EMAIL);
                String photo = mCursor.getString(NUM_COLUMN_TEACHERS_PHOTO);
                arr.add(new Person(id, name, lastname, username, email, photo));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    public ArrayList<Course> selectAllCourse() {
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.query(TABLE_NAME_COURSES, null, null, null, null, null, null);
        ArrayList<Course> arr = new ArrayList<Course>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_COURSE_ID);
                String title = mCursor.getString(NUM_COLUMN_COURSE_TITLE);
                Timetable timetable = selectTimetable(mCursor.getInt(NUM_COLUMN_COURSE_TIMETABLE_ID));
                String description = mCursor.getString(NUM_COLUMN_COURSE_DESCRIPTION);
                Person teacher = selectTeacher(mCursor.getInt(NUM_COLUMN_COURSE_TEACHER_ID));
                String image = mCursor.getString(NUM_COLUMN_COURSE_IMAGE);
                boolean isOpen = mCursor.getInt(NUM_COLUMN_COURSE_IS_OPEN) == 1;
                arr.add(new Course(id, title, timetable, description, teacher, image, isOpen));
            } while (mCursor.moveToNext());
        }
        return arr;
    }
    public ArrayList<Course> selectAllCourseName() {
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.query(TABLE_NAME_COURSES, null, null, null, null, null, NUM_COLUMN_COURSE_TITLE + " ASC");
        ArrayList<Course> arr = new ArrayList<Course>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_COURSE_ID);
                String title = mCursor.getString(NUM_COLUMN_COURSE_TITLE);
                Timetable timetable = selectTimetable(mCursor.getInt(NUM_COLUMN_COURSE_TIMETABLE_ID));
                String description = mCursor.getString(NUM_COLUMN_COURSE_DESCRIPTION);
                Person teacher = selectTeacher(mCursor.getInt(NUM_COLUMN_COURSE_TEACHER_ID));
                String image = mCursor.getString(NUM_COLUMN_COURSE_IMAGE);
                boolean isOpen = mCursor.getInt(NUM_COLUMN_COURSE_IS_OPEN) == 1;
                arr.add(new Course(id, title, timetable, description, teacher, image, isOpen));
            } while (mCursor.moveToNext());
        }
        return arr;
    }
    public ArrayList<Course> selectAllIsOpenCourse(boolean isOpen) {
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.query(TABLE_NAME_COURSES, null, COLUMN_COURSE_IS_OPEN + " = ?", new String[]{String.valueOf(isOpen ? 1 : 0)}, null, null, null);
        ArrayList<Course> arr = new ArrayList<Course>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_COURSE_ID);
                String title = mCursor.getString(NUM_COLUMN_COURSE_TITLE);
                Timetable timetable = selectTimetable(mCursor.getInt(NUM_COLUMN_COURSE_TIMETABLE_ID));
                String description = mCursor.getString(NUM_COLUMN_COURSE_DESCRIPTION);
                Person teacher = selectTeacher(mCursor.getInt(NUM_COLUMN_COURSE_TEACHER_ID));
                String image = mCursor.getString(NUM_COLUMN_COURSE_IMAGE);
                arr.add(new Course(id, title, timetable, description, teacher, image, isOpen));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    public ArrayList<String> columns() {
        Cursor dbCursor = mDataBase.query(TABLE_NAME_TEACHERS, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        return new ArrayList<>(Arrays.asList(columnNames));
    }

    private static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
//            String query = "CREATE TABLE Student_data (_id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT NOT NULL, lastname TEXT NOT NULL, courses TEXT,it_me INTEGER, username TEXT, email TEXT);\n CREATE TABLE Teachers (_id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT NOT NULL, lastname TEXT NOT NULL, username TEXT NOT NULL, email TEXT NOT NULL);\n CREATE TABLE Events (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, date TEXT NOT NULL, description TEXT NOT NULL);\n" +
//                    "CREATE TABLE Timetable (_id INTEGER PRIMARY KEY AUTOINCREMENT, mon INTEGER, tue INTEGER, web INTEGER, thu INTEGER, fri INTEGER, sat INTEGER, sun INTEGER, time_mon TEXT, time_tue TEXT, time_web TEXT, time_thu TEXT, time_fri TEXT, time_sat TEXT, time_sun TEXT, time_end_mon TEXT, time_end_tue TEXT, time_end_web TEXT, time_end_thu TEXT, time_end_fri TEXT, time_end_sat TEXT, time_end_sun TEXT);\n" +
//                    "CREATE TABLE Courses (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, timetable_id INTEGER NOT NULL, description TEXT NOT NULL, teacher_id INTEGER NOT NULL);";
            db.execSQL("CREATE TABLE " + TABLE_NAME_STUDENT_DATA + " (" +
                    COLUMN_STUDENT_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_STUDENT_DATA_NAME + " TEXT , " +
                    COLUMN_STUDENT_DATA_LASTNAME + " TEXT , " +
                    COLUMN_STUDENT_DATA_COURSES + " TEXT," +
                    COLUMN_STUDENT_DATA_I + " INTEGER, " +
                    COLUMN_STUDENT_DATA_USERNAME + " TEXT, " +
                    COLUMN_STUDENT_DATA_EMAIL + " TEXT); ");
            db.execSQL("CREATE TABLE " + TABLE_NAME_TEACHERS + " (" +
                    COLUMN_TEACHERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TEACHERS_FIRSTNAME + " TEXT , " +
                    COLUMN_TEACHERS_LASTNAME + " TEXT , " +
                    COLUMN_TEACHERS_USERNAME + " TEXT , " +
                    COLUMN_TEACHERS_EMAIL + " TEXT, " +
                    COLUMN_TEACHERS_PHOTO + " TEXT ); ");

            db.execSQL("CREATE TABLE " + TABLE_NAME_EVENTS + " (" +
                    COLUMN_EVENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EVENTS_TITLE + " TEXT , " +
                    COLUMN_EVENTS_DATE + " TEXT , " +
                    COLUMN_EVENTS_DESCRIPTION + " TEXT, " +
                    COLUMN_EVENTS_IS_OPEN + " INTEGER ); ");
            db.execSQL("CREATE TABLE " + TABLE_NAME_TIMETABLE + " (" +
                    COLUMN_TIMETABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TIMETABLE_MON + " INTEGER, " +
                    COLUMN_TIMETABLE_TUE + " INTEGER, " +
                    COLUMN_TIMETABLE_WEB + " INTEGER, " +
                    COLUMN_TIMETABLE_THU + " INTEGER, " +
                    COLUMN_TIMETABLE_FRI + " INTEGER, " +
                    COLUMN_TIMETABLE_SAT + " INTEGER, " +
                    COLUMN_TIMETABLE_SUN + " INTEGER, " +
                    COLUMN_TIMETABLE_TIME_MON + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_TUE + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_WEB + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_THU + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_FRI + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_SAT + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_SUN + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_END_MON + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_END_TUE + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_END_WEB + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_END_THU + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_END_FRI + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_END_SAT + " TEXT, " +
                    COLUMN_TIMETABLE_TIME_END_SUN + " TEXT);");
            db.execSQL("CREATE TABLE " + TABLE_NAME_COURSES + " (" +
                    COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COURSE_TITLE + " TEXT, " +
                    COLUMN_COURSE_TIMETABLE_ID + " INTEGER, " +
                    COLUMN_COURSE_DESCRIPTION + " TEXT, " +
                    COLUMN_COURSE_TEACHER_ID + " INTEGER, " +
                    COLUMN_COURSE_IMAGE + " TEXT, " +
                    COLUMN_COURSE_IS_OPEN + " TEXT);");
//            db.execSQL(query);
            db.execSQL("SELECT * FROM Student_data");
//            Log.d("MYSQL", query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STUDENT_DATA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_COURSES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TIMETABLE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TEACHERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EVENTS);
            onCreate(db);
        }
    }
}