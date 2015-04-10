package com.fiec.meetingsfinder.ui;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.fiec.meetingsfinder.R;
import com.fiec.meetingsfinder.model.MeetingSearch;
import com.fiec.meetingsfinder.service.Core;

import java.util.List;

/**
 * Created by Alexey on 09.04.2015.
 */
public class SettingFragment extends Fragment implements OnFindSections,View.OnClickListener,AdapterView.OnItemSelectedListener{
    private Spinner regions,sections;
    private SharedPreferences pref;
    private TextView region, section;
    private String[] values;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.txtsettings));
        pref = getActivity().getSharedPreferences(getResources().getString(R.string.prefname),ActionBarActivity.MODE_PRIVATE);

        region = (TextView) view.findViewById(R.id.region);
        section = (TextView) view.findViewById(R.id.section);
        Button change = (Button) view.findViewById(R.id.changeregsec);
        change.setOnClickListener(this);
        setRegec();
        return view;

    }

    private void setRegec() {
        region.setText(pref.getString(getResources().getString(R.string.prefregionname),""));
        section.setText(pref.getString(getResources().getString(R.string.prefsectionname),""));
    }

    @Override
    public void listregions(Bundle bun) {
        ArrayAdapter<String> adapterSections = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,bun.getStringArray("captions"));
        values = bun.getStringArray("values");
        this.sections.setAdapter(adapterSections);
    }

    @Override
    public void listMeetings(List<MeetingSearch> meetings) {

    }

    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle(getResources().getString(R.string.txtchangeregsec));
        dialog.setContentView(R.layout.dialog_changeregsec);
        regions = (Spinner) dialog.findViewById(R.id.spinnerregions);
        sections = (Spinner) dialog.findViewById(R.id.spinnersections);
        ArrayAdapter<String> adapterRegions = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.regions));
        regions.setAdapter(adapterRegions);
        regions.setOnItemSelectedListener(this);
        Button change = (Button) dialog.findViewById(R.id.buttonchange);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.edit().putString(getResources().getString(R.string.prefregion),""+(regions.getSelectedItemPosition()+1))
                        .putString(getResources().getString(R.string.prefsection), values[sections.getSelectedItemPosition()])
                        .putString(getResources().getString(R.string.prefregionname),(String)regions.getSelectedItem())
                        .putString(getResources().getString(R.string.prefsectionname), (String) sections.getSelectedItem())
                        .apply();
                setRegec();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        new Core(this,position+1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
