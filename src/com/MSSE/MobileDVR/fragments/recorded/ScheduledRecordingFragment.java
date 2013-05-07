package com.MSSE.MobileDVR.fragments.recorded;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.MSSE.MobileDVR.R;


/**
 * Created with IntelliJ IDEA.
 * User: dward
 * Date: 5/6/13
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduledRecordingFragment extends Fragment {
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
    public void onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.activity_tab_main, menu);
    }
}