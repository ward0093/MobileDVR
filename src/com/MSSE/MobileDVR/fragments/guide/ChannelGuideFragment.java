package com.MSSE.MobileDVR.fragments.guide;

import com.MSSE.MobileDVR.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class ChannelGuideFragment extends Fragment{
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);		
		ChannelGuideView view = new ChannelGuideView(getActivity());
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
