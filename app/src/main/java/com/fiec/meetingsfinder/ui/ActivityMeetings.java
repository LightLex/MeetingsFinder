package com.fiec.meetingsfinder.ui;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fiec.meetingsfinder.R;
import com.fiec.meetingsfinder.controllers.ListMeetingsAdapter;
import com.fiec.meetingsfinder.model.MeetingSearch;
import com.fiec.meetingsfinder.service.Core;

import java.util.List;

/**
 * Created by damato on 03/17/2015.
 */
public class ActivityMeetings extends ActionBarActivity {

    private Toolbar toolbar;
    private FragmentNavigationDrawer drawer;
    private FragmentMeetings meetings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meetings);

        PreferenceManager.setDefaultValues(this, getResources().getString(R.string.prefname),
                MODE_PRIVATE, R.xml.meetings, false);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(R.string.maintitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (FragmentNavigationDrawer) getSupportFragmentManager().findFragmentById(R.id.drawerfragment);
        drawer.setUp((DrawerLayout) findViewById(R.id.drawer_layout), R.id.drawerfragment, toolbar);

        if(meetings==null){
            meetings = new FragmentMeetings();
            getSupportFragmentManager().beginTransaction().add(R.id.container,meetings).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meetings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    public void closeDrawer(){
        drawer.close();
    }

    public static class FragmentMeetings extends Fragment implements AdapterView.OnItemSelectedListener, OnFindSections{
        private RecyclerView recyclerView;
        private SharedPreferences pref;
        private String[] values;
        public FragmentMeetings(){}
        private TextView nomeetings;
        private SwipeRefreshLayout swipe;
        private Spinner regions,sections;


        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_meetings,container,false);
            //searchmeetings = (TextView) v.findViewById(R.id.textsearchmeetings);
            pref = getActivity().getSharedPreferences(
                    getResources().getString(R.string.prefname), MODE_PRIVATE);

            ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.txthome));
            swipe = (SwipeRefreshLayout) v.findViewById(R.id.ini_swipe);
            swipe.setEnabled(false);
            swipe.setColorSchemeResources(R.color.sw1, R.color.sw2, R.color.sw3, R.color.sw4);
            nomeetings = (TextView) v.findViewById(R.id.nomeetings);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclermain);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            if(pref.getString(getResources().getString(R.string.prefregion),"").equals("")){
                welcome();
            }
            else{
                refresh();
            }
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    int istop = (recyclerView==null || recyclerView.getChildCount() == 0) ?
                            0 : recyclerView.getChildAt(0).getTop();
                    swipe.setEnabled(istop >= 0);
                }
            });
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipe.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refresh();
                            swipe.setRefreshing(false);
                        }
                    }, 7000);
                }
            });
            return v;
        }

        private void refresh(){
            String region = pref.getString(getResources().getString(R.string.prefregion),"");
            String section = pref.getString(getResources().getString(R.string.prefsection),"");
            new Core(FragmentMeetings.this,region,section);
        }
        private void welcome(){
            final Dialog dialog = new Dialog(getActivity(),R.style.welcome);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.weolcome_layout);
            regions = (Spinner) dialog.findViewById(R.id.spinnerregions);
            sections = (Spinner) dialog.findViewById(R.id.spinnersections);
            ArrayAdapter<String> adapterRegions = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.regions));
            regions.setAdapter(adapterRegions);
            regions.setOnItemSelectedListener(this);
            sections.setOnItemSelectedListener(this);
            Button start = (Button) dialog.findViewById(R.id.dialogstart);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(values!=null){
                        if(!values[sections.getSelectedItemPosition()].equals("")){
                            pref.edit().putString(getResources().getString(R.string.prefregion),""+(regions.getSelectedItemPosition()+1))
                                    .putString(getResources().getString(R.string.prefsection), values[sections.getSelectedItemPosition()])
                                    .putString(getResources().getString(R.string.prefregionname),(String)regions.getSelectedItem())
                                    .putString(getResources().getString(R.string.prefsectionname),(String)sections.getSelectedItem())
                                    .apply();
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.errmsgregsec),Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getActivity(),getResources().getString(R.string.errmsgregsec),Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
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
            if(meetings!=null && meetings.size() > 0) {
                nomeetings.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(new ListMeetingsAdapter(meetings, ((ActivityMeetings) getActivity())));
            }
            else{
                nomeetings.setVisibility(View.VISIBLE);
            }
            //searchmeetings.setText(getResources().getString(R.string.txtfindmeetings));
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //searchmeetings.setText(getResources().getString(R.string.txtsearching));
            if(parent.getId()==R.id.spinnerregions)
                new Core(this,position+1);
            else
                new Core(this,regions.getSelectedItemPosition()+1+"",values[position]);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}
