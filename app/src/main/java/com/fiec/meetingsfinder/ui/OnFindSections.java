package com.fiec.meetingsfinder.ui;

import android.os.Bundle;

import com.fiec.meetingsfinder.model.MeetingSearch;

import java.util.List;

/**
 * Created by damato on 04/04/2015.
 */
public interface OnFindSections {
    public abstract void listregions(Bundle bun);
    public abstract void listMeetings(List<MeetingSearch> meetings);
}
