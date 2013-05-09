package com.MSSE.MobileDVR.fragments.guide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.MSSE.MobileDVR.TabMainActivity;
import com.MSSE.MobileDVR.datamodel.Channel;
import com.MSSE.MobileDVR.datamodel.ShowInfo;
import com.MSSE.MobileDVR.datamodel.ShowTimeSlot;
import com.MSSE.MobileDVR.datasource.ListingSource;
import com.MSSE.MobileDVR.fragments.info.ShowInfoFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChannelGuideView extends LinearLayout implements ScrollListener, OnTouchListener,
		OnClickListener
{
	private final float DENSITY;

	private final int CHANNEL_TEXT_UNITS = TypedValue.COMPLEX_UNIT_SP;
	private final float CHANNEL_TEXT_SIZE = 18.0f;
	private final float SHOW_INFO_TEXT_SIZE = 12.0f;
	private final int CHANNEL_ROW_HEIGHT;
	private final int CHANNEL_NUMBER_WIDTH;
	private final int CHANNEL_NAME_WIDTH;
	private final int CHANNEL_PADDING_LEFT;
	private final int CHANNEL_PADDING_TOP;
	private final int CHANNEL_PADDING_RIGHT;
	private final int CHANNEL_PADDING_BOTTOM;
	private final int DISPLAY_WIDTH;
	private final int SEARCH_WIDTH;
	private final int SHOW_INFO_WIDTH;
	private final int SHOW_TITLE_HEIGHT;
	private final int SHOW_PAD_VIEW_HEIGHT;
	private final int SHOW_DESCRIPTION_HEIGHT;
	private final int SHOW_PADDING_LEFT;
	private final int SHOW_PADDING_TOP;
	private final int SHOW_PADDING_RIGHT;
	private final int SHOW_PADDING_BOTTOM;
	private final int TIME_WIDTH;
	private ObservableHorizontalScrollView timeView = null;
	private ObservableHorizontalScrollView showsView = null;
	private int scrollDeltaX = 0;
	
	private static LinearLayout searchAndTime = null;
	private static ScrollView channelsAndInfoScroller = null;

	private static final int backgroundRowColor[] =
	{
			0x55aaaaaa, 0x55111111
	};

	/**
	 * The name, DP, is short to read better. It will take a certain number
	 * of device pixels and return the number of real pixels it takes up.
	 * This DP(12) means 12 device pixels.
	 * 
	 * @param dp
	 * @return the number of real pixels corresponding to dp
	 */
	private int DP(int dp)
	{
		int result = DP(DENSITY, dp);
		return result;
	}
	
	private static int DP(float density, int dp)
	{
		int result = (int)(dp * density + 0.5);
		return result;
	}

	public ChannelGuideView(Activity activity)
	{
		super(activity);

		DisplayMetrics display = activity.getResources().getDisplayMetrics();
		DENSITY = display.density;

		DISPLAY_WIDTH = display.widthPixels;

		CHANNEL_ROW_HEIGHT = DP(60);
		CHANNEL_NUMBER_WIDTH = DP(40);
		CHANNEL_NAME_WIDTH = DP(60);
		CHANNEL_PADDING_LEFT = DP(4);
		CHANNEL_PADDING_RIGHT = CHANNEL_PADDING_LEFT;
		CHANNEL_PADDING_TOP = CHANNEL_PADDING_LEFT;
		CHANNEL_PADDING_BOTTOM = CHANNEL_PADDING_TOP;
		SEARCH_WIDTH = CHANNEL_NUMBER_WIDTH + CHANNEL_NAME_WIDTH;
		TIME_WIDTH = DISPLAY_WIDTH - SEARCH_WIDTH;
		SHOW_INFO_WIDTH = TIME_WIDTH;
		SHOW_TITLE_HEIGHT = (int) (CHANNEL_ROW_HEIGHT * 0.4);
		SHOW_DESCRIPTION_HEIGHT = CHANNEL_ROW_HEIGHT - SHOW_TITLE_HEIGHT;
		SHOW_PAD_VIEW_HEIGHT = CHANNEL_ROW_HEIGHT - SHOW_TITLE_HEIGHT - SHOW_DESCRIPTION_HEIGHT;
		SHOW_PADDING_LEFT = DP(10);
		SHOW_PADDING_RIGHT = DP(4);
		SHOW_PADDING_TOP = DP(4);
		SHOW_PADDING_BOTTOM = SHOW_PADDING_TOP;

		construct();
	}
	
	private void construct()
	{
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		this.setOrientation(LinearLayout.VERTICAL);

		this.addView(makeSearchAndTime());
		this.addView(makeChannelsAndInfoScroller());
	}

	private android.view.View makeSearchAndTime()
	{
//		if (searchAndTime != null)
//			((ViewGroup)searchAndTime.getParent()).removeView(searchAndTime);
//		else
		{
			searchAndTime = new LinearLayout(getContext());
			searchAndTime.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			searchAndTime.setOrientation(LinearLayout.HORIZONTAL);

			TextView search = new TextView(getContext());
			search.setLayoutParams(new LayoutParams(SEARCH_WIDTH, LayoutParams.WRAP_CONTENT));
			searchAndTime.addView(search);

			searchAndTime.addView(makeTimeView());
		}

		return searchAndTime;
	}

	private android.view.View makeTimeView()
	{
		timeView = new ObservableHorizontalScrollView(getContext());
		timeView.addListener(this);
		timeView.setOnTouchListener(this);

		LinearLayout timeLayoutView = new LinearLayout(getContext());
		timeLayoutView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		timeLayoutView.setOrientation(LinearLayout.HORIZONTAL);

		Date theTime = (Date) beginTime().clone();
		Date lastTime = endTime();
		long halfHour = 30L * 60L * 1000L;
		while (theTime.compareTo(lastTime) < 0)
		{
			TextView timeTextView = new TextView(getContext());
			timeTextView.setLayoutParams(new LayoutParams(TIME_WIDTH, LayoutParams.WRAP_CONTENT));
			timeTextView.setText(getTimeString(theTime));
			timeTextView.setTextSize(CHANNEL_TEXT_UNITS, CHANNEL_TEXT_SIZE);
			timeTextView.setGravity(Gravity.CENTER);
			timeTextView.setPadding(CHANNEL_PADDING_LEFT, CHANNEL_PADDING_TOP,
					CHANNEL_PADDING_RIGHT, CHANNEL_PADDING_BOTTOM);

			timeLayoutView.addView(timeTextView);

			theTime.setTime(theTime.getTime() + halfHour);
		}

		timeView.addView(timeLayoutView);

		return timeView;
	}

	public static String getTimeString(Date theTime)
	{
		DateFormat dateFormatter = SimpleDateFormat.getTimeInstance();
		String result = dateFormatter.format(theTime);
		return result;
	}

	private android.view.View makeChannelsAndInfoScroller()
	{
//		if (channelsAndInfoScroller != null)
//			((ViewGroup)channelsAndInfoScroller.getParent()).removeView(channelsAndInfoScroller);
//		else
		{
			channelsAndInfoScroller = new ScrollView(getContext());
			channelsAndInfoScroller.addView(makeChannelsAndInfo());
		}
		return channelsAndInfoScroller;
	}

	private android.view.View makeChannelsAndInfo()
	{
		LinearLayout channelsAndInfo = new LinearLayout(getContext());
		channelsAndInfo.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		channelsAndInfo.setOrientation(LinearLayout.HORIZONTAL);

		channelsAndInfo.addView(makeChannels());
		channelsAndInfo.addView(makeShowInfoScroller());

		return channelsAndInfo;
	}

	private android.view.View makeChannels()
	{
		LinearLayout channelsView = new LinearLayout(getContext());
		channelsView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		channelsView.setOrientation(LinearLayout.VERTICAL);

		Channel[] channels = TabMainActivity.getListingSource().getChannels();

		for (int i = 0; i < channels.length; ++i)
		{
			channelsView.addView(makeChannel(channels[i], backgroundRowColor[i & 1]));
		}

		return channelsView;
	}

	private android.view.View makeChannel(Channel theChannel, int bgColor)
	{
		LinearLayout channelView = new LinearLayout(getContext());
		channelView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		channelView.setOrientation(LinearLayout.HORIZONTAL);

		TextView channelNumberView = new TextView(getContext());
		channelNumberView
				.setLayoutParams(new LayoutParams(CHANNEL_NUMBER_WIDTH, CHANNEL_ROW_HEIGHT));
		channelNumberView.setText("" + theChannel.getNumber());
		channelNumberView.setTextSize(CHANNEL_TEXT_UNITS, CHANNEL_TEXT_SIZE);
		channelNumberView.setGravity(Gravity.CENTER);
		channelNumberView.setBackgroundColor(bgColor);
		//channelNumberView.setPadding(CHANNEL_PADDING_LEFT, CHANNEL_PADDING_TOP, CHANNEL_PADDING_RIGHT, CHANNEL_PADDING_BOTTOM);
		channelView.addView(channelNumberView);

		TextView channelNameView = new TextView(getContext());
		channelNameView.setLayoutParams(new LayoutParams(CHANNEL_NAME_WIDTH, CHANNEL_ROW_HEIGHT));
		channelNameView.setText(theChannel.getName());
		channelNameView.setTextSize(CHANNEL_TEXT_UNITS, CHANNEL_TEXT_SIZE);
		channelNameView.setGravity(Gravity.CENTER);
		channelNameView.setBackgroundColor(bgColor);
		//channelNumberView.setPadding(CHANNEL_PADDING_LEFT, CHANNEL_PADDING_TOP, CHANNEL_PADDING_RIGHT, CHANNEL_PADDING_BOTTOM);
		channelView.addView(channelNameView);

		return channelView;
	}

	/**
	 * To decide what goes into the horizontal scroller, we imagine that the
	 * data may grow arbitrarily large in the horizontal direction, and we
	 * likely
	 * will
	 * need to devise a way to add views as the user scrolls horizontally.
	 * However,
	 * while the data in the vertical direction is enough to require scrolling,
	 * the
	 * amount is bounded by the number of channels. Therefore we will place into
	 * the
	 * horizontal scroller a horizontal linear layout of vertical linear
	 * layouts.
	 * 
	 * @return
	 */
	private ObservableHorizontalScrollView makeShowInfoScroller()
	{
		showsView = new ObservableHorizontalScrollView(getContext());
		showsView.addListener(this);
		showsView.setOnTouchListener(this);

		showsView.addView(makeShowInfoHorizontalLayout());
		return showsView;
	}

	private android.view.View makeShowInfoHorizontalLayout()
	{
		LinearLayout horizontalLayout = new LinearLayout(getContext());
		horizontalLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

		Date theTime = (Date) beginTime().clone();
		Date lastTime = endTime();
		while (theTime.compareTo(lastTime) < 0)
		{
			Log.d("ChannelGuideView", "makeShowInfoHorizontalLayout at " + getTimeString(theTime));
			horizontalLayout.addView(makeShowInfoForTime(theTime));

			long ms = theTime.getTime();
			ms += 30 * 60L * 1000L;
			theTime.setTime(ms);
		}

		return horizontalLayout;
	}

	private Date beginTime()
	{
		Date result = TabMainActivity.getListingSource().getEarliest();
		return result;
	}

	private Date endTime()
	{
		long resultMS = TabMainActivity.getListingSource().getLatest().getTime();

		boolean makeResponseFaster = true;
		if (makeResponseFaster)
		{
			long beginMS = beginTime().getTime();

			long duration = 4; // hours
			duration *= 60; // minutes
			duration *= 60; // seconds
			duration *= 1000; // milliseconds

			resultMS = Math.min(beginMS + duration, resultMS);
		}

		Date result = new Date(resultMS);

		return result;
	}

	private android.view.View makeShowInfoForTime(Date theTime)
	{
		LinearLayout verticalLayout = new LinearLayout(getContext());
		verticalLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		verticalLayout.setOrientation(LinearLayout.VERTICAL);

		Channel[] channels = TabMainActivity.getListingSource().getChannels();

		for (int i = 0; i < channels.length; ++i)
		{
			LinearLayout infoHolder = new LinearLayout(getContext());
			infoHolder.setLayoutParams(new LayoutParams(SHOW_INFO_WIDTH, CHANNEL_ROW_HEIGHT));
			infoHolder.setOrientation(LinearLayout.VERTICAL);
			infoHolder.setGravity(Gravity.CENTER);
			infoHolder.setBackgroundColor(backgroundRowColor[i & 1]);

			String title = "";
			String description = "";

			Channel theChannel = channels[i];
			ShowTimeSlot showTimeSlot = TabMainActivity.getListingSource().lookupTimeSlot(
					theChannel, theTime);
			if (showTimeSlot != null)
			{
				infoHolder.setTag(showTimeSlot);
				ShowInfo showInfo = showTimeSlot.getShowInfo();
				if (showInfo != null)
				{
					title = showInfo.getTitle();
					description = showInfo.getDescription();
				}

				infoHolder.setOnClickListener(this);
			}

			TextView titleView = new TextView(getContext());
			titleView.setLayoutParams(new LayoutParams(SHOW_INFO_WIDTH, SHOW_TITLE_HEIGHT));
			titleView.setText(title);
			titleView.setTextSize(CHANNEL_TEXT_UNITS, SHOW_INFO_TEXT_SIZE);
			titleView.setPadding(SHOW_PADDING_LEFT, SHOW_PADDING_TOP, SHOW_PADDING_RIGHT,
					SHOW_PADDING_BOTTOM);
			infoHolder.addView(titleView);

			TextView descrView = new TextView(getContext());
			descrView.setLayoutParams(new LayoutParams(SHOW_INFO_WIDTH, SHOW_DESCRIPTION_HEIGHT));
			descrView.setText(description);
			descrView.setTextSize(CHANNEL_TEXT_UNITS, SHOW_INFO_TEXT_SIZE);
			descrView.setPadding(SHOW_PADDING_LEFT, SHOW_PADDING_TOP, SHOW_PADDING_RIGHT,
					SHOW_PADDING_BOTTOM);
			infoHolder.addView(descrView);

			verticalLayout.addView(infoHolder);
		}

		return verticalLayout;
	}

	private Activity getActivity()
	{
		return (Activity) getContext();
	}

	@Override
	public void onScrollChanged(View scrollView, int x, int y, int oldx, int oldy)
	{
		scrollDeltaX = x - oldx;

		if (scrollView == timeView)
			showsView.scrollTo(x, y);
		else if (scrollView == showsView)
			timeView.scrollTo(x, y);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		int action = event.getAction();
		Log.d("onTouch", "a=" + action + ", scrollDeltaX=" + scrollDeltaX);
		boolean handled = false;
		int x = 0;
		if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL)
		{
			handled = true;
			x = timeView.getScrollX();
			int threshhold = 25;
			if (scrollDeltaX < -threshhold)
				x -= TIME_WIDTH - 1;
			else if (scrollDeltaX > threshhold)
				x += TIME_WIDTH - 1;
			else
			{
				x += TIME_WIDTH / 2;
				Log.d("onTouch", "slow");
			}
			x = (x / TIME_WIDTH) * TIME_WIDTH;
		}

		if (handled)
		{
			Log.d("onTouch", "smooth to " + x);
			timeView.smoothScrollTo(x, timeView.getScrollY());
			showsView.smoothScrollTo(x, showsView.getScrollY());
		}

		return handled;
	}
	
	private static void launchShowInfo(Activity activity, ShowTimeSlot showTimeSlot)
	{
		int iShowInfo = 1;
		activity.getActionBar().setSelectedNavigationItem(iShowInfo);
		Fragment fragment = new ShowInfoFragment();
		Bundle args = new Bundle();
		args.putInt(ChannelGuideFragment.CHANNEL_ID, showTimeSlot.getChannel().getNumber());
        args.putInt(ChannelGuideFragment.ADD_OR_EDIT_OPTIONS, 0); //this is a ADD = 0
		args.putSerializable(ChannelGuideFragment.TIME_SLOT_DATE, showTimeSlot.getStartTime());
		fragment.setArguments(args);
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		ft.replace(android.R.id.content, fragment, "info");
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public void onClick(View v)
	{
		Object tag = v.getTag();
		if (tag instanceof ShowTimeSlot)
		{
			ShowTimeSlot showTimeSlot = (ShowTimeSlot)tag;
			launchShowInfo(getActivity(), showTimeSlot);
		}
	}

	private static class ShowMatcher
	{
		private String lcQuery;

		public ShowMatcher(String query)
		{
			lcQuery = query.toLowerCase();
		}

		public boolean matches(ShowInfo showInfo)
		{
			boolean result = false;

			if (showInfo.getTitle().toLowerCase().contains(lcQuery))
				result = true;
			else if (showInfo.getDescription().toLowerCase().contains(lcQuery))
				result = true;

			return result;
		}
	}
	
	public static View generateQueryResultsView(final Activity activity, String query)
	{
		DisplayMetrics display = activity.getResources().getDisplayMetrics();
		final float DENSITY = display.density;
		final int DISPLAY_WIDTH = display.widthPixels;
		final int CHANNEL_NUMBER_WIDTH = DP(DENSITY, 24);
		final int CHANNEL_NAME_WIDTH = DP(DENSITY, 48);
		final int TIME_WIDTH = DP(DENSITY, 80);
		final int TITLE_WIDTH = DISPLAY_WIDTH - TIME_WIDTH - CHANNEL_NAME_WIDTH - CHANNEL_NUMBER_WIDTH;
		final int ROW_HEIGHT = DP(DENSITY, 40);
		LinearLayout queryView = null;

		ShowMatcher matcher = new ShowMatcher(query);
		ListingSource listingSource = TabMainActivity.getListingSource();
		ShowInfo[] shows = listingSource.getShows();
		if (shows.length > 0)
		{
			queryView = new LinearLayout(activity);
			queryView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			queryView.setOrientation(LinearLayout.VERTICAL);
			int row = 0;

			for (int i = 0; i < shows.length; ++i)
			{
				ShowInfo theShow = shows[i];
				if (matcher.matches(theShow))
				{
					ShowTimeSlot[] airings = listingSource.getTimeSlotsForShow(theShow);
					for (int j = 0; j < airings.length; ++j)
					{
						int color = backgroundRowColor[row++ & 1];
						
						ShowTimeSlot timeSlot = airings[j];
						Channel channel = timeSlot.getChannel();
						LinearLayout infoView = new LinearLayout(activity);
						infoView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ROW_HEIGHT));
						infoView.setOrientation(LinearLayout.HORIZONTAL);
						
						TextView channelNumberView = new TextView(activity);
						channelNumberView.setLayoutParams(new LayoutParams(CHANNEL_NUMBER_WIDTH, ROW_HEIGHT));
						channelNumberView.setText("" + channel.getNumber());
						channelNumberView.setGravity(Gravity.CENTER);
						channelNumberView.setBackgroundColor(color);
						infoView.addView(channelNumberView);
						
						TextView channelNameView = new TextView(activity);
						channelNameView.setLayoutParams(new LayoutParams(CHANNEL_NAME_WIDTH, ROW_HEIGHT));
						channelNameView.setText(channel.getName());
						channelNameView.setGravity(Gravity.CENTER);
						channelNameView.setBackgroundColor(color);
						infoView.addView(channelNameView);
						
						TextView timeView = new TextView(activity);
						timeView.setLayoutParams(new LayoutParams(TIME_WIDTH, ROW_HEIGHT));
						timeView.setText(getTimeString(timeSlot.getStartTime()));
						timeView.setGravity(Gravity.CENTER);
						timeView.setBackgroundColor(color);
						infoView.addView(timeView);
						
						TextView titleView = new TextView(activity);
						titleView.setLayoutParams(new LayoutParams(TITLE_WIDTH, ROW_HEIGHT));
						titleView.setText(timeSlot.getShowInfo().getTitle());
						titleView.setGravity(Gravity.CENTER);
						titleView.setBackgroundColor(color);
						infoView.addView(titleView);
						
						infoView.setTag(timeSlot);
						infoView.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v)
							{
								Object tag = v.getTag();
								if (tag instanceof ShowTimeSlot)
								{
									ShowTimeSlot showTimeSlot = (ShowTimeSlot)tag;
									launchShowInfo(activity, showTimeSlot);
								}
							}
							
						});
						
						queryView.addView(infoView);
					}
				}
			}
		}
		
		return queryView;
	}
}
