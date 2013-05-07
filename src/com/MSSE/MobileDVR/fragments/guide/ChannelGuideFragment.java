package com.MSSE.MobileDVR.fragments.guide;

import com.MSSE.MobileDVR.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class ChannelGuideFragment extends Fragment{
	
    public static final String CHANNEL_ID = "channelNumber";
    public static final String TIME_SLOT_DATE = "timeSlotDate";

	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		ChannelGuideView view = new ChannelGuideView(getActivity());
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
	  //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	  SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
	  searchView.setQueryHint(getString(R.string.search_hint));
	  // Tells your app's SearchView to use this activity's searchable configuration
	  // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	  // searchView.setIconifiedByDefault(false);
	    
	  
	    
	    searchView.setOnSearchClickListener(new View.OnClickListener(){
	    	@Override
            public void onClick(View v) {
	    		Toast toast = Toast.makeText(getActivity(), ((SearchView) v).getQuery().toString(), Toast.LENGTH_SHORT);
	    		toast.show();
	    	}
	    });
	    
	    searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				Toast toast = Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT);
	    		toast.show();
				
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	 }
	
	

}
