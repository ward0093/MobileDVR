package com.MSSE.MobileDVR.datasource.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.MSSE.MobileDVR.MainActivity;
import com.MSSE.MobileDVR.TabMainActivity;
import com.MSSE.MobileDVR.datamodel.Channel;
import com.MSSE.MobileDVR.datamodel.ShowInfo;
import com.MSSE.MobileDVR.datamodel.ShowTimeSlot;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 5/4/13
 * Time: 10:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowTimeSlotDataSource {
    private SQLiteDatabase database;
    private MobileDVRDataBaseHelper dbHelper;
    private Context context;
    public static final String SHOWTIMESLOTTABLE = "show_time_slot";
    //private static final String COLUMN_ID = "_id";
    //private static final int IDX_COLUMN_ID = 0;
    public static final String COLUMN_CHANNEL_ID = "channelId";
    private static final int IDX_COLUMN_CHANNEL_ID = 0;
    private static final String COLUMN_SHOW_INFO_ID = "showInfoId";
    private static final int IDX_COLUMN_SHOW_INFO_ID = 1;
    public static final String COLUMN_START_TIME = "startTime";
    private static final int IDX_COLUMN_START_TIME = 2;
    private static final String COLUMN_DURATION = "duration";
    private static final int IDX_COLUMN_DURATION = 3;
    private String[] allColumns = { COLUMN_CHANNEL_ID, COLUMN_SHOW_INFO_ID, COLUMN_START_TIME, COLUMN_DURATION };

    public static final String TABLE_CREATE = "create table " + SHOWTIMESLOTTABLE +
            "(" + COLUMN_CHANNEL_ID + " integer not null, " +
            COLUMN_SHOW_INFO_ID + " integer not null, " +
            COLUMN_START_TIME + " integer not null, " +
            COLUMN_DURATION + " integer, " +
            " FOREIGN KEY (" + COLUMN_CHANNEL_ID + ") REFERENCES " + ChannelDataSource.CHANNELTABLE + " (" + ChannelDataSource.COLUMN_ID + ")," +
            " FOREIGN KEY (" + COLUMN_SHOW_INFO_ID + ") REFERENCES " + ShowInfoDataSource.SHOWINFOTABLE + " (" + ShowInfoDataSource.COLUMN_ID + ")," +
            " PRIMARY KEY (" + COLUMN_CHANNEL_ID + ", " + COLUMN_START_TIME + "));";

    public ShowTimeSlotDataSource(Context context) {
        dbHelper = MobileDVRDataBaseHelper.getInstance(context);
        context = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean isOpen() {
        if (database == null)
            return false;
        else
            return (database.isOpen());
    }

    public ShowTimeSlot createShowTimeSlot(Channel channel, ShowInfo showInfo, Date startTime, int durationMinutes) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHANNEL_ID, channel.getId());
        values.put(COLUMN_SHOW_INFO_ID, showInfo.getId());
        values.put(COLUMN_START_TIME, startTime.getTime());
        values.put(COLUMN_DURATION, durationMinutes);
        long insertId = database.insert(SHOWTIMESLOTTABLE, null, values);
        Cursor cursor = database.query(SHOWTIMESLOTTABLE, allColumns, "(" + COLUMN_CHANNEL_ID + " = " + channel.getId() + ") AND (" +
                COLUMN_SHOW_INFO_ID + " = " + showInfo.getId() + ")", null, null, null, null);
        cursor.moveToFirst();
        ShowTimeSlot newShowTimeSlot = cursorToShowTimeSlot(cursor);
        cursor.close();
        return newShowTimeSlot;
    }

    public void deleteShowInfo(ShowTimeSlot showTimeSlot) {
        long channelId = showTimeSlot.getChannel().getId();
        Date startTime = showTimeSlot.getStartTime();
        database.delete(SHOWTIMESLOTTABLE, COLUMN_CHANNEL_ID + " = " + channelId + " AND " + IDX_COLUMN_START_TIME + " = " + startTime.getTime(), null);
    }

    public ShowTimeSlot getShowTimeSlot(Channel channel, Date startTime) {
        ShowTimeSlot showTimeSlot;
        long channelId = channel.getId();
        Cursor cursor = database.query(SHOWTIMESLOTTABLE, allColumns,
                COLUMN_CHANNEL_ID + " = " + channelId + " AND " + COLUMN_START_TIME + " = " + startTime.getTime(),
                null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            showTimeSlot = cursorToShowTimeSlot(cursor);
        } else {
            showTimeSlot = null;
        }
        cursor.close();
        return showTimeSlot;
    }

    public ShowTimeSlot getShowTimeSlot(long channelId, Date startTime) {
        Channel channel = TabMainActivity.getChannelDB().getChannelById(channelId);
        return getShowTimeSlot(channel, startTime);
    }

    public List<ShowTimeSlot> getShowTimeSlotList() {
        List<ShowTimeSlot> showTimeSlotList = new ArrayList<ShowTimeSlot>();
        Cursor cursor = database.query(SHOWTIMESLOTTABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ShowTimeSlot showTimeSlot = cursorToShowTimeSlot(cursor);
            showTimeSlotList.add(showTimeSlot);
            cursor.moveToNext();
        }
        cursor.close();
        return showTimeSlotList;
    }

    private ShowTimeSlot cursorToShowTimeSlot(Cursor cursor) {
        long channelId = cursor.getLong(IDX_COLUMN_CHANNEL_ID);
        Channel channel = MainActivity.getChannelDB().getChannelById(channelId);
        long showInfoId = cursor.getLong(IDX_COLUMN_SHOW_INFO_ID);
        ShowInfo showInfo = MainActivity.getShowInfoDB().getShowInfo(showInfoId);
        Date startTime = new Date(cursor.getLong(IDX_COLUMN_START_TIME));
        int durationMinutes = cursor.getInt(IDX_COLUMN_DURATION);
        ShowTimeSlot newShowTimeSlot = new ShowTimeSlot(showInfo, channel, startTime, durationMinutes);
        return newShowTimeSlot;
    }
}
