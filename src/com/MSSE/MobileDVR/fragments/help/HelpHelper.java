package com.MSSE.MobileDVR.fragments.help;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;


public class HelpHelper {
	
	public static <T> void displayHelp(MenuItem item, Activity mActivity, Class<T> mClass, String mTag, Bundle mArgs){
		FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
		Fragment mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
        ft.replace(android.R.id.content, mFragment, mTag);
        ft.addToBackStack(null);
        ft.commit();
	}

}
