package com.livlypuer.popava;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.livlypuer.popava.db.DBManager;
import com.livlypuer.popava.models.Person;

import java.util.ArrayList;
import java.util.List;


public class ContactFragment extends Fragment {

    DBManager mDBConnector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        mDBConnector = new DBManager(getActivity());

        List<Person> teachers = mDBConnector.selectAllTeachers();

        return view;
    }
}