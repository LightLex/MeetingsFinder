package com.fiec.meetingsfinder.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.fiec.meetingsfinder.model.Meetings;


/**
 * Created by damato on 03/18/2015.
 */
public class Calldb {

    private SQLiteDatabase db;
    private HelperDb helper;

    public Calldb(Context context) {
        helper = new HelperDb(context);
    }

    public void open() {
        try {
            db = helper.getReadableDatabase();
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        db.close();
    }

    //TODO: delete/drop

    public boolean insertMeeting(Meetings m) {
        //no se necesita poner que campos si se llenan todos, solo deben estar en orden
        db.execSQL("insert into Meetings values (" + m.getMid() +
                ", " + m.getTitle() +
                ", " + m.getRegion() +
                ", " + m.getSection() +
                ", " + m.getOrg_unit() +
                ", " + m.isVirtual() +
                ", " + m.getCategory() +
                ", " + m.getSub_category() +
                ", " + m.getSociety() +
                ", " + m.getDescription() +
                ", " + m.getDate() +
                ", " + m.getTime() +
                ", " + m.getTimezone() +
                ", " + m.getAddress() +
                ", " + m.getBuilding() +
                ", " + m.getContact() +
                ", " + m.getLongitude() +
                ", " + m.getLatitude() +
                ", " + m.getAdditional_info() +
                ", " + m.getSpeaker() +
                ", " + m.getAgenda() +
                ")");
        return true;
    }

    public boolean updateMeeting(Meetings m) {
        db.execSQL("update Meetings set " +
                HelperDb.MID + "=" + m.getMid() +
                ", " + HelperDb.Title + "=" + m.getTitle() +
                ", " + HelperDb.Region + "=" + m.getRegion() +
                ", " + HelperDb.Section + "=" + m.getSection() +
                ", " + HelperDb.OrgUnit + "=" + m.getOrg_unit() +
                ", " + HelperDb.Virtual + "=" + m.isVirtual() +
                ", " + HelperDb.Category + "=" + m.getCategory() +
                ", " + HelperDb.SubCategory + "=" + m.getSub_category() +
                ", " + HelperDb.Society + "=" + m.getSociety() +
                ", " + HelperDb.Description + "=" + m.getDescription() +
                ", " + HelperDb.Date + "=" + m.getDate() +
                ", " + HelperDb.Time + "=" + m.getTime() +
                ", " + HelperDb.Timezone + "=" + m.getTimezone() +
                ", " + HelperDb.Address + "=" + m.getAddress() +
                ", " + HelperDb.Building + "=" + m.getBuilding() +
                ", " + HelperDb.Contact + "=" + m.getContact() +
                ", " + HelperDb.Longitude + "=" + m.getLongitude() +
                ", " + HelperDb.Latitude + "=" + m.getLatitude() +
                ", " + HelperDb.AdditionalInfo + "=" + m.getAdditional_info() +
                ", " + HelperDb.Speaker + "=" + m.getSpeaker() +
                ", " + HelperDb.Agenda + "=" + m.getAgenda() +
                "where mid="+m.getMid()+";");
        return true;
    }


    public Meetings getMeetingByMID(int mid) {
        Cursor cursor = db.query("Meetings", null, "mid="
                + mid, null, null, null, null);
        return meetingCreator(cursor)[0];
    }

    public Meetings[] getMeetings(int amount) {
        Cursor cursor = db.query("Meetings", null, null, null, null, null, null, "" + amount);
        return meetingCreator(cursor);
    }

    public Meetings[] getAllMeetings() {
        Cursor cursor = db.query("Meetings", null, null, null, null, null, null);
        return meetingCreator(cursor);
    }


    public Meetings[] meetingCreator(Cursor c) {
        int row_count=c.getCount();
        if (row_count > 0) {
            Meetings[] result = new Meetings[row_count];
            int i = 0;
            while (c.moveToNext()) {
                result[i++] = new Meetings(
                        c.getInt(c.getColumnIndex("mid")),
                        c.getString(c.getColumnIndex("title")),
                        c.getInt(c.getColumnIndex("region")),
                        c.getString(c.getColumnIndex("section")),
                        c.getString(c.getColumnIndex("org_unit")),
                        c.getInt(c.getColumnIndex("virtual")) != 0,
                        c.getString(c.getColumnIndex("category")),
                        c.getString(c.getColumnIndex("sub_category")),
                        c.getString(c.getColumnIndex("society")),
                        c.getString(c.getColumnIndex("description")),
                        c.getString(c.getColumnIndex("date")),
                        c.getString(c.getColumnIndex("time")),
                        c.getString(c.getColumnIndex("timezone")),
                        c.getString(c.getColumnIndex("address")),
                        c.getString(c.getColumnIndex("building")),
                        c.getString(c.getColumnIndex("contact")),
                        c.getFloat(c.getColumnIndex("longitude")),
                        c.getFloat(c.getColumnIndex("latitude")),
                        c.getString(c.getColumnIndex("additional_info")),
                        c.getString(c.getColumnIndex("speaker")),
                        c.getString(c.getColumnIndex("agenda"))
                );
            }
            return result;
        } else
            return null;
    }


}
