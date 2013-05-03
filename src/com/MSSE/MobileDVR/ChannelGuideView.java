package com.MSSE.MobileDVR;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChannelGuideView extends LinearLayout
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
	private final Date START_TIME;
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
	
	/**
	 * The name, DP, is short to read better. It will take a certain number
	 * of device pixels and return the number of real pixels it takes up.
	 * This DP(12) means 12 device pixels.
	 * @param dp
	 * @return the number of real pixels corresponding to dp
	 */
	private int DP(int dp)
	{
		int result = (int)(dp * DENSITY + 0.5);
		return result;
	}

	public ChannelGuideView(Context context)
	{
		super(context);
		
		DisplayMetrics display = context.getResources().getDisplayMetrics();
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
		SHOW_TITLE_HEIGHT = (int)(CHANNEL_ROW_HEIGHT * (1.0f/3.0f));
		SHOW_DESCRIPTION_HEIGHT = SHOW_TITLE_HEIGHT;
		SHOW_PAD_VIEW_HEIGHT = CHANNEL_ROW_HEIGHT - SHOW_TITLE_HEIGHT - SHOW_DESCRIPTION_HEIGHT;
		SHOW_PADDING_LEFT = DP(2);
		SHOW_PADDING_RIGHT = SHOW_PADDING_LEFT;
		SHOW_PADDING_TOP = SHOW_PADDING_LEFT;
		SHOW_PADDING_BOTTOM = SHOW_PADDING_TOP;
		
		Calendar theTime = Calendar.getInstance();
		theTime.set(Calendar.MILLISECOND, 0);
		theTime.set(Calendar.SECOND, 0);
		theTime.set(Calendar.MINUTE, 0);
		theTime.set(Calendar.HOUR_OF_DAY, 0);
		START_TIME = theTime.getTime();
		
		build();
	}
	
	private void build()
	{
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		this.setOrientation(LinearLayout.VERTICAL);
		
		this.addView(makeHeader());
		this.addView(makeChannelsAndInfoScroller());
	}
	
	private android.view.View makeHeader()
	{
		LinearLayout header = new LinearLayout(getContext());
		header.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		header.setOrientation(LinearLayout.HORIZONTAL);
		
		Button search = new Button(getContext());
		search.setLayoutParams(new LayoutParams(SEARCH_WIDTH, LayoutParams.WRAP_CONTENT));
		search.setText("Search");
		header.addView(search);
		
		TextView timeView = new TextView(getContext());
		timeView.setLayoutParams(new LayoutParams(TIME_WIDTH, LayoutParams.WRAP_CONTENT));
		timeView.setText(hhmm(START_TIME));
		timeView.setTextSize(CHANNEL_TEXT_UNITS, CHANNEL_TEXT_SIZE);
		timeView.setGravity(Gravity.CENTER);
		timeView.setPadding(CHANNEL_PADDING_LEFT, CHANNEL_PADDING_TOP, CHANNEL_PADDING_RIGHT, CHANNEL_PADDING_BOTTOM);
		header.addView(timeView);
		
		return header;
	}
	
	private String hhmm(Date when)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(when);
		String result = String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
		return result;
	}
	
	private android.view.View makeChannelsAndInfoScroller()
	{
		ScrollView verticalScroller = new ScrollView(getContext());
		verticalScroller.addView(makeChannelsAndInfo());
		return verticalScroller;
	}
	
	private android.view.View makeChannelsAndInfo()
	{
		LinearLayout channelsAndInfo = new LinearLayout(getContext());
		channelsAndInfo.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		channelsAndInfo.setOrientation(LinearLayout.HORIZONTAL);
		
		channelsAndInfo.addView(makeChannels());
		channelsAndInfo.addView(makeShowInfoScroller());
		
		return channelsAndInfo;
	}
	
	private android.view.View makeChannels()
	{
		LinearLayout channelsView = new LinearLayout(getContext());
		channelsView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		channelsView.setOrientation(LinearLayout.VERTICAL);
		
		Channel[] channels = MainActivity.getListingSource().getChannels();
		
		for (int i = 0; i < channels.length; ++i)
		{
			channelsView.addView(makeChannel(channels[i]));
		}
		
		return channelsView;
	}
	
	private android.view.View makeChannel(Channel theChannel)
	{
		LinearLayout channelView = new LinearLayout(getContext());
		channelView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		channelView.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView channelNumberView = new TextView(getContext());
		channelNumberView.setLayoutParams(new LayoutParams(CHANNEL_NUMBER_WIDTH, CHANNEL_ROW_HEIGHT));
		channelNumberView.setText("" + theChannel.getNumber());
		channelNumberView.setTextSize(CHANNEL_TEXT_UNITS, CHANNEL_TEXT_SIZE);
		channelNumberView.setGravity(Gravity.CENTER);
		//channelNumberView.setPadding(CHANNEL_PADDING_LEFT, CHANNEL_PADDING_TOP, CHANNEL_PADDING_RIGHT, CHANNEL_PADDING_BOTTOM);
		channelView.addView(channelNumberView);
		
		TextView channelNameView = new TextView(getContext());
		channelNameView.setLayoutParams(new LayoutParams(CHANNEL_NAME_WIDTH, CHANNEL_ROW_HEIGHT));
		channelNameView.setText(theChannel.getName());
		channelNameView.setTextSize(CHANNEL_TEXT_UNITS, CHANNEL_TEXT_SIZE);
		//channelNumberView.setPadding(CHANNEL_PADDING_LEFT, CHANNEL_PADDING_TOP, CHANNEL_PADDING_RIGHT, CHANNEL_PADDING_BOTTOM);
		channelView.addView(channelNameView);

		
		return channelView;
	}
	
	/**
	 * To decide what goes into the horizontal scroller, we imagine that the data
	 * may grow arbitrarily large in the horizontal direction, and we likely will
	 * need to devise a way to add views as the user scrolls horizontally. However,
	 * while the data in the vertical direction is enough to require scrolling, the
	 * amount is bounded by the number of channels. Therefore we will place into the
	 * horizontal scroller a horizontal linear layout of vertical linear layouts.
	 * @return
	 */
	private android.view.View makeShowInfoScroller()
	{
		HorizontalScrollView horizontalScroller = new HorizontalScrollView(getContext());
		horizontalScroller.addView(makeShowInfoHorizontalLayout());
		return horizontalScroller;
	}
	
	private android.view.View makeShowInfoHorizontalLayout()
	{
		LinearLayout horizontalLayout = new LinearLayout(getContext());
		horizontalLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		Date theTime = (Date)START_TIME.clone();
		Date lastTime = MainActivity.getListingSource().latest();
		String debugStr;
		while (theTime.compareTo(lastTime) <= 0)
		{
			debugStr = hhmm(theTime);
			horizontalLayout.addView(makeShowInfoForTime(theTime));
			
			long ms = theTime.getTime();
			ms += 30 * 60L * 1000L;
			theTime.setTime(ms);
		}
		
		return horizontalLayout;
	}
	
	private android.view.View makeShowInfoForTime(Date theTime)
	{
		LinearLayout verticalLayout = new LinearLayout(getContext());
		verticalLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		verticalLayout.setOrientation(LinearLayout.VERTICAL);
		
		Channel[] channels = MainActivity.getListingSource().getChannels();
		
		String debugStr;
		for (int i = 0; i < channels.length; ++i)
		{
			String title = "";
			String description = "";

			Channel theChannel = channels[i];
			ShowTimeSlot showTimeSlot = MainActivity.getListingSource().lookupTimeSlot(theChannel, theTime);
			if (showTimeSlot != null)
			{
				debugStr = hhmm(showTimeSlot.getStartTime());
				debugStr = hhmm(showTimeSlot.getEndTime());
				ShowInfo showInfo = showTimeSlot.getShowInfo();
				if (showInfo != null)
				{
					title = showInfo.getTitle();
					description = showInfo.getDescription();
				}
			}
			
			TextView blankView = new TextView(getContext());
			blankView.setLayoutParams(new LayoutParams(SHOW_INFO_WIDTH, this.SHOW_PAD_VIEW_HEIGHT));
			blankView.setText("");
			blankView.setTextSize(CHANNEL_TEXT_UNITS, SHOW_INFO_TEXT_SIZE);
			verticalLayout.addView(blankView);

			TextView titleView = new TextView(getContext());
			titleView.setLayoutParams(new LayoutParams(SHOW_INFO_WIDTH, SHOW_TITLE_HEIGHT));
			titleView.setText(title);
			titleView.setTextSize(CHANNEL_TEXT_UNITS, SHOW_INFO_TEXT_SIZE);
			titleView.setPadding(SHOW_PADDING_LEFT, SHOW_PADDING_TOP, SHOW_PADDING_RIGHT, SHOW_PADDING_BOTTOM);
			verticalLayout.addView(titleView);
			
			TextView descrView = new TextView(getContext());
			descrView.setLayoutParams(new LayoutParams(SHOW_INFO_WIDTH, SHOW_DESCRIPTION_HEIGHT));
			descrView.setText(description);
			descrView.setTextSize(CHANNEL_TEXT_UNITS, SHOW_INFO_TEXT_SIZE);
			descrView.setPadding(SHOW_PADDING_LEFT, SHOW_PADDING_TOP, SHOW_PADDING_RIGHT, SHOW_PADDING_BOTTOM);
			verticalLayout.addView(descrView);
		}
		
		return verticalLayout;
	}
}
