package com.livlypuer.popava.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Person extends BaseModel {
    private String name;
    private String photo;
    private String lastname;
    private String username;
    private String email;
    private List<Course> courses = new ArrayList<>();


    public Person(long id, String name, String lastname, String username, String email, String photo) {
        super(id);
        setUsername(username);
        setPhoto(photo);
        setEmail(email);
        setName(name);
        setLastname(lastname);
    }

    public Person(long id, String name, String lastname, String username, String email, ArrayList<Course> courses) {
        super(id);
        setCourses(courses);
        setEmail(email);
        setName(name);
        setUsername(username);
        setLastname(lastname);
    }

    public String getIdCoursesStrings() {
        ArrayList<String> ids = getIdCourses();
        return String.join(",", ids);
    }

    public ArrayList<String> getIdCourses() {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            ids.add(Long.toString(courses.get(i).getId()));
        }
        return ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void AddCourse(Course course) {
        courses.add(course);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public Bitmap getBitmapPhoto() {
        {
            Log.d("MY", photo);
            try {
                File f = new File(photo);
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
}
