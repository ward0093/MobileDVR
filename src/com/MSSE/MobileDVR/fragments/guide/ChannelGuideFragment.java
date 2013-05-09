package com.MSSE.MobileDVR.fragments.guide;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.MSSE.MobileDVR.R;
import com.MSSE.MobileDVR.TabMainActivity;
import com.MSSE.MobileDVR.fragments.help.HelpFragment;
import com.MSSE.MobileDVR.fragments.help.HelpHelper;

public class ChannelGuideFragment extends Fragment
{

	public static final String CHANNEL_ID = "channelNumber";
	public static final String TIME_SLOT_DATE = "timeSlotDate";
	public static final String QUERY = "query";
	public static ChannelGuideView channelGuideView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		View theView = null;

		Bundle args = getArguments();
		if (args != null)
		{
			String query = args.getString(QUERY);
			theView = ChannelGuideView.generateQueryResultsView(getActivity(), query);
			if (theView == null)
			{
				Toast toast = Toast.makeText(getActivity(), "No shows match your query.", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
		
		if (theView == null)
		{
			if (channelGuideView == null)
				channelGuideView = new ChannelGuideView(getActivity());
			theView = channelGuideView;
		}

		//setMenuVisibility(true);
		setHasOptionsMenu(true);
		return theView;
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		                 
		        case R.id.help :
		        	HelpHelper.displayHelp(item, getActivity(), HelpFragment.class, TabMainActivity.GUIDE, null);
		        	return true;
		        // Other case statements...

		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		getActivity().getMenuInflater().inflate(R.menu.activity_tab_main, menu);
		//SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
		searchView.setQueryHint(getString(R.string.search_hint));
		// Tells your app's SearchView to use this activity's searchable configuration
		// searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		// searchView.setIconifiedByDefault(false);

		searchView.setOnSearchClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//Toast toast = Toast.makeText(getActivity(), ((SearchView) v).getQuery().toString(), Toast.LENGTH_SHORT);
				//toast.show();
			}
		});

		searchView.setOnQueryTextListener(new OnQueryTextListener()
		{

			@Override
			public boolean onQueryTextSubmit(String query)
			{
				Fragment fragment = new ChannelGuideFragment();
				Bundle args = new Bundle();
				args.putString(ChannelGuideFragment.QUERY, query);
				fragment.setArguments(args);
				FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
				ft.replace(android.R.id.content, fragment, TabMainActivity.GUIDE);
				ft.addToBackStack(null);
				ft.commit();

				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText)
			{
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

}
