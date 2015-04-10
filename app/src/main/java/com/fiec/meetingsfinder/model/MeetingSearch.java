package com.fiec.meetingsfinder.model;

/**
 * Created by damato on 04/07/2015.
 */
public class MeetingSearch {
    private String ID;
    private String NAME;
    private String DATE;
    private String VIRTUAL;

    public MeetingSearch(String ID, String NAME, String DATE, String VIRTUAL) {
        this.ID = ID;
        this.NAME = NAME;
        this.DATE = DATE;
        this.VIRTUAL = VIRTUAL;
    }

    public String getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getDATE() {
        return DATE;
    }

    public String getVIRTUAL() {
        return VIRTUAL;
    }
}
