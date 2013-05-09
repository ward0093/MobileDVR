package com.MSSE.MobileDVR.fragments.info;

import java.util.Date;

import android.app.FragmentTransaction;
import com.MSSE.MobileDVR.MainActivity;
import com.MSSE.MobileDVR.R;
import com.MSSE.MobileDVR.RecordOptionsActivity;
import com.MSSE.MobileDVR.TabMainActivity;
import com.MSSE.MobileDVR.datamodel.ScheduledRecording;
import com.MSSE.MobileDVR.datamodel.ShowTimeSlot;
import com.MSSE.MobileDVR.fragments.guide.ChannelGuideFragment;
import com.MSSE.MobileDVR.fragments.help.HelpFragment;
import com.MSSE.MobileDVR.fragments.help.HelpHelper;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.MSSE.MobileDVR.fragments.recorded.ScheduledRecordingFragment;

public class RecordOptionFragment extends Fragment {
    private final long millsPerDay = 1000L * 60L * 60L * 24L;
	private ShowTimeSlot showTimeSlot;
	private int channelNum;
	private Date showDate;
	private ShowDataConfig showData;
    private int showInfoType;
    private ScheduledRecording tempSchedRec;

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
		channelNum = arguments.getInt(ChannelGuideFragment.CHANNEL_NUM, -1);
		showDate = (Date) arguments.getSerializable(ChannelGuideFragment.TIME_SLOT_DATE);
        showInfoType = arguments.getInt(ChannelGuideFragment.ADD_OR_EDIT_OPTIONS, ChannelGuideFragment.ADD_OPTION);
		
		showTimeSlot = TabMainActivity.getListingSource().lookupTimeSlot(TabMainActivity.getListingSource().lookupChannel(channelNum), showDate);
		showData = new ShowDataConfig(fragmentView);
		showData.setAllShowData(showTimeSlot);

        tempSchedRec = TabMainActivity.getSchedRecDB().getScheduledRecordingByTimeSlot(showTimeSlot);
        if (tempSchedRec != null) {
            updateRecordingOptions(tempSchedRec);
            /* If the timeslot was selected from the channel guide and a scheduled recording already exists,
               make the user edit the existing scheduled recording.
             */
            if (showInfoType == ChannelGuideFragment.ADD_OPTION) {
                Toast.makeText(fragmentView.getContext(), "Recording already scheduled for this timeslot.  Editing existing scheduled recording.",
                        Toast.LENGTH_LONG).show();
                showInfoType = ChannelGuideFragment.EDIT_OPTION;
            }
        }
        else
            /* There was not scheduled recording in existance for this show */
            tempSchedRec = new ScheduledRecording();


        Button okButton = (Button)fragmentView.findViewById(R.id.recordOptionsOkButton);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CheckBox recurring = (CheckBox)fragmentView.findViewById(R.id.recurringShowCheckBox);
				EditText showsToKeep = (EditText)fragmentView.findViewById(R.id.numberShowsRetainData);
				EditText daysToKeep = (EditText)fragmentView.findViewById(R.id.numberDaysRetainData);
				EditText minBefore = (EditText)fragmentView.findViewById(R.id.minutesBeforeRecordData);
				EditText minAfter = (EditText)fragmentView.findViewById(R.id.minutesAfterRecordData);
                if (showInfoType == ChannelGuideFragment.ADD_OPTION) {
                    tempSchedRec.setOriginalAirtime(showTimeSlot);
                    tempSchedRec.setShowInfo(showTimeSlot.getShowInfo());
                }
				tempSchedRec.setRecurring(recurring.isChecked());
                tempSchedRec.setShowsToKeep(Integer.parseInt(showsToKeep.getText().toString()));
                tempSchedRec.setKeepUntil(showTimeSlot.getStartTime(), Integer.parseInt(daysToKeep.getText().toString()));
                tempSchedRec.setMinutesBefore(Integer.parseInt(minBefore.getText().toString()));
                tempSchedRec.setMinutesAfter(Integer.parseInt(minAfter.getText().toString()));

                if (showInfoType == ChannelGuideFragment.EDIT_OPTION)
                    TabMainActivity.getSchedRecDB().updateScheduledRecording(tempSchedRec);
                else
                    TabMainActivity.getSchedRecDB().createScheduledRecording(tempSchedRec);

                /* Display success message in a toast */
                Toast toast = new Toast(getActivity());
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)fragmentView.findViewById(R.id.custom_toast_root));
				TextView title = (TextView)layout.findViewById(R.id.title);
				title.setText("Recording Scheduled Successfully");
				TextView showName = (TextView)layout.findViewById(R.id.show_name);
				showName.setText("\"" + showTimeSlot.getShowInfo().getTitle() + "\"");
				TextView actionResult = (TextView)layout.findViewById(R.id.action_result);
				actionResult.setText("has been scheduled for recording. After the recording is complete, you can playback your recording from the \"My Shows\" tab");
				toast.setView(layout);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setGravity(Gravity.FILL, 0, 0);
				toast.show();

                /* Move to the Scheduled Recording fragment */
                Fragment fragment = new ScheduledRecordingFragment();
                getActivity().getActionBar().setSelectedNavigationItem(TabMainActivity.MYSHOWS_INDEX);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(android.R.id.content, fragment, TabMainActivity.MYSHOWS);
                ft.addToBackStack(null);
                ft.commit();

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

    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		                 
		        case R.id.help :
		        	HelpHelper.displayHelp(item, getActivity(), HelpFragment.class, "info", null);
		        	return true;
		        // Other case statements...

		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}

    private void updateRecordingOptions(ScheduledRecording tempSchedRec) {
        View fragmentView = getView();
        CheckBox recurring = (CheckBox)fragmentView.findViewById(R.id.recurringShowCheckBox);
        recurring.setChecked(tempSchedRec.getRecurring());
        EditText showsToKeep = (EditText)fragmentView.findViewById(R.id.numberShowsRetainData);
        showsToKeep.setText(Integer.toString(tempSchedRec.getShowsToKeep()));
        EditText daysToKeep = (EditText)fragmentView.findViewById(R.id.numberDaysRetainData);
        daysToKeep.setText(Long.toString((tempSchedRec.getKeepUntil().getTime() - tempSchedRec.getOriginalAirtime().getStartTime().getTime()) / millsPerDay));
        EditText minBefore = (EditText)fragmentView.findViewById(R.id.minutesBeforeRecordData);
        minBefore.setText(Integer.toString(tempSchedRec.getMinutesBefore()));
        EditText minAfter = (EditText)fragmentView.findViewById(R.id.minutesAfterRecordData);
        minAfter.setText(Integer.toString(tempSchedRec.getMinutesAfter()));

    }
}
