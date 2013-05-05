package com.MSSE.MobileDVR;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 5/4/13
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowInfoDataSource {
    private SQLiteDatabase database;
    private MobileDVRDataBaseHelper dbHelper;
    private Context context;
    public static final String SHOWINFOTABLE = "show_info";
    public static final String COLUMN_ID = "_id";
    private static final int IDX_COLUMN_ID = 0;
    private static final String COLUMN_TITLE = "title";
    private static final int IDX_COLUMN_TITLE = 1;
    private static final String COLUMN_DESCRIPTION = "description";
    private static final int IDX_COLUMN_DESCRIPTION = 2;
    private static final String COLUMN_IMAGE = "image";
    private static final int IDX_COLUMN_IMAGE = 3;
    private String[] allColumns = { COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_IMAGE };

    public static final String TABLE_CREATE = "create table " + SHOWINFOTABLE +
            "(" + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null, " +
            COLUMN_DESCRIPTION + " text, " +
            COLUMN_IMAGE + " blob);";

    public ShowInfoDataSource(Context context) {
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

    public ShowInfo createShowInfo(String title, String description, Drawable image) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        if (image != null) {
            Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            values.put(COLUMN_IMAGE, bitmapData);
        } else {
            values.putNull(COLUMN_IMAGE);
        }
        long insertId = database.insert(SHOWINFOTABLE, null, values);
        Cursor cursor = database.query(SHOWINFOTABLE, allColumns, COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        ShowInfo newShowInfo = cursorToShowInfo(cursor);
        cursor.close();
        return newShowInfo;
    }

    public void deleteShowInfo(ShowInfo showInfo) {
        database.delete(SHOWINFOTABLE, COLUMN_ID + " = " + showInfo.getId(), null);
    }

    public ShowInfo getShowInfo(long id) {
        ShowInfo showInfo;
        Cursor cursor = database.query(SHOWINFOTABLE, allColumns, COLUMN_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            showInfo = cursorToShowInfo(cursor);
        } else {
            showInfo = null;
        }
        cursor.close();
        return showInfo;
    }

    public List<ShowInfo> getShowInfoList() {
        List<ShowInfo> showInfoList = new ArrayList<ShowInfo>();
        Cursor cursor = database.query(SHOWINFOTABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ShowInfo showInfo = cursorToShowInfo(cursor);
            showInfoList.add(showInfo);
            cursor.moveToNext();
        }
        cursor.close();
        return showInfoList;
    }

    private ShowInfo cursorToShowInfo(Cursor cursor) {
        ShowInfo showInfo = new ShowInfo();
        showInfo.setId(cursor.getLong(IDX_COLUMN_ID));
        showInfo.setTitle(cursor.getString(IDX_COLUMN_TITLE));
        showInfo.setDescription(cursor.getString(IDX_COLUMN_DESCRIPTION));
        // Only get the image if it exists
        if (!cursor.isNull(IDX_COLUMN_IMAGE)) {
            byte[] imageBlob = cursor.getBlob(IDX_COLUMN_IMAGE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
            Drawable image = new BitmapDrawable(context.getResources(), bitmap);
            showInfo.setImage(image);
        }
        return showInfo;
    }
}
