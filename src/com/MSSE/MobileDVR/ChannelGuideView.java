package com.MSSE.MobileDVR;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChannelGuideView extends LinearLayout
{
	private final float DENSITY;

	private final int CHANNEL_TEXT_UNITS = TypedValue.COMPLEX_UNIT_SP;
	private final float CHANNEL_TEXT_SIZE = 18.0f;
	private final int CHANNEL_ROW_HEIGHT;
	private final int CHANNEL_NUMBER_WIDTH;
	private final int CHANNEL_NAME_WIDTH;
	private final int CHANNEL_PADDING_LEFT;
	private final int CHANNEL_PADDING_TOP;
	private final int CHANNEL_PADDING_RIGHT;
	private final int CHANNEL_PADDING_BOTTOM;
	private final Date START_TIME;
	private final int DISPLAY_WIDTH;
	
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
		
		Calendar now = Calendar.getInstance();
		now.set(Calendar.SECOND, 0);
		int minute = now.get(Calendar.MINUTE);
		now.set(Calendar.MINUTE, (minute / 30) * 30);
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
		
		ListingSource listings = getListingSource();
		Channel[] channels = listings.getChannels();
		
		for (int i = 0; i < channels.length; ++i)
		{
			Channel theChannel = channels[i];
			
			RowResult row = makeRow(theChannel);
			
			this.addView(row.getChannelInfo());
		}
	}
	
	private android.view.View makeHeader()
	{
		LinearLayout header = new LinearLayout(getContext());
		header.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		header.setOrientation(LinearLayout.HORIZONTAL);
		
		Button search = new Button(getContext());
		int searchWidth = CHANNEL_NUMBER_WIDTH + CHANNEL_NAME_WIDTH;
		search.setLayoutParams(new LayoutParams(searchWidth, LayoutParams.WRAP_CONTENT));
		search.setText("Search");
		header.addView(search);
		
		TextView timeView = new TextView(getContext());
		timeView.setLayoutParams(new LayoutParams(DISPLAY_WIDTH - searchWidth, LayoutParams.WRAP_CONTENT));
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

		LinearLayout showInfo = new LinearLayout(getContext());
		showInfo.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		showInfo.setOrientation(LinearLayout.VERTICAL);
		
		RowResult result = new RowResult(channelInfo, showInfo);
		return result;
	}
}
