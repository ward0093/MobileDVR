package com.MSSE.MobileDVR;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 4/28/13
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowInfoActivity extends Activity {
    private int showInfoID;
    private ShowInfo showInfo;
    private ShowTimeSlot showTimeSlot;
    TextView showNameData;
    TextView showChannelData;
    TextView showTimeData;
    TextView showDateData;
    TextView showDescriptionData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details);
        showInfoID = getIntent().getIntExtra(MainActivity.SHOW_INFO_ID, -1);
        //showInfo = MainActivity.showRepo.get(showInfoID);
        showInfo = new ShowInfo("Mythbusters", "The greatest show on Earth");

        showNameData = (TextView)findViewById(R.id.showInfoShowData);
        showDescriptionData = (TextView)findViewById(R.id.showInfoDescription);
        showNameData.setText(showInfo.getTitle());
        showDescriptionData.setText(showInfo.getDescription());
    }
}