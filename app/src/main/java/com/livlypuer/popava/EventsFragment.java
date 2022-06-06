package com.livlypuer.popava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;


import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import com.livlypuer.popava.db.DBManager;
import com.livlypuer.popava.models.Course;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


public class EventsFragment extends Fragment {
    DBManager mDBConnector;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        mDBConnector = new DBManager(getActivity());
//        TODO
        showCourses(view);
        showMyCourses(view);
        return view;
    }

    private void showCourses(View view) {
        GridLayout grid = view.findViewById(R.id.gridCont);
        ArrayList<Course> courses = mDBConnector.selectAllIsOpenCourse(true);
        for (int index = 0; index < courses.size(); index++) {
            Course course = courses.get(index);
            CardView cardview = new CardView(view.getContext());

            LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            if (index % 2 == 0) {
                layoutparams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.dp16),
                        getResources().getDimensionPixelSize(R.dimen.dp8), 0);
            } else {
                layoutparams.setMargins(getResources().getDimensionPixelSize(R.dimen.dp8),
                        getResources().getDimensionPixelSize(R.dimen.dp16),
                        0, 0);
            }
            cardview.setLayoutParams(layoutparams);
            cardview.setCardElevation(0);
            cardview.setRadius(getResources().getDimension(R.dimen.dp20));

            LinearLayout basell = new LinearLayout(view.getContext());
            LinearLayout.LayoutParams basellparams = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.dp180),
                    getResources().getDimensionPixelSize(R.dimen.dp200)
            );
            basell.setLayoutParams(basellparams);
            basell.setPadding(getResources().getDimensionPixelSize(R.dimen.dp10),
                    getResources().getDimensionPixelSize(R.dimen.dp10),
                    getResources().getDimensionPixelSize(R.dimen.dp10),
                    getResources().getDimensionPixelSize(R.dimen.dp10));
            basell.setOrientation(LinearLayout.VERTICAL);
            GifImageView roundedImageView = new GifImageView(view.getContext());
            LinearLayout.LayoutParams roundedImageViewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.dp100)
            );
            roundedImageView.setLayoutParams(roundedImageViewParams);
            roundedImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            roundedImageView.setImageBitmap(course.getBitmapImage());
//            roundedImageView.setCornerRadiusDimen(R.dimen.dp20);


            LinearLayout titleLL = new LinearLayout(view.getContext());
            LinearLayout.LayoutParams titleLLparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            titleLLparams.gravity = Gravity.CENTER;
            titleLL.setLayoutParams(titleLLparams);
            titleLL.setOrientation(LinearLayout.VERTICAL);

            TextView title = new TextView(view.getContext());
            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            title.setLayoutParams(titleParams);
            title.setText(course.getTitle());
            Log.d("MY", course.getTitle());
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            title.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.rubik), Typeface.BOLD);
            title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            title.setTextColor(getResources().getColor(R.color.black));

            ImageView titleImage = new ImageView(view.getContext());
            LinearLayout.LayoutParams titleImageParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.dp10)
            );
            titleImage.setLayoutParams(titleImageParams);
            titleImage.setImageResource(R.drawable.line);

            Button button = new Button(view.getContext());
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.dp130),
                    getResources().getDimensionPixelSize(R.dimen.dp36)
            );
            buttonParams.gravity = Gravity.CENTER;
            button.setLayoutParams(buttonParams);
            button.setText(R.string.sign_up);
//            button.set(Color.RED);
            button.setBackground(getResources().getDrawable(R.drawable.simple_button));
            button.setTextColor(Color.WHITE);
            button.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.circe), Typeface.BOLD);
            button.setAllCaps(false);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/itcube-site/courses"));
                    startActivity(browserIntent);
                }
            });
//            button.setBackgroundResource(R.color.idicator);
            basell.addView(roundedImageView);
            basell.addView(titleLL);
            titleLL.addView(title);
            titleLL.addView(titleImage);
            basell.addView(button);
            cardview.addView(basell);

            grid.addView(cardview);


        }
        Log.d("MY", Integer.toString(grid.getChildCount()));
    }

    private void showMyCourses(View view) {
        LinearLayout grid = view.findViewById(R.id.myCoursesCont);
        List<Course> courses = mDBConnector.selectStudentData(true).getCourses();
        if (courses.size() == 0) {
            TextView textView = view.findViewById(R.id.textNews2);
            textView.setText("");
        }
        for (int index = 0; index < courses.size(); index++) {
            Course course = courses.get(index);
            CardView cardview = new CardView(view.getContext());

            LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutparams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.dp16),
                    getResources().getDimensionPixelSize(R.dimen.dp8), 0);

            cardview.setLayoutParams(layoutparams);
            cardview.setCardElevation(0);
            cardview.setRadius(getResources().getDimension(R.dimen.dp20));

            LinearLayout basell = new LinearLayout(view.getContext());
            LinearLayout.LayoutParams basellparams = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.dp180),
                    getResources().getDimensionPixelSize(R.dimen.dp200)
            );
            basell.setLayoutParams(basellparams);
            basell.setPadding(getResources().getDimensionPixelSize(R.dimen.dp10),
                    getResources().getDimensionPixelSize(R.dimen.dp10),
                    getResources().getDimensionPixelSize(R.dimen.dp10),
                    getResources().getDimensionPixelSize(R.dimen.dp10));
            basell.setOrientation(LinearLayout.VERTICAL);
            GifImageView roundedImageView = new GifImageView(view.getContext());
            LinearLayout.LayoutParams roundedImageViewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.dp100)
            );
            roundedImageView.setLayoutParams(roundedImageViewParams);
            roundedImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            roundedImageView.setImageBitmap(course.getBitmapImage());
//            roundedImageView.setCornerRadiusDimen(R.dimen.dp20);


            LinearLayout titleLL = new LinearLayout(view.getContext());
            LinearLayout.LayoutParams titleLLparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            titleLLparams.gravity = Gravity.CENTER;
            titleLL.setLayoutParams(titleLLparams);
            titleLL.setOrientation(LinearLayout.VERTICAL);

            TextView title = new TextView(view.getContext());
            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            title.setLayoutParams(titleParams);
            title.setText(course.getTitle());
            Log.d("MY", course.getTitle());
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            title.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.rubik), Typeface.BOLD);
            title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            title.setTextColor(getResources().getColor(R.color.black));

            ImageView titleImage = new ImageView(view.getContext());
            LinearLayout.LayoutParams titleImageParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.dp10)
            );
            titleImage.setLayoutParams(titleImageParams);
            titleImage.setImageResource(R.drawable.line);

            Button button = new Button(view.getContext());
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.dp130),
                    getResources().getDimensionPixelSize(R.dimen.dp36)
            );
            buttonParams.gravity = Gravity.CENTER;
            button.setLayoutParams(buttonParams);
            button.setText(R.string.about_course);
//            button.set(Color.RED);
            button.setBackground(getResources().getDrawable(R.drawable.simple_button));
            button.setTextColor(Color.WHITE);
            button.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.circe), Typeface.BOLD);
            button.setAllCaps(false);
            button.setGravity(Gravity.TOP | Gravity.CENTER);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//            button.setBackgroundResource(R.color.idicator);
            basell.addView(roundedImageView);
            basell.addView(titleLL);
            titleLL.addView(title);
            titleLL.addView(titleImage);
            basell.addView(button);
            cardview.addView(basell);

            grid.addView(cardview);


        }
        Log.d("MY", Integer.toString(grid.getChildCount()));
    }

}