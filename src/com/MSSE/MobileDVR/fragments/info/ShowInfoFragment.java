package com.MSSE.MobileDVR.fragments.info;

import java.net.URI;
import java.util.Date;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;
import com.MSSE.MobileDVR.R;
import com.MSSE.MobileDVR.TabMainActivity;
import com.MSSE.MobileDVR.datamodel.ShowTimeSlot;
import com.MSSE.MobileDVR.fragments.guide.ChannelGuideFragment;
import com.MSSE.MobileDVR.fragments.help.HelpFragment;
import com.MSSE.MobileDVR.fragments.help.HelpHelper;

public class ShowInfoFragment extends Fragment {

    public static final String PREVIEW_URL = "PreviewURL";
	private ShowTimeSlot showTimeSlot;
    private int channelNum;
    private Date showDate;
    private Integer channelNumber;
    private ShowDataConfig showData;
    private TextView showDescriptionData;
    private int showInfoType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view;
		super.onCreate(savedInstanceState);
		 Bundle arguments = getArguments();
	     if (arguments == null){
	    	 view = new TextView(getActivity());
	    	 ((TextView)view).setGravity(Gravity.CENTER);
	    	 ((TextView)view).setText(getString(R.string.no_show_info));
	     } else {
	    	 view = inflater.inflate(R.layout.show_details, container, false); 
	     }
	
		setMenuVisibility(true);
		setHasOptionsMenu(true);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        Bundle arguments = getArguments();
        if (arguments == null){
        	return;
        }
		channelNum = arguments.getInt(ChannelGuideFragment.CHANNEL_NUM, -1);
		showDate = (Date) arguments.getSerializable(ChannelGuideFragment.TIME_SLOT_DATE);
        showInfoType = arguments.getInt(ChannelGuideFragment.ADD_OR_EDIT_OPTIONS, ChannelGuideFragment.ADD_OPTION);
		
		showTimeSlot = TabMainActivity.getListingSource().lookupTimeSlot(TabMainActivity.getListingSource().lookupChannel(channelNum), showDate);
		showData = new ShowDataConfig(view);
		showData.setAllShowData(showTimeSlot);
		showDescriptionData = (TextView)view.findViewById(R.id.showInfoDescription);

		if (showTimeSlot.getShowInfo().getDescription().equals("")) {
			showDescriptionData.setText("No description for show...");
		} else {
			showDescriptionData.setText(showTimeSlot.getShowInfo().getDescription());
		}

		Button recordButton = (Button)view.findViewById(R.id.showInfoRecordButton);
        if (showInfoType == ChannelGuideFragment.EDIT_OPTION) {
            recordButton.setText("Edit");
        }
		recordButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Fragment fragment = new RecordOptionFragment();
            	Bundle args = new Bundle();
            	//set your arguments that you need to pass to the RecordOptionFragment
            	args.putInt(ChannelGuideFragment.CHANNEL_NUM, channelNum);
            	args.putSerializable(ChannelGuideFragment.TIME_SLOT_DATE, showDate);
                args.putInt(ChannelGuideFragment.ADD_OR_EDIT_OPTIONS, showInfoType);
            	fragment.setArguments(args);
            	FragmentTransaction ft = getFragmentManager().beginTransaction();
            	// "info" should be changed to "guide" after final integration
            	ft.replace(android.R.id.content, fragment, "info");
            	ft.addToBackStack(null);
            	ft.commit();

			}
		});

        Button previewButton = (Button)view.findViewById(R.id.showInfoPreviewButton);
        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((showTimeSlot.getPreviewUrl() == null) ||
                        (showTimeSlot.getPreviewUrl() == "")) {
                    Toast.makeText(v.getContext(), "No preview data available", Toast.LENGTH_LONG).show();
                } else {
                    Uri previewUri = Uri.parse(showTimeSlot.getPreviewUrl());
                    startActivity(new Intent(Intent.ACTION_VIEW, previewUri));
                }
            }
        });

        Button upcomingButton = (Button)view.findViewById(R.id.showInfoUpcomingButton);
        if (showInfoType == ChannelGuideFragment.EDIT_OPTION) {
            upcomingButton.setText("Delete");
        }
		//        upcomingButton.setOnClickListener(new View.OnClickListener() {
		//            @Override
		//            public void onClick(View v) {
		//                Intent intent = new Intent(ShowInfoActivity.this, UpcomingActivity.class);
		//                intent.putExtra(MainActivity.CHANNEL_ID, channelNum);
		//                intent.putExtra(MainActivity.TIME_SLOT_DATE, showDate);
		//      ShowInfoActivity.this.startActivity(intent);
		//            }
		//        });

        Button watchNowButton = (Button)view.findViewById(R.id.showInfoWatchNowButton);
        watchNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Not implemented...Maybe you should go outside and play instead!", Toast.LENGTH_LONG).show();
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
}
