package com.MSSE.MobileDVR.fragments.recorded;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.MSSE.MobileDVR.R;

public class MyShowsFragment extends Fragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.my_shows, container, false);

        setMenuVisibility(true);
        setHasOptionsMenu(true);

        return view;
    }
}
