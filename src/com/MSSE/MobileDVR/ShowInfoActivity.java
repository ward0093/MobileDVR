package com.MSSE.MobileDVR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.MSSE.MobileDVR.datamodel.ShowTimeSlot;
import com.MSSE.MobileDVR.fragments.info.ShowDataConfig;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 4/28/13
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowInfoActivity extends Activity {
    private ShowTimeSlot showTimeSlot;
    private int channelNum;
    private Date showDate;
    private Integer channelNumber;
    private ShowDataConfig showData;
    private TextView showDescriptionData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details);
        channelNum = getIntent().getIntExtra(MainActivity.CHANNEL_ID, -1);
        showDate = (Date) getIntent().getSerializableExtra(MainActivity.TIME_SLOT_DATE);
        showTimeSlot = MainActivity.getListingSource().lookupTimeSlot(MainActivity.getListingSource().lookupChannel(channelNum), showDate);
        //showData = new ShowDataConfig(getV);
        showData.setAllShowData(showTimeSlot);
        showDescriptionData = (TextView)findViewById(R.id.showInfoDescription);
        ToolbarConfig toolbar = new ToolbarConfig(this, "Show Info");
        Button leftButton = toolbar.getToolbarLeftButton();
        leftButton.setText("Back");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (showTimeSlot.getShowInfo().getDescription().equals("")) {
            showDescriptionData.setText("No description for show...");
        } else {
            showDescriptionData.setText(showTimeSlot.getShowInfo().getDescription());
        }

        Button recordButton = (Button)findViewById(R.id.showInfoRecordButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowInfoActivity.this, RecordOptionsActivity.class);
                intent.putExtra(MainActivity.CHANNEL_ID, channelNum);
                intent.putExtra(MainActivity.TIME_SLOT_DATE, showDate);
                ShowInfoActivity.this.startActivity(intent);
            }
        });

//        Button upcomingButton = (Button)findViewById(R.id.showInfoUpcomingButton);
//        upcomingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ShowInfoActivity.this, UpcomingActivity.class);
//                intent.putExtra(MainActivity.CHANNEL_ID, channelNum);
//                intent.putExtra(MainActivity.TIME_SLOT_DATE, showDate);
//                ShowInfoActivity.this.startActivity(intent);
//            }
//        });
    }
}
