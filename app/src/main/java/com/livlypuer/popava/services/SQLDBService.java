package com.livlypuer.popava.services;

import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.livlypuer.popava.MainActivity;
import com.livlypuer.popava.db.DBManager;
import com.livlypuer.popava.models.EventModel;
import com.livlypuer.popava.models.Person;
import com.livlypuer.popava.models.Timetable;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SQLDBService extends Service {
    public static final String CHANNEL = "GIS_SERVICE";
    public static final String INFO = "INFO";
    DBManager mDBConnector;
    OkHttpClient client = new OkHttpClient();
    private Intent intent;
    final public static String server = "http://LivelyDog10.pythonanywhere.com";
    final public static String timetable_url = "/api/time-tables/";
    final public static String teachers_url = "/api/teachers/";
    final public static String courses_url = "/api/courses/";
    final public static String events_url = "/api/events/";

    @Override
    public void onCreate() {
        // сообщение о создании службы
        Toast.makeText(this, "Sync database", Toast.LENGTH_SHORT).show();
        mDBConnector = new DBManager(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.intent = intent;
        // сообщение о запуске службы
//        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
        startSyncDB();
        return START_NOT_STICKY;
    }

    public void startSyncDB() {
        AsyncDB t = new AsyncDB();
        t.execute();
    }

    @Override
    public void onDestroy() {
        //сообщение об остановке службы
        Toast.makeText(this, "Service stoped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public String getUrl(String path) {
        return server + path;
    }

    public byte[] bitmapToBytes(Bitmap bitmap) {
        int size = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(byteBuffer);
        return byteBuffer.array();
    }

    //поток работы с сетью
    private class AsyncDB extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
//            MainActivity._inst.restartActivity();

        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.d("MY", "Start adds");
            addTimetables();
            addTeachers();
            addCourses();
            addEvent();

            Log.d("MY", "End adds");
            return "true";
        }

        private String getIdLast(String url) {
            String[] tmp = url.split("/");
            return tmp[(int) (Arrays.stream(tmp).count() - 1)];
        }

        private String saveToInternalStorage(String name, Bitmap bitmapImage){
            // path to /data/data/yourapp/app_data/imageDir
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("images", Context.MODE_PRIVATE);

            // Create imageDir
            File mypath = new File(directory, name + ".png");
            if(mypath.exists()){
                return mypath.getAbsolutePath();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                return mypath.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return directory.getAbsolutePath();
        }


        private void addTimetables() {
            Request request = new Request.Builder()
                    .url(getUrl(timetable_url))
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONArray json = new JSONArray(response.body().string());
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject object = json.getJSONObject(i);
                            long id = object.getLong("id");
                            boolean mon = object.getBoolean("Mon");
                            boolean tue = object.getBoolean("Tue");
                            boolean web = object.getBoolean("Web");
                            boolean thu = object.getBoolean("Thu");
                            boolean fri = object.getBoolean("Fri");
                            boolean sat = object.getBoolean("Sat");
                            boolean sun = object.getBoolean("Sun");
                            LocalTime time_mon = LocalTime.parse(object.getString("MonTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_tue = LocalTime.parse(object.getString("TueTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_web = LocalTime.parse(object.getString("WebTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_thu = LocalTime.parse(object.getString("ThuTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_fri = LocalTime.parse(object.getString("FriTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_sat = LocalTime.parse(object.getString("SatTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_sun = LocalTime.parse(object.getString("SunTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_end_mon = LocalTime.parse(object.getString("MonEndTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_end_tue = LocalTime.parse(object.getString("TueEndTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_end_web = LocalTime.parse(object.getString("WebEndTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_end_thu = LocalTime.parse(object.getString("ThuEndTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_end_fri = LocalTime.parse(object.getString("FriEndTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_end_sat = LocalTime.parse(object.getString("SatEndTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            LocalTime time_end_sun = LocalTime.parse(object.getString("SunEndTime"), DateTimeFormatter.ofPattern(Timetable.PATTERN));
                            Timetable timetable = new Timetable(id, mon, tue, web, thu, fri, sat, sun, time_mon, time_tue, time_web, time_thu, time_fri, time_sat, time_sun, time_end_mon, time_end_tue, time_end_web, time_end_thu, time_end_fri, time_end_sat, time_end_sun);
                            mDBConnector.safeInsertTimetable(timetable);
                        }
                    } catch (JSONException ee) {
                        ee.printStackTrace();
                        Log.d("MY", "Timetable error");
                        return;
                    }
                    Log.d("MY", "Timetable OK");
                }
            });
        }

        private void addTeachers() {
            Request request = new Request.Builder()
                    .url(getUrl(teachers_url))
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONArray json = new JSONArray(response.body().string());

                        for (int i = 0; i < json.length(); i++) {
                            JSONObject object = json.getJSONObject(i);
                            long id = object.getLong("id");
                            String username = object.getString("username");
                            String firstname = object.getString("first_name");
                            String lastname = object.getString("last_name");
                            String email = object.getString("email");
                            String get_image_url = object.getString("photo");
                            RequestCreator p = Picasso.get().load(getUrl(get_image_url));
                            Bitmap img = p.get();
                            String name_img = "Teachers" + id + "_" + getUrl(get_image_url).hashCode();
                            String path = saveToInternalStorage(name_img, img);
                            Person t = new Person(id, firstname, lastname, username, email, path);
                            mDBConnector.safeInsertTeacher(t);
                        }
                    } catch (JSONException ee) {
                        ee.printStackTrace();
                        Log.d("MY", "Teacher error");
                        return;
                    }
                    Log.d("MY", "Teacher OK");
                }
            });
        }

        private void addCourses() {
            Request request = new Request.Builder()
                    .url(getUrl(courses_url))
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONArray json = new JSONArray(response.body().string());

                        for (int i = 0; i < json.length(); i++) {
                            JSONObject object = json.getJSONObject(i);
                            long id = object.getLong("id");
                            String get_image_url = object.getString("img");
                            String imgOriginalName = getIdLast(get_image_url);
                            Log.d("MY", "orig" + imgOriginalName);
                            RequestCreator p = Picasso.get().load(get_image_url);
                            Bitmap img = p.get();
                            String name_img = "Course" + id + "_" + get_image_url.hashCode();
                            String path = saveToInternalStorage(name_img, img);
                            long teacher_id = Long.parseLong(getIdLast(object.getString("teacher")));
                            long timetable_id = Long.parseLong(getIdLast(object.getString("timetable")));
                            String title = object.getString("title");
                            String description = object.getString("description");
                            boolean isOpen = object.getBoolean("is_open");
                            Log.d("MY", "" + id + " " + isOpen);
                            mDBConnector.safeNativeInsertCourse(id, title, timetable_id, description, teacher_id, path, isOpen);
                        }
                    } catch (JSONException ee) {
                        ee.printStackTrace();
                        Log.d("MY", "Course error");
                        return;
                    }
                    Log.d("MY", "Course OK");
                }
            });
        }

        private void addEvent() {
            Request request = new Request.Builder()
                    .url(getUrl(events_url))
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONArray json = new JSONArray(response.body().string());

                        for (int i = 0; i < json.length(); i++) {
                            JSONObject object = json.getJSONObject(i);
                            long id = object.getLong("id");
                            LocalDate date = LocalDate.parse(object.getString("start_date"), DateTimeFormatter.ofPattern(EventModel.PATTERN));
                            String title = object.getString("title");
                            String description = object.getString("description");
                            boolean isOpen = object.getBoolean("is_open");
                            mDBConnector.safeInsertEvent(new EventModel(id, title, date, description, isOpen));
                        }
                    } catch (JSONException ee) {
                        ee.printStackTrace();
                        Log.d("MY", "Events error");
                        return;
                    }
                    Log.d("MY", "Events OK");
                }
            });
        }
    }
}
