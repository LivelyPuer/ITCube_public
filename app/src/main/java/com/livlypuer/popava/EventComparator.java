package com.livlypuer.popava;

import com.livlypuer.popava.models.EventModel;

import java.util.Comparator;

public class EventComparator implements Comparator<EventModel> {
    @Override
    public int compare(EventModel o1, EventModel o2) {
        return o1.compareTo(o2);
    }
}