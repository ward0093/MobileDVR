package com.MSSE.MobileDVR.fragments.recorded;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.*;

import android.widget.*;
import com.MSSE.MobileDVR.R;
import com.MSSE.MobileDVR.TabMainActivity;
import com.MSSE.MobileDVR.datamodel.ScheduledRecording;
import com.MSSE.MobileDVR.fragments.guide.ChannelGuideFragment;
import com.MSSE.MobileDVR.fragments.help.HelpFragment;
import com.MSSE.MobileDVR.fragments.help.HelpHelper;
import com.MSSE.MobileDVR.fragments.info.RecordOptionFragment;
import com.MSSE.MobileDVR.fragments.info.ShowInfoFragment;

import java.util.LinkedList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: dward
 * Date: 5/6/13
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduledRecordingFragment extends ListFragment {

    List<ScheduledRecording> mySchedule = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mySchedule = TabMainActivity.getSchedRecDB().getScheduledRecordingList();
        View view = null;

//        if (mySchedule.size() == 0){
//            view = new ListView(getActivity());
//            TextView v2 = new TextView(getActivity());
//            v2.setGravity(Gravity.CENTER);
//            v2.setText(getString(R.string.no_scheduled_recordings));
//            view.addView(v2);
//        } else {
            view = inflater.inflate(R.layout.my_scheduled_recordings, container, false);
//        }

        setMenuVisibility(true);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        Bundle arguments = getArguments();

        // initialize the list view
        ListAdapter myAdp = new ScheduledRecordingAdapter(getActivity(), R.layout.my_scheduled_recording_list_item, mySchedule);

        setListAdapter(myAdp);
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        //setListShown(true);

//        Button myScheduledRecordingButton = (Button)view.findViewById(R.id.myShowsMyScheduledRecordingsButton);
//        myScheduledRecordingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new ScheduledRecordingFragment();
//                Bundle args = new Bundle();
//                //set you arguments that you need to pass to the RecordOptionFragment
//                //args.putInt(ChannelGuideFragment.CHANNEL_ID, channelNum);
//                //args.putSerializable(ChannelGuideFragment.TIME_SLOT_DATE, showDate);
//                fragment.setArguments(args);
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(android.R.id.content, fragment, "myshows");
//                ft.addToBackStack(null);
//                ft.commit();
//            }
//        });
//
//        Button myRecordedShowsButton = (Button)view.findViewById(R.id.myShowsMyRecordedShowsButton);
//        myRecordedShowsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new RecordedShowFragment();
//                Bundle args = new Bundle();
//                //set you arguments that you need to pass to the RecordOptionFragment
//                //args.putInt(ChannelGuideFragment.CHANNEL_ID, channelNum);
//                //args.putSerializable(ChannelGuideFragment.TIME_SLOT_DATE, showDate);
//                fragment.setArguments(args);
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(android.R.id.content, fragment, "myshows");
//                ft.addToBackStack(null);
//                ft.commit();
//            }
//        });
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.activity_tab_main, menu);
    }

    /* We need to provide a custom adapter in order to use a custom list item view.
             */
    public class ScheduledRecordingAdapter extends ArrayAdapter<ScheduledRecording> {

        public ScheduledRecordingAdapter(Context context, int textViewResourceId, List<ScheduledRecording> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View item = inflater.inflate(R.layout.my_scheduled_recording_list_item, parent, false);

            ScheduledRecording sr = getItem(position);
            if (sr != null ) {
                ((TextView)item.findViewById(R.id.scheduled_recording_item_title)).setText(sr.getShowInfo().getTitle());
                ((TextView)item.findViewById(R.id.scheduled_recording_item_channel)).setText("" + sr.getOriginalAirtime().getChannel().getNumber());
                ((TextView)item.findViewById(R.id.scheduled_recording_item_timeofday)).setText(sr.getOriginalAirtime().getStartTimeTimeOnly());
                ((TextView)item.findViewById(R.id.scheduled_recording_item_theday)).setText(sr.getOriginalAirtime().getStartTimeDayOfWeek());
            }

            item.setTag(sr);
            return item;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);    //To change body of overridden methods use File | Settings | File Templates.

        ScheduledRecording mySR = (ScheduledRecording)l.getItemAtPosition(position);

//        Fragment fragment = new RecordOptionFragment();
//        Bundle args = new Bundle();
//        //set your arguments that you need to pass to the RecordOptionFragment
//        args.putInt(ChannelGuideFragment.CHANNEL_ID, mySR.getOriginalAirtime().getChannel().getNumber());
//        args.putInt(ChannelGuideFragment.ADD_OR_EDIT_OPTIONS, 1); //this is a EDIT = 1
//        args.putSerializable(ChannelGuideFragment.TIME_SLOT_DATE, mySR.getOriginalAirtime().getStartTime());
//        fragment.setArguments(args);
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        // "info" should be changed to "guide" after final integration
//        ft.replace(android.R.id.content, fragment, "info");
//        ft.addToBackStack(null);
//        ft.commit();

        getActivity().getActionBar().setSelectedNavigationItem(TabMainActivity.INFO_INDEX);
        Fragment fragment = new ShowInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ChannelGuideFragment.ADD_OR_EDIT_OPTIONS, ChannelGuideFragment.EDIT_OPTION); //this is a ADD = 0
        args.putInt(ChannelGuideFragment.CHANNEL_NUM, mySR.getOriginalAirtime().getChannel().getNumber());
        args.putSerializable(ChannelGuideFragment.TIME_SLOT_DATE, mySR.getOriginalAirtime().getStartTime());
        fragment.setArguments(args);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        // "info" should be changed to "guide" after final integration
        ft.replace(android.R.id.content, fragment, TabMainActivity.INFO);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		                 
		        case R.id.help :
		        	HelpHelper.displayHelp(item, getActivity(), HelpFragment.class, "myshows", null);
		        	return true;
		        // Other case statements...

		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
}
