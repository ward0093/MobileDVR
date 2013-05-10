package com.MSSE.MobileDVR.fragments.info;

import java.util.Date;

import android.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;
import com.MSSE.MobileDVR.R;
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
    private final int VALUENOTENTERED = -1;
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
                Toast.makeText(fragmentView.getContext(), "Recording already scheduled for this show time slot.  Editing existing scheduled recording.",
                        Toast.LENGTH_LONG).show();
                showInfoType = ChannelGuideFragment.EDIT_OPTION;
            }
        }
        else
            /* There was no scheduled recording in existance for this show */
            tempSchedRec = new ScheduledRecording();


        Button okButton = (Button)fragmentView.findViewById(R.id.recordOptionsOkButton);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CheckBox recurring = (CheckBox)fragmentView.findViewById(R.id.recurringShowCheckBox);
				EditText showsToKeepEditText = (EditText)fragmentView.findViewById(R.id.numberShowsRetainData);
                int showsToKeep = getEditTextInt(showsToKeepEditText);
				EditText daysToKeepEditText = (EditText)fragmentView.findViewById(R.id.numberDaysRetainData);
                int daysToKeep = getEditTextInt(daysToKeepEditText);

				EditText minBeforeEditText = (EditText)fragmentView.findViewById(R.id.minutesBeforeRecordData);
                int minBefore = getEditTextInt(minBeforeEditText);
				EditText minAfterEditText = (EditText)fragmentView.findViewById(R.id.minutesAfterRecordData);
                int minAfter = getEditTextInt(minAfterEditText);
                if (showInfoType == ChannelGuideFragment.ADD_OPTION) {
                    tempSchedRec.setOriginalAirtime(showTimeSlot);
                    tempSchedRec.setShowInfo(showTimeSlot.getShowInfo());
                }
				tempSchedRec.setRecurring(recurring.isChecked());
                tempSchedRec.setShowsToKeep(showsToKeep);
                tempSchedRec.setKeepUntil(showTimeSlot.getStartTime(), daysToKeep);
                tempSchedRec.setMinutesBefore(minBefore);
                tempSchedRec.setMinutesAfter(minAfter);

                if (showInfoType == ChannelGuideFragment.EDIT_OPTION)
                    TabMainActivity.getSchedRecDB().updateScheduledRecording(tempSchedRec);
                else
                    TabMainActivity.getSchedRecDB().createScheduledRecording(tempSchedRec);

                /* Close the keyboard */
                InputMethodManager imm = (InputMethodManager)fragmentView.getContext().getSystemService(fragmentView.getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(fragmentView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

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
        if (tempSchedRec.getShowsToKeep() == VALUENOTENTERED)
            showsToKeep.setText("");
        else
            showsToKeep.setText(Integer.toString(tempSchedRec.getShowsToKeep()));

        EditText daysToKeep = (EditText)fragmentView.findViewById(R.id.numberDaysRetainData);
        if (tempSchedRec.getKeepUntil() == null)
            daysToKeep.setText("");
        else
            daysToKeep.setText(Long.toString((tempSchedRec.getKeepUntil().getTime() - tempSchedRec.getOriginalAirtime().getStartTime().getTime()) / millsPerDay));

        EditText minBefore = (EditText)fragmentView.findViewById(R.id.minutesBeforeRecordData);
        if (tempSchedRec.getMinutesBefore() == VALUENOTENTERED)
            minBefore.setText("");
        else
            minBefore.setText(Integer.toString(tempSchedRec.getMinutesBefore()));

        EditText minAfter = (EditText)fragmentView.findViewById(R.id.minutesAfterRecordData);
        if (tempSchedRec.getMinutesAfter() == VALUENOTENTERED)
            minAfter.setText("");
        else
            minAfter.setText(Integer.toString(tempSchedRec.getMinutesAfter()));

    }

    public int getEditTextInt(EditText editText) {
        int result;
        if (editText.getText().toString().trim().length() > 0) {
            result = Integer.parseInt(editText.getText().toString());
        } else {
            result = VALUENOTENTERED;
        }
        return result;
    }

}
