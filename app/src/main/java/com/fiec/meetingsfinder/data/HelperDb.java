package com.fiec.meetingsfinder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by damato on 03/18/2015.
 */
public class HelperDb extends SQLiteOpenHelper {

    public static final String TableMeeting="Meetings";
    //campos
    public static final String MID="mid";
    public static final String Title = "title";
    public static final String Region = "region";
    public static final String Section= "section";
    public static final String OrgUnit= "org_unit";
    public static final String Virtual= "virtual";
    public static final String Category= "category";
    public static final String SubCategory= "sub_category";
    public static final String Society= "society";
    public static final String Description= "description";
    public static final String Date= "date";
    public static final String Time= "time";
    public static final String Timezone= "timezone";
    public static final String Address= "address";
    public static final String Building= "building";
    public static final String Contact= "contact";
    public static final String Longitude= "longitude";
    public static final String Latitude= "latitude";
    public static final String AdditionalInfo= "additional_info";
    public static final String Speaker= "speaker";
    public static final String Agenda= "agenda";


    public static String DBNAME = "meetings.db";
    public static int VERSION = 1;

    //TODO: pasar create al formato de daniel con las variables

    public static final String CreateTableMeeting="CREATE TABLE Meetings (" +
            "   mid INT PRIMARY KEY AUTOINCREMENT" +
            "   title TEXT NOT NULL," +
            "   region INT NOT NULL," +
            "   section TEXT NOT NULL," +
            "   org_unit TEXT NULL," +
            "   virtual INT NULL," +
            "   category TEXT NULL," +
            "   sub_category TEXT NULL," +
            "   society TEXT NULL," +
            "   description TEXT NULL," +
            "   date TEXT NULL," +
            "   time TEXT NULL," +
            "   timezone TEXT NULL," +
            "   address TEXT NULL," +
            "   building TEXT NULL," +
            "   contact TEXT NULL," +
            "   longitude REAL NULL," +
            "   latitude REAL NULL," +
            "   additional_info TEXT NULL," +
            "   speaker TEXT NULL," +
            "   agenda TEXT NULL," +
            ");";

    public HelperDb(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTableMeeting);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
