package com.MSSE.MobileDVR.datasource.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Switch;
import com.MSSE.MobileDVR.TabMainActivity;
import com.MSSE.MobileDVR.datamodel.Channel;
import com.MSSE.MobileDVR.datamodel.RecordedShow;
import com.MSSE.MobileDVR.datamodel.ShowInfo;
import com.MSSE.MobileDVR.datamodel.ShowTimeSlot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 5/8/13
 * Time: 12:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class RecordedShowDataSource {
    private SQLiteDatabase database;
    private MobileDVRDataBaseHelper dbHelper;
    private Context context;
    public static final String RECORDEDSHOWTABLE = "recordedShow";
    public static final String COLUMN_ID = "_id";
    private static final int IDX_COLUMN_ID = 0;
    public static final String COLUMN_CHANNEL_ID = "channelId";
    private static final int IDX_COLUMN_CHANNEL_ID = 1;
    private static final String COLUMN_SHOW_INFO_ID = "showInfoId";
    private static final int IDX_COLUMN_SHOW_INFO_ID = 2;
    public static final String COLUMN_START_TIME = "startTime";
    private static final int IDX_COLUMN_START_TIME = 3;
    private static final String COLUMN_KEEPUNTIL = "keepUntil";
    private static final int IDX_COLUMN_KEEPUNTIL = 4;
    private String[] allColumns = { COLUMN_ID, COLUMN_CHANNEL_ID, COLUMN_SHOW_INFO_ID, COLUMN_START_TIME, COLUMN_KEEPUNTIL };

    public static final String TABLE_CREATE = "create table " + RECORDEDSHOWTABLE +
            "(" + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_CHANNEL_ID + " integer not null, " +
            COLUMN_SHOW_INFO_ID + " integer not null, " +
            COLUMN_START_TIME + " integer not null, " +
            COLUMN_KEEPUNTIL + " integer, " +
            " FOREIGN KEY (" + COLUMN_CHANNEL_ID + ") REFERENCES " + ChannelDataSource.CHANNELTABLE + " (" + ChannelDataSource.COLUMN_ID + ")," +
            " FOREIGN KEY (" + COLUMN_SHOW_INFO_ID + ") REFERENCES " + ShowInfoDataSource.SHOWINFOTABLE + " (" + ShowInfoDataSource.COLUMN_ID + ")," +
            " FOREIGN KEY (" + COLUMN_START_TIME + ") REFERENCES " + ShowTimeSlotDataSource.SHOWTIMESLOTTABLE + " (" + ShowTimeSlotDataSource.COLUMN_START_TIME + "));";

    public RecordedShowDataSource(Context context) {
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

    public RecordedShow createRecordedShow(ShowInfo showInfo, ShowTimeSlot showTimeSlot, Date keepUntil) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHANNEL_ID, showTimeSlot.getChannel().getId());
        values.put(COLUMN_SHOW_INFO_ID, showInfo.getId());
        values.put(COLUMN_START_TIME, showTimeSlot.getStartTime().getTime());
        values.put(COLUMN_KEEPUNTIL, keepUntil.getTime());
        long insertId = database.insert(RECORDEDSHOWTABLE, null, values);
        Cursor cursor = database.query(RECORDEDSHOWTABLE, allColumns, COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        RecordedShow recordedShow = cursorToRecordedShow(cursor);
        cursor.close();
        return recordedShow;
    }

    public void deleteRecordedShow(RecordedShow recordedShow) {
        database.delete(RECORDEDSHOWTABLE, COLUMN_ID + " = " + recordedShow.getId(), null);
    }

    public RecordedShow getRecordedShowById(long id) {
        RecordedShow recordedShow = null;
        Cursor cursor = database.query(RECORDEDSHOWTABLE, allColumns, COLUMN_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            recordedShow = cursorToRecordedShow(cursor);
        }
        cursor.close();
        return recordedShow;
    }

    public List<RecordedShow> getRecordedListByShowInfoId(long showInfoId) {
        List<RecordedShow> recordedShowList = new ArrayList<RecordedShow>();
        Cursor cursor = database.query(RECORDEDSHOWTABLE, allColumns, COLUMN_SHOW_INFO_ID + " = " + showInfoId,
                null, null, null, COLUMN_START_TIME + " ASC, " + COLUMN_CHANNEL_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            recordedShowList.add(cursorToRecordedShow(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        if (recordedShowList.isEmpty()) {
            return recordedShowList;
        } else {
            return null;
        }
    }


    public List<RecordedShow> getRecordedShowList(int column) {
        String columnString;
        switch(column) {
            case IDX_COLUMN_CHANNEL_ID:
                columnString = COLUMN_CHANNEL_ID;
                break;
            case IDX_COLUMN_SHOW_INFO_ID:
                columnString = COLUMN_SHOW_INFO_ID;
                break;
            case IDX_COLUMN_START_TIME:
                columnString = COLUMN_START_TIME;
                break;
            case IDX_COLUMN_KEEPUNTIL:
                columnString = COLUMN_KEEPUNTIL;
                break;
            case IDX_COLUMN_ID:
                columnString = COLUMN_ID;
                break;
            default:
                columnString = COLUMN_ID;
                break;
        }
        List<RecordedShow> recordedShowList = new ArrayList<RecordedShow>();
        Cursor cursor = database.query(RECORDEDSHOWTABLE, allColumns, null, null, null, null, columnString + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            recordedShowList.add(cursorToRecordedShow(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        if (!recordedShowList.isEmpty())
            return recordedShowList;
        else
            return null;
    }

    public List<RecordedShow> getRecordedShowList() {
        return getRecordedShowList(IDX_COLUMN_ID);
    }

    private RecordedShow cursorToRecordedShow(Cursor cursor) {
        long recordedShowId = cursor.getLong(IDX_COLUMN_ID);
        long channelId = cursor.getLong(IDX_COLUMN_CHANNEL_ID);
        long showInfoId = cursor.getLong(IDX_COLUMN_SHOW_INFO_ID);
        Date origStartTime = new Date(cursor.getLong(IDX_COLUMN_START_TIME));
        Date keepUntil = null;
        if (!cursor.isNull(IDX_COLUMN_KEEPUNTIL))
            keepUntil = new Date(cursor.getLong(IDX_COLUMN_KEEPUNTIL));
        ShowTimeSlot origAirTime = TabMainActivity.getShowTimeSlotDB().getShowTimeSlot(channelId, origStartTime);
        ShowInfo showInfo = TabMainActivity.getShowInfoDB().getShowInfo(showInfoId);
        RecordedShow recordedShow = new RecordedShow(recordedShowId, showInfo, origAirTime, keepUntil);
        return recordedShow;
    }
}
