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
 * Created by Mukei on 9/4/15.
 */
public class FragmentHelp extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.txthelp));
            View view = inflater.inflate(R.layout.fragment_help, container, false);
            TextView t = (TextView) view.findViewById(R.id.textViewHelp);
            t.setText (Html.fromHtml(getString(R.string.help)));
            return view;

        }



}
