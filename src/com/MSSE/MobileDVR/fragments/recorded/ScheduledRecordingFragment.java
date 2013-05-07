package com.MSSE.MobileDVR.fragments.recorded;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import com.MSSE.MobileDVR.R;


/**
 * Created with IntelliJ IDEA.
 * User: dward
 * Date: 5/6/13
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduledRecordingFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.my_scheduled_recordings, container, false);


        setMenuVisibility(true);
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        Bundle arguments = getArguments();

        Button myScheduledRecordingButton = (Button)view.findViewById(R.id.myShowsMyScheduledRecordingsButton);
        myScheduledRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ScheduledRecordingFragment();
                Bundle args = new Bundle();
                //set you arguments that you need to pass to the RecordOptionFragment
                //args.putInt(ChannelGuideFragment.CHANNEL_ID, channelNum);
                //args.putSerializable(ChannelGuideFragment.TIME_SLOT_DATE, showDate);
                fragment.setArguments(args);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(android.R.id.content, fragment, "myshows");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        Button myRecordedShowsButton = (Button)view.findViewById(R.id.myShowsMyRecordedShowsButton);
        myRecordedShowsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RecordedShowFragment();
                Bundle args = new Bundle();
                //set you arguments that you need to pass to the RecordOptionFragment
                //args.putInt(ChannelGuideFragment.CHANNEL_ID, channelNum);
                //args.putSerializable(ChannelGuideFragment.TIME_SLOT_DATE, showDate);
                fragment.setArguments(args);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(android.R.id.content, fragment, "myshows");
                ft.addToBackStack(null);
                ft.commit();
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