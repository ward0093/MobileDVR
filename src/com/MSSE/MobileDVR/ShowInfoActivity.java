package com.MSSE.MobileDVR;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 4/28/13
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowInfoActivity extends Activity {
    private static final String SHOW_TIME_FORMAT = "h:mm a";
    private static final String SHOW_DATE_FORMAT = "EEE M/d";
    private ShowTimeSlot showTimeSlot;
    private int channelNum;
    private Date showDate;
    private Integer channelNumber;
    private TextView showNameData;
    private TextView showChannelData;
    private TextView showTimeData;
    private TextView showDateData;
    private TextView showDescriptionData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details);
        channelNum = getIntent().getIntExtra(MainActivity.CHANNEL_ID, -1);
        showDate = (Date) getIntent().getSerializableExtra(MainActivity.TIME_SLOT_DATE);
        showTimeSlot = MainActivity.listingSource.lookupTimeSlot(MainActivity.listingSource.lookupChannel(channelNum), showDate);
        channelNumber = showTimeSlot.getChannel().getNumber();

        showNameData = (TextView)findViewById(R.id.showInfoShowData);
        showChannelData = (TextView)findViewById(R.id.showInfoChannelData);
        showTimeData = (TextView)findViewById(R.id.showInfoTimeData);
        showDateData = (TextView)findViewById(R.id.showInfoDateData);
        showDescriptionData = (TextView)findViewById(R.id.showInfoDescription);
        showNameData.setText(showTimeSlot.getShowInfo().getTitle());
        showChannelData.setText(channelNumber.toString());
        setShowTime();
        setShowDate();
        if (showTimeSlot.getShowInfo().getDescription() == "") {
            showDescriptionData.setText("No description for show...");
        } else {
            showDescriptionData.setText(showTimeSlot.getShowInfo().getDescription());
        }
    }

    private void setShowTime() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(SHOW_TIME_FORMAT);
        showTimeData.setText(dateFormatter.format(showTimeSlot.getStartTime()));
    }
    private void setShowDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(SHOW_DATE_FORMAT);
        showDateData.setText(dateFormatter.format(showTimeSlot.getStartTime()));
    }
}
