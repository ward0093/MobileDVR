package com.MSSE.MobileDVR.fragments.recorded;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.MSSE.MobileDVR.R;
import com.MSSE.MobileDVR.fragments.help.HelpFragment;
import com.MSSE.MobileDVR.fragments.help.HelpHelper;

public class RecordedShowFragment extends Fragment {
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
}
