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
import java.util.List;

import com.MSSE.MobileDVR.datamodel.Channel;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 5/4/13
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelDataSource {
    private SQLiteDatabase database;
    private MobileDVRDataBaseHelper dbHelper;
    private Context context;
    public static final String CHANNELTABLE = "channel";
    public static final String COLUMN_ID = "_id";
    private static final int IDX_COLUMN_ID = 0;
    private static final String COLUMN_NUMBER = "number";
    private static final int IDX_COLUMN_NUMBER = 1;
    private static final String COLUMN_NAME = "name";
    private static final int IDX_COLUMN_NAME = 2;
    private String[] allColumns = { COLUMN_ID, COLUMN_NUMBER, COLUMN_NAME };

    public static final String TABLE_CREATE = "create table " + CHANNELTABLE +
            "(" + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NUMBER + " integer not null, " +
            COLUMN_NAME + " text);";

    public ChannelDataSource(Context context) {
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
        return (database.isOpen());
    }

    public Channel createChannel(int channelNumber, String channelName) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NUMBER, channelNumber);
        values.put(COLUMN_NAME, channelName);
        long insertId = database.insert(CHANNELTABLE, null, values);
        Cursor cursor = database.query(CHANNELTABLE, allColumns, COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Channel newChannel = cursorToChannel(cursor);
        cursor.close();
        return newChannel;
    }

    public void deleteChannel(Channel channel) {
        database.delete(CHANNELTABLE, COLUMN_ID + " = " + channel.getId(), null);
    }

    public Channel getChannelById(long id) {
        Channel channel;
        Cursor cursor = database.query(CHANNELTABLE, allColumns, COLUMN_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            channel = cursorToChannel(cursor);
        } else {
            channel = null;
        }
        cursor.close();
        return channel;
    }

    public Channel getChannelByNumber(int channelNumber) {
        Channel channel;
        Cursor cursor = database.query(CHANNELTABLE, allColumns, COLUMN_NUMBER + " = " + channelNumber, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            channel = cursorToChannel(cursor);
        } else {
            channel = null;
        }
        cursor.close();
        return channel;
    }

    public Channel getChannelByName(String name) {
        Channel channel;
        Cursor cursor = database.query(CHANNELTABLE, allColumns, COLUMN_NAME + " = " + name, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            channel = cursorToChannel(cursor);
        } else {
            channel = null;
        }
        cursor.close();
        return channel;
    }

    public List<Channel> getChannelList() {
        List<Channel> channelList = new ArrayList<Channel>();
        Cursor cursor = database.query(CHANNELTABLE, allColumns, null, null, null, null, COLUMN_NUMBER + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Channel channel = cursorToChannel(cursor);
            channelList.add(channel);
            cursor.moveToNext();
        }
        cursor.close();
        return channelList;
    }

    private Channel cursorToChannel(Cursor cursor) {
        long channelId = cursor.getLong(IDX_COLUMN_ID);
        int channelNumber = cursor.getInt(IDX_COLUMN_NUMBER);
        String channelName = cursor.getString(IDX_COLUMN_NAME);
        Channel newChannel = new Channel(channelId, channelNumber, channelName);
        return newChannel;
    }
}
