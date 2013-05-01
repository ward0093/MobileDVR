package com.MSSE.MobileDVR;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 4/30/13
 * Time: 11:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecordOptionsActivity extends Activity {
    private ShowTimeSlot showTimeSlot;
    private int channelNum;
    private Date showDate;
    private ShowDataConfig showData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_options);
        channelNum = getIntent().getIntExtra(MainActivity.CHANNEL_ID, -1);
        showDate = (Date) getIntent().getSerializableExtra(MainActivity.TIME_SLOT_DATE);
        showTimeSlot = MainActivity.listingSource.lookupTimeSlot(MainActivity.listingSource.lookupChannel(channelNum), showDate);
        showData = new ShowDataConfig(this);
        showData.setAllShowData(showTimeSlot);
    }
}
