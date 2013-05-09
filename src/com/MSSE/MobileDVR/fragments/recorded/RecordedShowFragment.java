package com.MSSE.MobileDVR.fragments.recorded;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.MSSE.MobileDVR.R;

import com.MSSE.MobileDVR.fragments.help.HelpFragment;
import com.MSSE.MobileDVR.fragments.help.HelpHelper;

import com.MSSE.MobileDVR.TabMainActivity;
import com.MSSE.MobileDVR.datamodel.RecordedShow;
import com.MSSE.MobileDVR.datamodel.ScheduledRecording;

import java.util.List;

public class RecordedShowFragment extends ListFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.my_recorded_shows, container, false);
		
	
		setMenuVisibility(true);
		setHasOptionsMenu(true);
		return view;
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        Bundle arguments = getArguments();

        List<RecordedShow> myRecordedShows = TabMainActivity.getRecordedShowDB().getRecordedShowList();
        //listAdapter = new ContactAdapter(this, R.layout.list_item, new LinkedList<Contact>());
        // initialize the list view
        ListAdapter myAdp = new RecordedShowsAdapter(getActivity(), R.layout.my_recorded_shows_list_item, myRecordedShows);

        setListAdapter(myAdp);
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        //setListShown(true);

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
		        	HelpHelper.displayHelp(item, getActivity(), HelpFragment.class, "myshows", null);
		        	return true;
		        // Other case statements...

		        default:
		            return super.onOptionsItemSelected(item);
		    }
	}

    /* We need to provide a custom adapter in order to use a custom list item view.
             */
    public class RecordedShowsAdapter extends ArrayAdapter<RecordedShow> {

        public RecordedShowsAdapter(Context context, int textViewResourceId, List<RecordedShow> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View item = inflater.inflate(R.layout.my_recorded_shows_list_item, parent, false);

            RecordedShow rs = getItem(position);
            if (rs != null ) {
                ((TextView)item.findViewById(R.id.recorded_show_item_title)).setText(rs.getShowInfo().getTitle());
                ((TextView)item.findViewById(R.id.recorded_show_item_channel)).setText("" + rs.getOriginalAirtime().getChannel().getNumber());
                ((TextView)item.findViewById(R.id.recorded_show_item_timeofday)).setText(rs.getOriginalAirtime().getStartTimeTimeOnly());
                ((TextView)item.findViewById(R.id.recorded_show_item_theday)).setText(rs.getOriginalAirtime().getStartTimeDayOfWeek());
            }

            item.setTag(rs);
            return item;
        }
    }
}
