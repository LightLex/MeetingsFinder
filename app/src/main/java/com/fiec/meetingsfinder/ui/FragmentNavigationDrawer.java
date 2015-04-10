package com.fiec.meetingsfinder.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fiec.meetingsfinder.R;
import com.fiec.meetingsfinder.controllers.NavigationDrawerAdapter;
import com.fiec.meetingsfinder.model.DrawerMenu;

/**
 * Created by damato on 03/17/2015.
 */
public class FragmentNavigationDrawer extends Fragment {

    private ActionBarDrawerToggle toogle;
    private DrawerLayout drawer;
    private View drawerView;

    private RecyclerView recyclerView;
    private NavigationDrawerAdapter adapter;


    public FragmentNavigationDrawer(){ /* NO ESCRIBIR CÓDIGO AQUÍ*/}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigationdrawer, container, false);
        String[] texts = getResources().getStringArray(R.array.menutexts);
        Drawable[] imgs = new Drawable[]{getResources().getDrawable(R.drawable.ic_home_grey600_24dp),
                getResources().getDrawable(R.drawable.ic_settings_grey600_24dp),
                getResources().getDrawable(R.drawable.ic_help_grey600_24dp),
                getResources().getDrawable(R.drawable.ic_info_grey600_24dp)};

        DrawerMenu[] drawerMenu = new DrawerMenu[texts.length];
        for(int i=0; i<texts.length; i++)
            drawerMenu[i] = new DrawerMenu(imgs[i],texts[i]);

        adapter = new NavigationDrawerAdapter(((ActivityMeetings)getActivity()));
        adapter.setData(drawerMenu);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    public void close(){
        drawer.closeDrawer(getView());
    }

    public void setUp(final DrawerLayout drawer, int drawerid, final Toolbar toolbar) {
        this.drawer = drawer;
        this.drawerView=getActivity().findViewById(drawerid);
        toogle=new ActionBarDrawerToggle(getActivity(),drawer,toolbar,R.string.drawer_open,
                R.string.app_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView,slideOffset);
                if(slideOffset < 0.6){
                    //toolbar.setAlpha(1 - slideOffset);
                }
            }
        };

        this.drawer.setDrawerListener(toogle);
        drawer.post(new Runnable() {
            @Override
            public void run() {
                toogle.syncState();
            }
        });
    }


}
