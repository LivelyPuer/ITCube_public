package com.livlypuer.popava.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.livlypuer.popava.MainActivity;
import com.livlypuer.popava.R;
import com.livlypuer.popava.db.DBManager;
import com.livlypuer.popava.elems.EventsCard;
import com.livlypuer.popava.models.Course;
import com.livlypuer.popava.models.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EnterActivity extends AppCompatActivity {

    TextView registerButton;
    TextView enterButtonMain;

    EditText epnEnter;
    EditText passwordEnter;

    ProgressBar progressBar;
    OkHttpClient client = new OkHttpClient();
    DBManager mDBConnector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        mDBConnector = new DBManager(this);
        registerButton = findViewById(R.id.registerText);
        enterButtonMain = findViewById(R.id.enterButton);
        epnEnter = findViewById(R.id.epnEnterField);
        passwordEnter = findViewById(R.id.passwordEnterField);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);
        if (mDBConnector.existStudentData(true)) {
            updateUser();
            progressBar.setVisibility(View.VISIBLE);
            enterButtonMain.setEnabled(false);
            epnEnter.setEnabled(false);
            passwordEnter.setEnabled(false);
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(EnterActivity.this, RegisterActivity.class);
//                startActivity(i);
            }
        });
        enterButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = true;
                if (epnEnter.getText().toString().trim().equalsIgnoreCase("")) {
                    valid = false;
                    epnEnter.setError("Поле пустое");
                }
                if (passwordEnter.getText().toString().trim().equalsIgnoreCase("")) {
                    valid = false;
                    passwordEnter.setError("Поле пустое");
                }
                if (valid) {
                    checkPassword();
                    progressBar.setVisibility(View.VISIBLE);
                    enterButtonMain.setEnabled(false);
                    epnEnter.setEnabled(false);
                    passwordEnter.setEnabled(false);
                }
//                startHomeActivity();
            }
        });
    }

    protected void updateUser() {
        ParseUser pu = new ParseUser(this, mDBConnector.selectStudentData(true).getId());

        final Handler handler = new Handler();
        final int delay = 10; // 1000 milliseconds == 1 second
        handler.postDelayed(new Runnable() {
            public void run() {
                if (pu.isReady()) {
                    startHomeActivity();

                    pu.setReady(false);
                    Thread.currentThread().interrupt();
                }
                handler.postDelayed(this, delay);

            }
        }, delay);
    }

    protected void checkPassword() {
        ParseUser pu = new ParseUser(this, epnEnter.getText().toString(), passwordEnter.getText().toString());

        final Handler handler = new Handler();
        final int delay = 10; // 1000 milliseconds == 1 second
        handler.postDelayed(new Runnable() {
            public void run() {
                if (pu.isReady()) {
                    if (pu.getCheck()) {
                        startHomeActivity();
                    } else {
                        again();
                    }
                    pu.setReady(false);
                    Thread.currentThread().interrupt();
                }
                handler.postDelayed(this, delay);

            }
        }, delay);
    }

    protected String runMyCourses(JSONArray urls) {
        return "";
    }

    protected void runCourse(Long id) {

    }

    protected void startHomeActivity() {
        Intent i = new Intent(EnterActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public String getUrl(int path) {
        return getString(R.string.url) + getString(path);
    }

    public String getUrl(String path) {
        return getString(R.string.url) + path;
    }

    public void again() {
        progressBar.setVisibility(View.INVISIBLE);
        enterButtonMain.setEnabled(true);
        epnEnter.setEnabled(true);
        passwordEnter.setEnabled(true);
        passwordEnter.setText("");
        Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
    }
}

class ParseUser {
    OkHttpClient client = new OkHttpClient();
    DBManager mDBConnector;
    final Long[] id = {0L};
    final String[] username = {""};
    final String[] name = {""};
    final String[] lastname = {""};
    final String[] email = {""};
    final ArrayList<Course> courses = new ArrayList<Course>();
    final boolean[] ready = {false};
    final String[] body = {""};
    final boolean[] check = {false};
    final boolean[] noConnect = {false};
    final boolean[] readyUser = {false};
    final boolean[] readyCourses = {false};
    final boolean[] readyTimetables = {false};
    final boolean[] readyTeacher = {false};

    private String getIdLast(String url) {
        String[] tmp = url.split("/");
        return tmp[(int) (Arrays.stream(tmp).count() - 1)];
    }

    public ParseUser(EnterActivity activity, String username_in, String password_in) {
        mDBConnector = new DBManager(activity);
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(activity.getUrl(R.string.check_password_url) + username_in + "/" + password_in)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    public void onResponse(Call call, Response response)
                            throws IOException {
                        body[0] = response.body().string();
                        ready[0] = true;

                    }

                    public void onFailure(Call call, IOException e) {
                        noConnect[0] = true;
                    }
                });
            }
        });
        final Handler handler = new Handler();
        final int delay = 10; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {
                if (noConnect[0]) {
                    noConnect[0] = false;
                    Toast.makeText(activity.getApplicationContext(), "Отсутствует подключение к интернету", Toast.LENGTH_LONG).show();
                    setReady(true);
                    Thread.currentThread().interrupt();
                } else if (ready[0]) {
                    ready[0] = false;
                    try {
                        JSONObject json = new JSONObject(body[0]);
                        boolean check_b = Integer.parseInt(json.get("result").toString()) == 1;
                        check[0] = check_b;
                        if (check_b) {
                            Log.d("MY", "ок");
                            JSONObject data = json.getJSONObject("data");
                            id[0] = data.getLong("id");
                            username[0] = data.getString("username");
                            name[0] = data.getString("first_name");
                            lastname[0] = data.getString("last_name");
                            email[0] = data.getString("email");
                            setReady(true);
                            ArrayList<Course> courses = new ArrayList<>();
                            JSONArray courses_j = data.getJSONArray("courses");
                            for (int i = 0; i < courses_j.length(); i++) {
//                                Log.d("MY", "C: " + getIdLast(courses_j.getString(i)));
                                courses.add(mDBConnector.selectCourse(Long.parseLong(getIdLast(courses_j.getString(i)))));
                            }
                            mDBConnector.safeInsertStudentData(new Person(id[0], name[0], lastname[0], username[0], email[0], courses), true);
//                            runCourses(activity, courses_j);

                        } else {
                            setReady(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Thread.currentThread().interrupt();
                }
                handler.postDelayed(this, delay);

            }
        }, delay);
    }

    public ParseUser(EnterActivity activity, long idIn) {
        mDBConnector = new DBManager(activity);
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(activity.getUrl(R.string.update_user) + idIn)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    public void onResponse(Call call, Response response)
                            throws IOException {
                        body[0] = response.body().string();
                        ready[0] = true;

                    }

                    public void onFailure(Call call, IOException e) {
                        noConnect[0] = true;
                    }
                });
            }
        });
        final Handler handler = new Handler();
        final int delay = 10; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {
                if (noConnect[0]) {
                    noConnect[0] = false;
                    Toast.makeText(activity.getApplicationContext(), "Отсутствует подключение к интернету", Toast.LENGTH_LONG).show();
                    setReady(true);
                    Thread.currentThread().interrupt();
                } else if (ready[0]) {
                    ready[0] = false;
                    try {
                        JSONObject data = new JSONObject(body[0]);
                        Log.d("MY", "ок");
                        id[0] = data.getLong("id");
                        username[0] = data.getString("username");
                        name[0] = data.getString("first_name");
                        lastname[0] = data.getString("last_name");
                        email[0] = data.getString("email");
                        setReady(true);
                        ArrayList<Course> courses = new ArrayList<>();
                        JSONArray courses_j = data.getJSONArray("courses");
                        for (int i = 0; i < courses_j.length(); i++) {
//                                Log.d("MY", "C: " + getIdLast(courses_j.getString(i)));
                            courses.add(mDBConnector.selectCourse(Long.parseLong(getIdLast(courses_j.getString(i)))));
                        }
                        mDBConnector.safeInsertStudentData(new Person(id[0], name[0], lastname[0], username[0], email[0], courses), true);
//                            runCourses(activity, courses_j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Thread.currentThread().interrupt();
                }
                handler.postDelayed(this, delay);

            }
        }, delay);
    }

    public boolean isReady() {

        return ready[0];
    }

    public void setReady(boolean r) {
        ready[0] = r;
    }

    public boolean getCheck() {
        return check[0];
    }

}
