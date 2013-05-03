package com.MSSE.MobileDVR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
        showTimeSlot = MainActivity.getListingSource().lookupTimeSlot(MainActivity.getListingSource().lookupChannel(channelNum), showDate);
        showData = new ShowDataConfig(this);
        showData.setAllShowData(showTimeSlot);

        Button okButton = (Button)findViewById(R.id.recordOptionsOkButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox recurring = (CheckBox)findViewById(R.id.recurringShowCheckBox);
                EditText showsToKeep = (EditText)findViewById(R.id.numberShowsRetainData);
                EditText daysToKeep = (EditText)findViewById(R.id.numberDaysRetainData);
                EditText minBefore = (EditText)findViewById(R.id.minutesBeforeRecordData);
                EditText minAfter = (EditText)findViewById(R.id.minutesAfterRecordData);
                ScheduledRecording scheduledRec = MainActivity.scheduledRecordings.newScheduledRecording();
                scheduledRec.setRecurring(recurring.isChecked());
                scheduledRec.setShowsToKeep(Integer.parseInt(showsToKeep.getText().toString()));
                scheduledRec.setKeepUntil(showTimeSlot.getStartTime(), Integer.parseInt(daysToKeep.getText().toString()));
                scheduledRec.setMinutesBefore(Integer.parseInt(minBefore.getText().toString()));
                scheduledRec.setMinutesAfter(Integer.parseInt(minAfter.getText().toString()));
                scheduledRec.setOriginalAirtime(showTimeSlot);
                scheduledRec.setShowInfo(showTimeSlot.getShowInfo());
            }
        });
    }
}
