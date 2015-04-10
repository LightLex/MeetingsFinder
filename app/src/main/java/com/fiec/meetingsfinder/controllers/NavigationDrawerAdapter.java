package com.fiec.meetingsfinder.controllers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fiec.meetingsfinder.R;
import com.fiec.meetingsfinder.model.DrawerMenu;
import com.fiec.meetingsfinder.model.Meetings;
import com.fiec.meetingsfinder.ui.AboutFragment;
import com.fiec.meetingsfinder.ui.ActivityMeetings;
import com.fiec.meetingsfinder.ui.FragmentHelp;
import com.fiec.meetingsfinder.ui.SettingFragment;

/**
 * Created by damato on 03/17/2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.RecyclerViewHolder> {
    private DrawerMenu[] menu;
    private ActivityMeetings act;

    public NavigationDrawerAdapter(ActivityMeetings act){
        this.act = act;
    }

    public void setData(DrawerMenu[] menu){
        this.menu = menu;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_menu,parent,false);
        RecyclerViewHolder vh = new RecyclerViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.image.setImageDrawable(menu[position].getImage());
        holder.text.setText(menu[position].getText());
    }

    @Override
    public int getItemCount() {
        return menu.length;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView image;
        private TextView text;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.menuimage);
            text = (TextView) itemView.findViewById(R.id.menutext);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (getPosition()){
                case 0:
                    act.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new ActivityMeetings.FragmentMeetings()).commit();
                    break;
                case 1:
                    act.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,new SettingFragment()).commit();
                    break;
                case 2:
                    act.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,new FragmentHelp()).commit();
                    break;
                case 3:
                    act.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new AboutFragment()).commit();
                    break;
            }
            act.closeDrawer();
        }
    }
}
