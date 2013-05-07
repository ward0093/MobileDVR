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
import com.MSSE.MobileDVR.datasource.sql.ChannelDataSource;
import com.MSSE.MobileDVR.datasource.sql.ScheduledRecordingDataSource;
import com.MSSE.MobileDVR.datasource.sql.ShowInfoDataSource;
import com.MSSE.MobileDVR.datasource.sql.ShowTimeSlotDataSource;
import com.MSSE.MobileDVR.fragments.guide.ChannelGuideFragment;
import com.MSSE.MobileDVR.fragments.info.RecordOptionFragment;
import com.MSSE.MobileDVR.fragments.info.ShowInfoFragment;
import com.MSSE.MobileDVR.fragments.more.MoreFragment;
import com.MSSE.MobileDVR.fragments.recorded.MyShowsFragment;

public class TabMainActivity extends Activity {

	/**
	* The serialization (saved instance state) Bundle key representing the
	* current tab position.
	*/

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private static ListingSource listingSource = new DummyListingSource();
    //public static final ScheduledRecordingSource scheduledRecordings = new ScheduledRecordingFile();
    public static final RecordedShowSource myRecordedShows = new RecordedShowFile();
    private static ShowInfoDataSource showInfoDataSource;
    private static ChannelDataSource channelDataSource;
    private static ShowTimeSlotDataSource showTimeSlotDataSource;
    private static ScheduledRecordingDataSource schedRecDataSource;
	
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
                        this, "guide", ChannelGuideFragment.class)));
	    actionBar.addTab(actionBar.newTab()
                .setText(R.string.title_info)
                //.setIcon(R.drawable.info)
                .setTabListener(new TabListener<TestFragment>(
                        this, "info", TestFragment.class)));
	    actionBar.addTab(actionBar.newTab()
                .setText(R.string.title_myshows)
               // .setIcon(R.drawable.my_shows)
                .setTabListener(new TabListener<MyShowsFragment>(
                        this, "myshows", MyShowsFragment.class)));
	    actionBar.addTab(actionBar.newTab()
                .setText(R.string.title_more)
                //.setIcon(R.drawable.more)
                .setTabListener(new TabListener<MoreFragment>(
                        this, "more", MoreFragment.class)));

        showInfoDataSource = new ShowInfoDataSource(this);
        channelDataSource = new ChannelDataSource(this);
        showTimeSlotDataSource = new ShowTimeSlotDataSource(this);
        schedRecDataSource = new ScheduledRecordingDataSource(this);
        try {
            showInfoDataSource.open();
            channelDataSource.open();
            showTimeSlotDataSource.open();
            schedRecDataSource.open();
        } catch (Exception e) {
            Log.e("MainActivity -- DataSource Opening", "Failed to open a DataSource", e);
        }
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

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		//getMenuInflater().inflate(R.menu.activity_tab_main, menu);
//
//		//return true;
//	}
	
	

//	  /**
//	   * A dummy fragment representing a section of the app
//	   */
//
//	  public static class DummySectionFragment extends Fragment {
//	    public static final String ARG_SECTION_NUMBER = "placeholder_text";
//	    TextView textView;
//	    @Override
//	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//	        Bundle savedInstanceState) {
//	      textView = new TextView(getActivity());
//	      textView.setGravity(Gravity.CENTER);
//	      textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
//	      return textView;
//	    }
//	    
//	    
//	    public void onActivityCreated(Bundle savedInstanceState) {
//	        super.onActivityCreated(savedInstanceState);
//	    }
//	   
//
//		  @Override
//		  public void onSaveInstanceState(Bundle outState) {
//		    // Serialize the current tab position.
//		    outState.putCharSequence(ARG_SECTION_NUMBER, textView.getText());
//		  }
//	  }
	  
	  public static class TestFragment extends Fragment {
	  
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
        
	        View view = inflater.inflate(R.layout.main, container, false);
	        
	        ShowInfo testShow = new ShowInfo("Mythbusters", "Greatest show on Earth");

	        Button onlyButton = (Button) view.findViewById(R.id.show_button);
	        onlyButton.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	ShowInfo showInfos[] = listingSource.getShows();
	                ShowTimeSlot showTimeSlot[] = listingSource.getTimeSlotsForShow(showInfos[0]);
 	            	Fragment fragment = new ShowInfoFragment();
	            	Bundle args = new Bundle();
	            	args.putInt(ChannelGuideFragment.CHANNEL_ID, showTimeSlot[0].getChannel().getNumber());
	            	args.putSerializable(ChannelGuideFragment.TIME_SLOT_DATE, showTimeSlot[0].getStartTime());
	            	fragment.setArguments(args);
	            	FragmentTransaction ft = getFragmentManager().beginTransaction();
	            	ft.replace(android.R.id.content, fragment, "info");
	            	ft.addToBackStack(null);
	            	ft.commit();
	            }
	        });
	        
	        setMenuVisibility(true);
	        setHasOptionsMenu(true);

	        Button recordedShows = (Button) view.findViewById(R.id.recorded_shows_button);
	        recordedShows.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {

	            	//RecordedShow myRecordings[] = myRecordedShows.getRecordedShows();

//	            	Fragment fragment = new RecordOptionFragment();
//	            	Bundle args = new Bundle();
//	            	//set you arguments that you need to pass to the RecordOptionFragment
//	            	fragment.setArguments(args);
//	            	FragmentTransaction ft = getFragmentManager().beginTransaction();
//	            	ft.replace(android.R.id.content, fragment, "info");
//	            	ft.addToBackStack(null);
//	            	ft.commit();
            	
	            }
	        });
	        return view;
	    }
	  
	  @Override
		public void onPrepareOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
		  super.onPrepareOptionsMenu(menu);
		  menu.clear();
		  getActivity().getMenuInflater().inflate(R.menu.activity_tab_main, menu);
		 }
	  
	  
//	  @Override
//		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//			// Inflate the menu; this adds items to the action bar if it is present.
//		  super.onCreateOptionsMenu(menu,inflater);
//		  menu.clear();
//		  inflater.inflate(R.menu.activity_tab_main, menu);
//		 }
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
	            if (mFragment == null) {
	                mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
	                ft.add(android.R.id.content, mFragment, mTag);
	            } else {
	                ft.attach(mFragment);
	            }
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
	            Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
	        }
	    }

}
