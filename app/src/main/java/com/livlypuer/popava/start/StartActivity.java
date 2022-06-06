package com.livlypuer.popava.start;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.livlypuer.popava.MainActivity;
import com.livlypuer.popava.R;
import com.livlypuer.popava.db.DBManager;
import com.livlypuer.popava.register.EnterActivity;
import com.livlypuer.popava.services.SQLDBService;


public class StartActivity extends AppCompatActivity {
    DBManager mDBConnector;
    private Button nextButton;
    private TextView welcome_text_elem;
    private Integer[] welcomeText = new Integer[]{R.string.start_check_news, R.string.start_get_courses, R.string.start_check_tasks};
    private Integer curPosition = 0;
    private Integer[] framesIndicatorInLayout = new Integer[]{R.id.indicator1, R.id.indicator2, R.id.indicator3};
    private Integer selectFrame = R.drawable.indicator;
    private Integer notSelectFrame = R.drawable.no_select_frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mDBConnector=new DBManager(this);

        Intent intent = new Intent(this, SQLDBService.class);
        startService(intent);

        if (mDBConnector.existStudentData(true))
        {
            Intent i = new Intent(StartActivity.this, EnterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        nextButton = findViewById(R.id.next_button);
        welcome_text_elem = findViewById(R.id.welcome_text);
        showNewFrame();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curPosition += 1;
                showNewFrame();
            }
        });
    }

    protected void showNewFrame() {

        if (curPosition > 2) {
            Intent i = new Intent(StartActivity.this, EnterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }else{
            for (int i = 0; i < 3; i++){
                ImageView indicator = findViewById(framesIndicatorInLayout[i]);
                if (i == curPosition){
                    indicator.setImageResource(selectFrame);
                }else{
                    indicator.setImageResource(notSelectFrame);
                }
            }

            welcome_text_elem.setText(welcomeText[curPosition]);
        }

    }
}