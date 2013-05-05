package com.MSSE.MobileDVR;

import com.MSSE.MobileDVR.datamodel.ShowInfo;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TabMainActivity extends Activity {

	/**
	* The serialization (saved instance state) Bundle key representing the
	* current tab position.
	*/

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	
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
                .setTabListener(new TabListener<MainActivityFragment>(
                        this, "guide", MainActivityFragment.class)));
	    actionBar.addTab(actionBar.newTab()
                .setText(R.string.title_info)
                //.setIcon(R.drawable.info)
                .setTabListener(new TabListener<MainActivityFragment>(
                        this, "info", MainActivityFragment.class)));
	    actionBar.addTab(actionBar.newTab()
                .setText(R.string.title_myshows)
               // .setIcon(R.drawable.my_shows)
                .setTabListener(new TabListener<MainActivityFragment>(
                        this, "myshows", MainActivityFragment.class)));
	    actionBar.addTab(actionBar.newTab()
                .setText(R.string.title_more)
                //.setIcon(R.drawable.more)
                .setTabListener(new TabListener<MainActivityFragment>(
                        this, "more", MainActivityFragment.class)));		
	    
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
	
	

	  /**
	   * A dummy fragment representing a section of the app
	   */

	  public static class DummySectionFragment extends Fragment {
	    public static final String ARG_SECTION_NUMBER = "placeholder_text";
	    TextView textView;
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	      textView = new TextView(getActivity());
	      textView.setGravity(Gravity.CENTER);
	      textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
	      return textView;
	    }
	    
	    
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	    }
	   

		  @Override
		  public void onSaveInstanceState(Bundle outState) {
		    // Serialize the current tab position.
		    outState.putCharSequence(ARG_SECTION_NUMBER, textView.getText());
		  }
	  }
	  
	  public static class MainActivityFragment extends Fragment {
	  
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
        
	        View view = inflater.inflate(R.layout.main, container, false);
	        Button guideButton = (Button)view.findViewById(R.id.guide_button);
	        guideButton.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View v) {
					//Toast.makeText(this, "Edit Button clicked!", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(getActivity(), ChannelGuide.class);
					//intent.putExtra(CONTACT_ID, storage.newContact().getID());
					//intent.putExtra("contact", storage.newContact());
					//intent.putExtra(REPOSITORY, storage);
					startActivityForResult(intent, 1);
				}
	        });
	        ShowInfo testShow = new ShowInfo("Mythbusters", "Greatest show on Earth");

	        Button onlyButton = (Button) view.findViewById(R.id.show_button);
	        onlyButton.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Fragment fragment = new DummySectionFragment();
	            	Bundle args = new Bundle();
	            	args.putInt(DummySectionFragment.ARG_SECTION_NUMBER,
	            	        1 + 1);
	            	fragment.setArguments(args);
	            	FragmentTransaction ft = getFragmentManager().beginTransaction();
	            	ft.replace(android.R.id.content, fragment);
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
	                //Toast.makeText(this, "Edit Button clicked!", Toast.LENGTH_LONG).show();
	                Intent intent = new Intent( getActivity(), RecordedShowsActivity.class);
	                //intent.putExtra(CONTACT_ID, storage.newContact().getID());
	                //intent.putExtra("contact", storage.newContact());
	                //intent.putExtra(REPOSITORY, storage);
	                getActivity().startActivity(intent);
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
	            if (mFragment != null) {
	                ft.detach(mFragment);
	            }
	        }

	        public void onTabReselected(Tab tab, FragmentTransaction ft) {
	            Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
	        }
	    }

}
