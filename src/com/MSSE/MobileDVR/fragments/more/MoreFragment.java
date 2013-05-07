package com.MSSE.MobileDVR.fragments.more;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.MSSE.MobileDVR.R;
import com.MSSE.MobileDVR.fragments.recorded.RecordedShowFragment;
import com.MSSE.MobileDVR.fragments.recorded.ScheduledRecordingFragment;

public class MoreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.more_options, container, false);

        setMenuVisibility(true);
        setHasOptionsMenu(true);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        Bundle arguments = getArguments();

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
}
