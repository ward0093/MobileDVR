package com.MSSE.MobileDVR.fragments.info;

import java.util.Date;

import com.MSSE.MobileDVR.MainActivity;
import com.MSSE.MobileDVR.R;
import com.MSSE.MobileDVR.RecordOptionsActivity;
import com.MSSE.MobileDVR.TabMainActivity;
import com.MSSE.MobileDVR.datamodel.ScheduledRecording;
import com.MSSE.MobileDVR.datamodel.ShowTimeSlot;
import com.MSSE.MobileDVR.fragments.guide.ChannelGuideFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecordOptionFragment extends Fragment {
	private ShowTimeSlot showTimeSlot;
	private int channelNum;
	private Date showDate;
	private ShowDataConfig showData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View fragmentView = inflater.inflate(R.layout.record_options, container, false);
		
        //mark the action bar to show up
		setHasOptionsMenu(true);
		return fragmentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        final View fragmentView = getView();
        
        Bundle arguments = getArguments();
		channelNum = arguments.getInt(ChannelGuideFragment.CHANNEL_ID, -1);
		showDate = (Date) arguments.getSerializable(ChannelGuideFragment.TIME_SLOT_DATE);
		
		showTimeSlot = TabMainActivity.getListingSource().lookupTimeSlot(TabMainActivity.getListingSource().lookupChannel(channelNum), showDate);
		showData = new ShowDataConfig(fragmentView);
		showData.setAllShowData(showTimeSlot);

		Button okButton = (Button)fragmentView.findViewById(R.id.recordOptionsOkButton);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CheckBox recurring = (CheckBox)fragmentView.findViewById(R.id.recurringShowCheckBox);
				EditText showsToKeep = (EditText)fragmentView.findViewById(R.id.numberShowsRetainData);
				EditText daysToKeep = (EditText)fragmentView.findViewById(R.id.numberDaysRetainData);
				EditText minBefore = (EditText)fragmentView.findViewById(R.id.minutesBeforeRecordData);
				EditText minAfter = (EditText)fragmentView.findViewById(R.id.minutesAfterRecordData);
				ScheduledRecording scheduledRec = TabMainActivity.scheduledRecordings.newScheduledRecording();
				scheduledRec.setRecurring(recurring.isChecked());
				scheduledRec.setShowsToKeep(Integer.parseInt(showsToKeep.getText().toString()));
				scheduledRec.setKeepUntil(showTimeSlot.getStartTime(), Integer.parseInt(daysToKeep.getText().toString()));
				scheduledRec.setMinutesBefore(Integer.parseInt(minBefore.getText().toString()));
				scheduledRec.setMinutesAfter(Integer.parseInt(minAfter.getText().toString()));
				scheduledRec.setOriginalAirtime(showTimeSlot);
				scheduledRec.setShowInfo(showTimeSlot.getShowInfo());

				Toast toast = new Toast(getActivity());
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)fragmentView.findViewById(R.id.custom_toast_root));
				TextView title = (TextView)layout.findViewById(R.id.title);
				title.setText("Recording Scheduled Successfully");
				TextView showName = (TextView)layout.findViewById(R.id.show_name);
				showName.setText("\"" + showTimeSlot.getShowInfo().getTitle() + "\"");
				TextView actionResult = (TextView)layout.findViewById(R.id.action_result);
				actionResult.setText("has been scheduled for recording. After the recording is complete, you can playback your recording from the \"My Shows\" tab");
				//        layout.setBackgroundResource(60);
				toast.setView(layout);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setGravity(Gravity.FILL, 0, 0);
				toast.show();
			}
		});
		Button cancelButton = (Button)fragmentView.findViewById(R.id.recordOptionsCancelButton);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//finish();
			}
		});
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		getActivity().getMenuInflater().inflate(R.menu.activity_tab_main, menu);
	}
}
