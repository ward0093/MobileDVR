package com.MSSE.MobileDVR.fragments.info;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.MSSE.MobileDVR.R;
import com.MSSE.MobileDVR.TabMainActivity;
import com.MSSE.MobileDVR.datamodel.ShowInfo;
import com.MSSE.MobileDVR.datamodel.ShowTimeSlot;
import com.MSSE.MobileDVR.fragments.guide.ChannelGuideFragment;
import com.MSSE.MobileDVR.fragments.help.HelpFragment;
import com.MSSE.MobileDVR.fragments.help.HelpHelper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 5/9/13
 * Time: 1:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class UpcomingFragment extends ListFragment {

    List<ShowTimeSlot> upcomingShows = null;
    ShowInfo showInfo = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        long showInfoId = arguments.getLong(ShowInfoFragment.SHOWINFOID);
        showInfo = TabMainActivity.getShowInfoDB().getShowInfo(showInfoId);

        upcomingShows = TabMainActivity.getShowTimeSlotDB().getTimeSlotsListForShow(showInfoId);
        View view = inflater.inflate(R.layout.upcoming_show, container, false);
//        }

        setMenuVisibility(true);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();


        ((TextView)view.findViewById(R.id.upcomingShowNameData)).setText(showInfo.getTitle());

        // initialize the list view
        ListAdapter myAdp = new UpcomingShowsAdapter(getActivity(), R.layout.upcoming_show_list_item, upcomingShows);

        setListAdapter(myAdp);
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
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
    public class UpcomingShowsAdapter extends ArrayAdapter<ShowTimeSlot> {

        public UpcomingShowsAdapter(Context context, int textViewResourceId, List<ShowTimeSlot> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View item = inflater.inflate(R.layout.upcoming_show_list_item, parent, false);

            ShowTimeSlot showTimeSlot = getItem(position);
            if (showTimeSlot != null ) {
                ((TextView)item.findViewById(R.id.upcomingDate)).setText(showTimeSlot.getStartTimeDayOfWeek());
                ((TextView)item.findViewById(R.id.upcomingStartTime)).setText(showTimeSlot.getStartTimeTimeOnly());
                ((TextView)item.findViewById(R.id.upcomingEndTime)).setText(showTimeSlot.getEndTimeTimeOnly());
                ((TextView)item.findViewById(R.id.upcomingChannel)).setText(Integer.toString(showTimeSlot.getChannel().getNumber()));
            }

            item.setTag(showTimeSlot);
            return item;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ShowTimeSlot mySTS = (ShowTimeSlot)l.getItemAtPosition(position);

        getActivity().getActionBar().setSelectedNavigationItem(TabMainActivity.INFO_INDEX);
        Fragment fragment = new ShowInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ChannelGuideFragment.ADD_OR_EDIT_OPTIONS, ChannelGuideFragment.ADD_OPTION);
        args.putInt(ChannelGuideFragment.CHANNEL_NUM, mySTS.getChannel().getNumber());
        args.putSerializable(ChannelGuideFragment.TIME_SLOT_DATE, mySTS.getStartTime());
        fragment.setArguments(args);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
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

