package com.MSSE.MobileDVR;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
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
		
		Calendar now = Calendar.getInstance();
		now.set(Calendar.MILLISECOND, 0);
		now.set(Calendar.SECOND, 0);
		int minute = now.get(Calendar.MINUTE);
		minute -= minute % 30;
		now.set(Calendar.MINUTE, minute);
		START_TIME = now.getTime();
		
		build();
	}
	
	private class RowResult
	{
		private LinearLayout channelInfo;
		private LinearLayout showInfo;
		
		public RowResult(LinearLayout channelInfo, LinearLayout showInfo)
		{
			this.channelInfo = channelInfo;
			this.showInfo = showInfo;
		}
		
		public android.view.View getChannelInfo()
		{
			return channelInfo;
		}
		
		public android.view.View getShowInfo()
		{
			return showInfo;
		}
	}
	
	private void build()
	{
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		this.setOrientation(LinearLayout.VERTICAL);
		
		android.view.View header = makeHeader();
		this.addView(header);
		
		ScrollView scrollView = new ScrollView(getContext());
		
		LinearLayout rowsView = new LinearLayout(getContext());		
		rowsView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		rowsView.setOrientation(LinearLayout.VERTICAL);
		
		ListingSource listings = getListingSource();
		Channel[] channels = listings.getChannels();
		
		for (int i = 0; i < channels.length; ++i)
		{
			Channel theChannel = channels[i];
			
			RowResult row = makeRow(theChannel);
			
			rowsView.addView(row.getChannelInfo());
		}
		
		scrollView.addView(rowsView);
		this.addView(scrollView);
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
	
	private ListingSource getListingSource()
	{
		return MainActivity.listingSource;
	}
	
	private RowResult makeRow(Channel theChannel)
	{
		LinearLayout channelInfo = new LinearLayout(getContext());
		channelInfo.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		channelInfo.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView channelNumberView = new TextView(getContext());
		LayoutParams numberLayout = new LayoutParams(CHANNEL_NUMBER_WIDTH, CHANNEL_ROW_HEIGHT);
		channelNumberView.setLayoutParams(numberLayout);
		channelNumberView.setText("" + theChannel.getNumber());
		channelNumberView.setTextSize(CHANNEL_TEXT_UNITS, CHANNEL_TEXT_SIZE);
		channelNumberView.setGravity(Gravity.CENTER);
		channelNumberView.setPadding(CHANNEL_PADDING_LEFT, CHANNEL_PADDING_TOP, CHANNEL_PADDING_RIGHT, CHANNEL_PADDING_BOTTOM);
		channelInfo.addView(channelNumberView);
		
		TextView channelNameView = new TextView(getContext());
		LayoutParams nameLayout = new LayoutParams(CHANNEL_NAME_WIDTH, CHANNEL_ROW_HEIGHT);
		channelNameView.setLayoutParams(nameLayout);
		channelNameView.setText(theChannel.getName());
		channelNameView.setTextSize(CHANNEL_TEXT_UNITS, CHANNEL_TEXT_SIZE);
		channelNumberView.setPadding(CHANNEL_PADDING_LEFT, CHANNEL_PADDING_TOP, CHANNEL_PADDING_RIGHT, CHANNEL_PADDING_BOTTOM);
		channelInfo.addView(channelNameView);

		LinearLayout showInfoView = new LinearLayout(getContext());
		showInfoView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		showInfoView.setOrientation(LinearLayout.VERTICAL);
		
		Date theTime = (Date)START_TIME.clone();
		Date lastTime = MainActivity.listingSource.latest();
		String s;
		while (theTime.compareTo(lastTime) <= 0)
		{
			s = hhmm(theTime);
			ShowTimeSlot showTimeSlot = MainActivity.listingSource.lookupTimeSlot(theChannel, theTime);
			if (showTimeSlot != null)
			{
				s = hhmm(showTimeSlot.getStartTime());
				s = hhmm(showTimeSlot.getEndTime());
				ShowInfo showInfo = showTimeSlot.getShowInfo();
				TextView titleView = new TextView(getContext());
				LayoutParams titleLayout = new LayoutParams(SHOW_INFO_WIDTH, LayoutParams.WRAP_CONTENT);
				titleView.setLayoutParams(titleLayout);
				titleView.setText(showInfo.getTitle());
				titleView.setTextSize(CHANNEL_TEXT_UNITS, SHOW_INFO_TEXT_SIZE);
				showInfoView.addView(titleView);
				
				TextView descrView = new TextView(getContext());
				LayoutParams descrLayout = new LayoutParams(SHOW_INFO_WIDTH, LayoutParams.WRAP_CONTENT);
				descrView.setLayoutParams(descrLayout);
				descrView.setText(showInfo.getDescription());
				descrView.setTextSize(CHANNEL_TEXT_UNITS, SHOW_INFO_TEXT_SIZE);
				showInfoView.addView(descrView);
			}
			
			long ms = theTime.getTime();
			ms += 30 * 60L * 1000L;
			theTime.setTime(ms);
		}
		
		channelInfo.addView(showInfoView);
		
		RowResult result = new RowResult(channelInfo, showInfoView);
		return result;
	}
}
