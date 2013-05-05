package com.MSSE.MobileDVR;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final int GUIDE = 1;
    public static final String CHANNEL_ID = "channelNumber";
    public static final String TIME_SLOT_DATE = "timeSlotDate";
    
    private static ListingSource listingSource = new DummyListingSource();
    public static ListingSource getListingSource() {
    	return listingSource;
    }
    private static ShowInfoDataSource showInfoDataSource;
    private static ChannelDataSource channelDataSource;
    private static ShowTimeSlotDataSource showTimeSlotDataSource;
    
    public static final ScheduledRecordingSource scheduledRecordings = new ScheduledRecordingFile();
   // public static final ScheduledRecordingSource scheduledRecordings = new ScheduledRecordingFile();

    public static final RecordedShowSource myRecordedShows = new RecordedShowFile();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        showInfoDataSource = new ShowInfoDataSource(this);
        channelDataSource = new ChannelDataSource(this);
        showTimeSlotDataSource = new ShowTimeSlotDataSource(this);
        try {
            showInfoDataSource.open();
            channelDataSource.open();
            showTimeSlotDataSource.open();
        } catch (Exception e) {
            Log.e("MainActivity -- DataSource Opening", "Failed to open a DataSource", e);
        }

        // Uncomment these lines to begin storing data in the sqlite database

//        Channel myFirstChannel = channelDataSource.createChannel(1, "TPT1");
//        Channel mySecondChannel = channelDataSource.createChannel(2, "TPT2");
//        List<Channel> channelList = channelDataSource.getChannelList();
//       // mySecondChannel = channelDataSource.getChannelByNumber(myFirstChannel.getNumber());
//        Log.d("Dumb tag", "myFirstChannel name " + myFirstChannel.getName() + "   mySecondChannel name " + mySecondChannel.getName());
//        ShowInfo myFirstShow = showInfoDataSource.createShowInfo("Darin's First Test", "First test without an image", null);
//        ShowInfo mySecondShow = showInfoDataSource.createShowInfo("Darin's 2nd Test", "Second test without an image", null);
//        List<ShowInfo> fullList = showInfoDataSource.getShowInfoList();
//        Log.d("My First test", "Did it work?");
//        ShowTimeSlot firstTimeSlot = showTimeSlotDataSource.createShowTimeSlot(myFirstChannel, myFirstShow, new Date(), 120);
//        ShowTimeSlot secondTimeSlot = showTimeSlotDataSource.createShowTimeSlot(mySecondChannel, mySecondShow, new Date(), 180);
        
        Button guideButton = (Button)findViewById(R.id.guide_button);
        guideButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
				//Toast.makeText(this, "Edit Button clicked!", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(MainActivity.this, ChannelGuide.class);
				//intent.putExtra(CONTACT_ID, storage.newContact().getID());
				//intent.putExtra("contact", storage.newContact());
				//intent.putExtra(REPOSITORY, storage);
				startActivityForResult(intent, GUIDE);
			}
        });
        ShowInfo testShow = new ShowInfo("Mythbusters", "Greatest show on Earth");

        Button onlyButton = (Button)findViewById(R.id.show_button);
        onlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowInfoActivity.class);
                ShowInfo showInfos[] = listingSource.getShows();
                ShowTimeSlot showTimeSlot[] = listingSource.getTimeSlotsForShow(showInfos[0]);
                intent.putExtra(CHANNEL_ID, showTimeSlot[0].getChannel().getNumber());
                intent.putExtra(TIME_SLOT_DATE, showTimeSlot[0].getStartTime());
                MainActivity.this.startActivity(intent);
            }
        });

        Button recordedShows = (Button)findViewById(R.id.recorded_shows_button);
        recordedShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordedShowsActivity.class);
                RecordedShow myRecordings[] = myRecordedShows.getRecordedShows();

                //intent.putExtra(CONTACT_ID, storage.newContact().getID());
                //intent.putExtra("contact", storage.newContact());
                //intent.putExtra(REPOSITORY, storage);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    public void onResume() {
        super.onResume();
        if (!showInfoDataSource.isOpen()) {
            try {
                showInfoDataSource.open();
            } catch (Exception e) {
                Log.e("MainActivity -- ShowInfoDataSource", "Failed to open ShowInfoDataSource", e);
            }
        }
    }

    public void onPause() {
        super.onPause();
        showInfoDataSource.close();
    }

    public static ChannelDataSource getChannelDB() {
        return channelDataSource;
    }

    public static ShowInfoDataSource getShowInfoDB() {
        return showInfoDataSource;
    }
}
