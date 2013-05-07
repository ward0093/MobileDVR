package com.MSSE.MobileDVR.datasource.sql;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 5/4/13
 * Time: 6:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileDVRDataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mobileDVR.db";
    private static final int DATABASE_VERSION = 1;

    public static final String SHOWTIMESLOTTABLE = "showtimeslot";
    public static final String SCHEDULEDRECTABLE = "scheduledrecording";

    private static MobileDVRDataBaseHelper instance = null;

    private MobileDVRDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static MobileDVRDataBaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new MobileDVRDataBaseHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("PRAGMA foreign_keys = ON;");
        database.execSQL(ShowInfoDataSource.TABLE_CREATE);
        database.execSQL(ChannelDataSource.TABLE_CREATE);
        database.execSQL(ShowTimeSlotDataSource.TABLE_CREATE);
        database.execSQL(ScheduledRecordingDataSource.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ShowInfoDataSource.SHOWINFOTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ChannelDataSource.CHANNELTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ShowTimeSlotDataSource.SHOWTIMESLOTTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ScheduledRecordingDataSource.SCHEDULEDRECORDINGTABLE);
        onCreate(db);
    }
}
