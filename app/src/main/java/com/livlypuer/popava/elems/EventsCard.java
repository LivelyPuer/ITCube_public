package com.livlypuer.popava.elems;

import static com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.android.material.card.MaterialCardView;
import com.livlypuer.popava.R;
import com.livlypuer.popava.databinding.ElemEventsCardBinding;

public class EventsCard extends MaterialCardView {
    private ElemEventsCardBinding binding;
    private static final int defStyleAttr = R.style.event_card;
    public EventsCard(@NonNull Context context) {
        this(context, null);
        init(context, null, defStyleAttr);

    }
    public EventsCard(Context context, AttributeSet attrs) {
        this(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public EventsCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.elem_events_card, this, true);
        binding = ElemEventsCardBinding.bind(this);

        binding.getRoot().setBackgroundResource(android.R.color.transparent);

        initAttrs(attrs, defStyleAttr);
    }
    private void initAttrs(AttributeSet attrs, int defStyleAttr){
        if (attrs == null){return;}
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EventsCard, defStyleAttr, 0);
        String dayText = typedArray.getString(R.styleable.EventsCard_dayText);
        binding.dayText.setText(dayText != null? dayText: "");

        String monthText = typedArray.getString(R.styleable.EventsCard_monthText);
        binding.monthText.setText(monthText != null? monthText: "");

        String titleText = typedArray.getString(R.styleable.EventsCard_titleText);
        binding.TitleEventText.setText(titleText != null? titleText: "");

        String descriptionText = typedArray.getString(R.styleable.EventsCard_descriptionText);
        binding.DescriptionEventText.setText(descriptionText != null? descriptionText: "");

        typedArray.recycle();
    }
    public void SetDayText(String text){
        binding.dayText.setText(text);
    }
    public void SetMonthText(String text){
        binding.monthText.setText(text);
    }
    public void SetTitleText(String text){
        binding.TitleEventText.setText(text);
    }
    public void SetDescriptionText(String text){
        binding.DescriptionEventText.setText(text);
    }

}
