package com.livlypuer.popava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.livlypuer.popava.db.DBManager;
import com.livlypuer.popava.elems.EventsCard;
import com.livlypuer.popava.models.Person;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    public ArrayList<Integer> icons = new ArrayList<>();
    public TabLayout tabLayout;
    public Button restartButton;
    public ViewPager2 viewPager;
    public DrawerLayout drawerLayout;
    public ImageView switchNavigationView;
    public TextView sideName;
    final public static String[] Months = new String[]{"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря",};
    public List<String> courses = new ArrayList<String>() {
    };
    private ArrayList<EventsCard> eventsCardsInActivity = new ArrayList<>();
    private static Boolean eventsCardIsReady = false;
    private static String eventsCardText = "";
    private static String oldEventsCardText = "";
    public static MainActivity mainActivitySingleton;
    private Person selfUser;
    DBManager mDBConnector;
    public static MainActivity _inst;
    private Boolean coursesReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _inst = this;
        setContentView(R.layout.activity_main);
        mDBConnector = new DBManager(this);
        mainActivitySingleton = this;
        InitElements();
        InitListeners();
        selfUser = mDBConnector.selectStudentData(true);
        Log.d("MY", selfUser.getName());


    }

    @Override
    protected void onStart() {
        super.onStart();
//        sideName.setText(selfUser.getName());
    }

    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    protected void InitElements() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        drawerLayout = findViewById(R.id.drawerLayout);
        switchNavigationView = findViewById(R.id.logo);
        sideName = findViewById(R.id.sideName);
        restartButton = findViewById(R.id.restart);
        InitPages();
    }

    protected void InitListeners() {
        switchNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartActivity();
            }
        });
    }

    protected void InitPages() {
        InitPagesLists();

        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(MainActivity.this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setIcon(icons.get(position));
            }
        }).

                attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.setCurrentItem(0);
    }

    protected void InitPagesLists() {

        fragments.add(new HomeFragment());
        fragments.add(new EventsFragment());
        fragments.add(new InfoFragment());
        fragments.add(new ContactFragment());
        fragments.add(new SearchFragment());

        icons.add(R.drawable.home_icon);
        icons.add(R.drawable.bookmark_icon);
        icons.add(R.drawable.info_button);
        icons.add(R.drawable.person_icon);
        icons.add(R.drawable.question);


    }

    class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(AppCompatActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }

    }

    public static String doGet(String url)
            throws Exception {

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("Content-Type", "application/json");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        bufferedReader.close();

//      print result
        Log.d("MYHTTP", "Response string: " + response.toString());


        return response.toString();
    }

    public String getUrl(int path) {
        return getString(R.string.url) + getString(path);
    }


    public static void setEventsCardIsReady(String jsonText) {
        eventsCardIsReady = true;
        eventsCardText = jsonText;
    }
}
