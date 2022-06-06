package com.livlypuer.popava.models;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class Course extends BaseModel {
    private String title;
    private String image;
    private Timetable timetable;
    private String description;
    private Person teacher;
    private boolean isOpen;

    public Course(long id, String title, Timetable timetable, String description, Person teacher, String image_name, boolean isOpen) {
        super(id);
        this.title = title;
        this.image = image_name;
        this.timetable = timetable;
        this.description = description;
        this.teacher = teacher;
        this.isOpen = isOpen;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Bitmap getBitmapImage() {
        {
            Log.d("MY", image);
            try {
                File f = new File(image);
                Log.d("MY", "FILE");
                return BitmapFactory.decodeStream(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                Log.d("MY", "NO FILE");
                int w = 1, h = 1;

                Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
                return Bitmap.createBitmap(w, h, conf);
            }

        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
