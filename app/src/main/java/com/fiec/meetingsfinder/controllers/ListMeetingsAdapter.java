package com.fiec.meetingsfinder.controllers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fiec.meetingsfinder.R;
import com.fiec.meetingsfinder.model.MeetingSearch;
import com.fiec.meetingsfinder.ui.ActivityMap;
import com.fiec.meetingsfinder.ui.ActivityMeetings;
import com.fiec.meetingsfinder.util.Globals;

import java.util.List;

/**
 * Created by damato on 04/08/2015.
 */
public class ListMeetingsAdapter extends RecyclerView.Adapter<ListMeetingsAdapter.ViewHolder>{
    private List<MeetingSearch> meetings;
    private ActivityMeetings act;

    public ListMeetingsAdapter(List<MeetingSearch> meetings,ActivityMeetings act) {
        this.act = act;
        this.meetings = meetings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_meetings,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int color = Globals.getRandomColor();
        holder.color_shape.setColor(color);
        holder.bar.setBackgroundColor(color);
        holder.title.setText(meetings.get(position).getNAME());
        holder.date.setText(meetings.get(position).getDATE());
        holder.dateday.setText(meetings.get(position).getDATE().substring(0, 2).replaceAll(" ", ""));
        holder.datemon.setText(meetings.get(position).getDATE().substring(3, 6).replaceAll(" ", ""));
        holder.virtual.setText(meetings.get(position).getVIRTUAL());
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private LinearLayout bar;
        private GradientDrawable color_shape;
        private LayerDrawable bgdraw;
        private TextView title,date,virtual,dateday,datemon;
        private Button inscript,map;

        public ViewHolder(View itemView) {
            super(itemView);
            bar = (LinearLayout) itemView.findViewById(R.id.meet_barcolor);
            title = (TextView) itemView.findViewById(R.id.meettitle);
            dateday = (TextView) itemView.findViewById(R.id.txtday);
            bgdraw = (LayerDrawable)dateday.getBackground();
            color_shape = (GradientDrawable) bgdraw.findDrawableByLayerId(R.id.calendarcolor);
            datemon = (TextView) itemView.findViewById(R.id.txtmo);
            date = (TextView) itemView.findViewById(R.id.meetfecha);
            virtual = (TextView) itemView.findViewById(R.id.meetvirtual);
            map = (Button) itemView.findViewById(R.id.meetmap);
            map.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent tomap = new Intent(act, ActivityMap.class);
            tomap.putExtra(Globals.KEY_MEETINGID,meetings.get(getPosition()).getID());
            act.startActivity(tomap);
        }
    }

}
