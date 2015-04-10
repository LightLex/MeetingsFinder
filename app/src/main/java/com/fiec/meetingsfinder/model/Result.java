package com.fiec.meetingsfinder.model;

import android.graphics.PointF;

/**
 * Created by damato on 03/19/2015.
 */
public class Result {
    private String When;
    private String Where;
    private String Who;
    private String More;
    private String Speaker;
    private String Agenda;
    private PointF Points;
    private String RegistrationURL;

    public Result(String when, String where, String who, String more, String speaker, String agenda, PointF points, String registrationURL) {
        When = when;
        Where = where;
        Who = who;
        More = more;
        Speaker = speaker;
        Agenda = agenda;
        Points = points;
        RegistrationURL=registrationURL;
    }

    public String getRegistrationURL() {
        return RegistrationURL;
    }

    public void setRegistrationURL(String registrationURL) {
        RegistrationURL = registrationURL;
    }

    public String getWhen() {
        return When;
    }

    public void setWhen(String when) {
        When = when;
    }

    public String getWhere() {
        return Where;
    }

    public void setWhere(String where) {
        Where = where;
    }

    public String getWho() {
        return Who;
    }

    public void setWho(String who) {
        Who = who;
    }

    public String getMore() {
        return More;
    }

    public void setMore(String more) {
        More = more;
    }

    public String getSpeaker() {
        return Speaker;
    }

    public void setSpeaker(String speaker) {
        Speaker = speaker;
    }

    public String getAgenda() {
        return Agenda;
    }

    public void setAgenda(String agenda) {
        Agenda = agenda;
    }

    public PointF getPoints() {
        return Points;
    }

    public void setPoints(PointF points) {
        Points = points;
    }
}
