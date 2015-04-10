package com.fiec.meetingsfinder.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fiec.meetingsfinder.R;

/**
 * Created by Alexey on 09.04.2015.
 */
public class AboutFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.txtabout));
        return view;

    }
}
