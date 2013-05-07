package com.MSSE.MobileDVR.datasource.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.MSSE.MobileDVR.TabMainActivity;
import com.MSSE.MobileDVR.datamodel.Channel;
import com.MSSE.MobileDVR.datamodel.ScheduledRecording;
import com.MSSE.MobileDVR.datamodel.ShowInfo;
import com.MSSE.MobileDVR.datamodel.ShowTimeSlot;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 5/5/13
 * Time: 10:26 PM
 * To change this template use File | Settings | File Templates.
 * 	private boolean recurring;
 private int minutesBefore;
 private int minutesAfter;
 private int showsToKeep;
 private Date keepUntil;
 private ShowInfo showInfo;
 private ShowTimeSlot originalAirtime;
 private int id;
 */
public class ScheduledRecordingDataSource {
    private SQLiteDatabase database;
    private MobileDVRDataBaseHelper dbHelper;
    private Context context;
    public static final String SCHEDULEDRECORDINGTABLE = "scheduled_recording";
    public static final String COLUMN_ID = "_id";
    private static final int IDX_COLUMN_ID = 0;
    private static final String COLUMN_SHOWINFO_ID = "show_info_id";
    private static final int IDX_COLUMN_SHOWINFO_ID = 1;
    private static final String COLUMN_CHANNEL_ID = "channel_id";
    private static final int IDX_COLUMN_CHANNEL_ID = 2;
    private static final String COLUMN_ORIGSTARTTIME = "original_start_time";
    private static final int IDX_COLUMN_ORIGSTARTTIME = 3;
    private static final String COLUMN_KEEPUNTIL = "keep_until";
    private static final int IDX_COLUMN_KEEPUNTIL = 4;
    private static final String COLUMN_SHOWSTOKEEP = "shows_to_keep";
    private static final int IDX_COLUMN_SHOWSTOKEEP = 5;
    private static final String COLUMN_MINUTESBEFORE = "minutes_before";
    private static final int IDX_COLUMN_MINUTESBEFORE = 6;
    private static final String COLUMN_MINUTESAFTER = "minutes_after";
    private static final int IDX_COLUMN_MINUTESAFTER = 7;
    private static final String COLUMN_RECURRING = "recurring";
    private static final int IDX_COLUMN_RECURRING = 8;
    private String[] allColumns = { COLUMN_ID, COLUMN_SHOWINFO_ID, COLUMN_CHANNEL_ID, COLUMN_ORIGSTARTTIME,
            COLUMN_KEEPUNTIL, COLUMN_SHOWSTOKEEP, COLUMN_MINUTESBEFORE, COLUMN_MINUTESAFTER, COLUMN_RECURRING };

    public static final String TABLE_CREATE = "create table " + SCHEDULEDRECORDINGTABLE +
            "(" + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_SHOWINFO_ID + " integer not null, " +
            COLUMN_CHANNEL_ID + " integer not null, " +
            COLUMN_ORIGSTARTTIME + " integer not null, " +
            COLUMN_KEEPUNTIL + " integer, " +
            COLUMN_SHOWSTOKEEP + " integer, " +
            COLUMN_MINUTESBEFORE + " integer, " +
            COLUMN_MINUTESAFTER + " integer, " +
            COLUMN_RECURRING + " integer not null, " +
            " FOREIGN KEY (" + COLUMN_CHANNEL_ID + ") REFERENCES " + ShowTimeSlotDataSource.SHOWTIMESLOTTABLE + " (" + ShowTimeSlotDataSource.COLUMN_CHANNEL_ID + ")," +
            " FOREIGN KEY (" + COLUMN_SHOWINFO_ID + ") REFERENCES " + ShowInfoDataSource.SHOWINFOTABLE + " (" + ShowInfoDataSource.COLUMN_ID + ")," +
            " FOREIGN KEY (" + COLUMN_ORIGSTARTTIME + ") REFERENCES " + ShowTimeSlotDataSource.SHOWTIMESLOTTABLE + " (" + ShowTimeSlotDataSource.COLUMN_START_TIME + "));";

    public ScheduledRecordingDataSource(Context context) {
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

    public ScheduledRecording createScheduledRecording(ShowInfo showInfo, ShowTimeSlot showTimeSlot, Date keepUntil,
                                                                 int showsToKeep, int minutesBefore, int minutesAfter, boolean recurring) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SHOWINFO_ID, showInfo.getId());
        values.put(COLUMN_CHANNEL_ID, showTimeSlot.getChannel().getId());
        values.put(COLUMN_ORIGSTARTTIME, showTimeSlot.getStartTime().getTime());
        values.put(COLUMN_KEEPUNTIL, keepUntil.getTime());
        values.put(COLUMN_SHOWSTOKEEP, showsToKeep);
        values.put(COLUMN_MINUTESBEFORE, minutesBefore);
        values.put(COLUMN_MINUTESAFTER, minutesAfter);
        values.put(COLUMN_RECURRING, (recurring) ? 1 : 0);
        long insertId = database.insert(SCHEDULEDRECORDINGTABLE, null, values);
        Cursor cursor = database.query(SCHEDULEDRECORDINGTABLE, allColumns, COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        ScheduledRecording newScheduledRec = cursorToScheduledRec(cursor);
        cursor.close();
        return newScheduledRec;
    }

    public ScheduledRecording createScheduledRecording(ScheduledRecording tempSchedRec) {
        return createScheduledRecording(tempSchedRec.getShowInfo(), tempSchedRec.getOriginalAirtime(), tempSchedRec.getKeepUntil(),
                tempSchedRec.getShowsToKeep(), tempSchedRec.getMinutesBefore(), tempSchedRec.getMinutesAfter(),
                tempSchedRec.getRecurring());
    }

    public void deleteShowInfo(ScheduledRecording schedRec) {
        database.delete(SCHEDULEDRECORDINGTABLE, COLUMN_ID + " = " + schedRec.getId(), null);
    }

    public ScheduledRecording getScheduledRecording(long id) {
        ScheduledRecording schedRec;
        Cursor cursor = database.query(SCHEDULEDRECORDINGTABLE, allColumns, COLUMN_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            schedRec = cursorToScheduledRec(cursor);
        } else {
            schedRec = null;
        }
        cursor.close();
        return schedRec;
    }

    public List<ScheduledRecording> getScheduledRecordingList() {
        List<ScheduledRecording> schedRecList = new ArrayList<ScheduledRecording>();
        Cursor cursor = database.query(SCHEDULEDRECORDINGTABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ScheduledRecording schedRec = cursorToScheduledRec(cursor);
            schedRecList.add(schedRec);
            cursor.moveToNext();
        }
        cursor.close();
        return schedRecList;
    }

    private ScheduledRecording cursorToScheduledRec(Cursor cursor) {
        long schedRecId = cursor.getLong(IDX_COLUMN_ID);
        long showInfoId = cursor.getLong(IDX_COLUMN_SHOWINFO_ID);
        ShowInfo showInfo = TabMainActivity.getShowInfoDB().getShowInfo(showInfoId);
        long channelId = cursor.getLong(IDX_COLUMN_CHANNEL_ID);
        Date startTime = new Date(cursor.getLong(IDX_COLUMN_ORIGSTARTTIME));
        ShowTimeSlot showTimeSlot = TabMainActivity.getShowTimeSlotDB().getShowTimeSlot(channelId, startTime);
        Date keepUntil = new Date(cursor.getLong(IDX_COLUMN_KEEPUNTIL));
        int showsToKeep = cursor.getInt(IDX_COLUMN_SHOWSTOKEEP);
        int minutesBefore = cursor.getInt(IDX_COLUMN_MINUTESBEFORE);
        int minutesAfter = cursor.getInt(IDX_COLUMN_MINUTESAFTER);
        boolean recurring = cursor.getInt(IDX_COLUMN_RECURRING) != 0;

        ScheduledRecording schedRec = new ScheduledRecording(schedRecId, showInfo, showTimeSlot, keepUntil, recurring,
                minutesBefore, minutesAfter, showsToKeep);
        return schedRec;
    }
}
