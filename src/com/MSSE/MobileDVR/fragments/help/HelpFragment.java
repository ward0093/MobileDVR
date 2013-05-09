package com.MSSE.MobileDVR.fragments.help;

import com.MSSE.MobileDVR.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class HelpFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.help_details, container, false);
	
		setMenuVisibility(true);
		setHasOptionsMenu(true);
		return view;
	}
	
}
