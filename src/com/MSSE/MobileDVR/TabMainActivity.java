package com.MSSE.MobileDVR;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.MSSE.MobileDVR.datamodel.ShowInfo;
import com.MSSE.MobileDVR.datamodel.ShowTimeSlot;
import com.MSSE.MobileDVR.datasource.ListingSource;
import com.MSSE.MobileDVR.datasource.RecordedShowSource;
import com.MSSE.MobileDVR.datasource.ScheduledRecordingSource;
import com.MSSE.MobileDVR.datasource.dummy.DummyListingSource;
import com.MSSE.MobileDVR.datasource.dummy.RecordedShowFile;
//import com.MSSE.MobileDVR.datasource.dummy.ScheduledRecordingFile;
import com.MSSE.MobileDVR.datasource.sql.*;
import com.MSSE.MobileDVR.fragments.guide.ChannelGuideFragment;
import com.MSSE.MobileDVR.fragments.info.RecordOptionFragment;
import com.MSSE.MobileDVR.fragments.info.ShowInfoFragment;
import com.MSSE.MobileDVR.fragments.more.MoreFragment;
import com.MSSE.MobileDVR.fragments.recorded.MyShowsFragment;

public class TabMainActivity extends Activity {
	public static final String GUIDE = "guide";
	public static final int GUIDE_INDEX = 0;
	public static final String INFO = "info";
	public static final int INFO_INDEX = 1;
	public static final String MYSHOWS = "myshows";
	public static final int MYSHOWS_INDEX = 2;
	public static final String MORE = "more";
	public static final int MORE_INDEX = 3;

	/**
	* The serialization (saved instance state) Bundle key representing the
	* current tab position.
	*/

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private static ListingSource listingSource;
	//private static ListingSource listingSource = new DummyListingSource();
    //public static final ScheduledRecordingSource scheduledRecordings = new ScheduledRecordingFile();
    public static final RecordedShowSource myRecordedShows = new RecordedShowFile();
    private static ShowInfoDataSource showInfoDataSource;
    private static ChannelDataSource channelDataSource;
    private static ShowTimeSlotDataSource showTimeSlotDataSource;
    private static ScheduledRecordingDataSource schedRecDataSource;
    private static RecordedShowDataSource recordedShowDataSource;
	
	public static ListingSource getListingSource() {
	  	return listingSource;
	}

    public static ChannelDataSource getChannelDB() {
        return channelDataSource;
    }

    public static ShowInfoDataSource getShowInfoDB() {
        return showInfoDataSource;
    }

    public static ShowTimeSlotDataSource getShowTimeSlotDB() {
        return showTimeSlotDataSource;
    }

    public static ScheduledRecordingDataSource getSchedRecDB() {
        return schedRecDataSource;
    }

    public static RecordedShowDataSource getRecordedShowDB() {
        return recordedShowDataSource;
    }
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set up the action bar to show tabs.
	    final ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    /*actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setDisplayShowCustomEnabled(true);
	    actionBar.setDisplayShowTitleEnabled(true);*/
    
    
	    actionBar.addTab(actionBar.newTab()
                .setText(R.string.title_guide)
                //.setIcon(R.drawable.guide)
                .setTabListener(new TabListener<ChannelGuideFragment>(
                        this, GUIDE, ChannelGuideFragment.class)));
	    actionBar.addTab(actionBar.newTab()
                .setText(R.string.title_info)
                //.setIcon(R.drawable.info)
                .setTabListener(new TabListener<ShowInfoFragment>(
                        this, INFO, ShowInfoFragment.class)));
	    actionBar.addTab(actionBar.newTab()
                .setText(R.string.title_myshows)
               // .setIcon(R.drawable.my_shows)
                .setTabListener(new TabListener<MyShowsFragment>(
                        this, MYSHOWS, MyShowsFragment.class)));
	    actionBar.addTab(actionBar.newTab()
                .setText(R.string.title_more)
                //.setIcon(R.drawable.more)
                .setTabListener(new TabListener<MoreFragment>(
                        this, MORE, MoreFragment.class)));

        showInfoDataSource = new ShowInfoDataSource(this);
        channelDataSource = new ChannelDataSource(this);
        showTimeSlotDataSource = new ShowTimeSlotDataSource(this);
        schedRecDataSource = new ScheduledRecordingDataSource(this);
        recordedShowDataSource = new RecordedShowDataSource(this);
        try {
            showInfoDataSource.open();
            channelDataSource.open();
            showTimeSlotDataSource.open();
            schedRecDataSource.open();
            recordedShowDataSource.open();
        } catch (Exception e) {
            Log.e("MainActivity -- DataSource Opening", "Failed to open a DataSource", e);
        }
        listingSource = new DummyListingSource();
	}
	
	@Override
	  public void onRestoreInstanceState(Bundle savedInstanceState) {
	    // Restore the previously serialized current tab position.
	    if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
	      getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
	    }
	  }

	  @Override
	  public void onSaveInstanceState(Bundle outState) {
	    // Serialize the current tab position.
	    outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
	        .getSelectedNavigationIndex());
	  }

	  
	  public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
	        private final Activity mActivity;
	        private final String mTag;
	        private final Class<T> mClass;
	        private final Bundle mArgs;
	        private Fragment mFragment;

	        public TabListener(Activity activity, String tag, Class<T> clz) {
	            this(activity, tag, clz, null);
	        }

	        public TabListener(Activity activity, String tag, Class<T> clz, Bundle args) {
	            mActivity = activity;
	            mTag = tag;
	            mClass = clz;
	            mArgs = args;

	            // Check to see if we already have a fragment for this tab, probably
	            // from a previously saved state.  If so, deactivate it, because our
	            // initial state is that a tab isn't shown.
	            mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
	            if (mFragment != null && !mFragment.isDetached()) {
	                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
	                ft.detach(mFragment);
	                ft.commit();
	            }
	        }

	        public void onTabSelected(Tab tab, FragmentTransaction ft) {
	            //if (mFragment == null) {
	                mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
	                ft.add(android.R.id.content, mFragment, mTag);
	            //} else {
	            //    ft.attach(mFragment);
	            //}
	        }

	        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	            Fragment preInitializedFragment = (Fragment) mActivity.getFragmentManager().findFragmentByTag(mTag);
	            if (preInitializedFragment != null) {
	                ft.detach(preInitializedFragment); 
	            } else if (mFragment != null) {
	                ft.detach(mFragment);
	            }
	        }

	        public void onTabReselected(Tab tab, FragmentTransaction ft) {
	        	if (mTag.equals(GUIDE))
	        	{
	        		Fragment preInitializedFragment = (Fragment) mActivity.getFragmentManager().findFragmentByTag(mTag);
	        		if (preInitializedFragment != null)
	        		{
	        			ft.detach(preInitializedFragment);
	        			Fragment fragment = new ChannelGuideFragment();
	        			ft.add(android.R.id.content, fragment, mTag);
	        		}
	        	}
	        	//else
	        		//Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
	        }
	    }

}
