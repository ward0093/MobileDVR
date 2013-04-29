package com.MSSE.MobileDVR;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChannelGuide extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(newContentView());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.channel_guide, menu);
		return true;
	}
	
	android.view.View newContentView()
	{
		LinearLayout result = (LinearLayout) new LinearLayout(this);
		result.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		result.setOrientation(LinearLayout.VERTICAL);
		
		ListingSource listings = getListingSource();
		Channel[] channels = listings.getChannels();
		
		for (int i = 0; i < channels.length; ++i)
		{
			Channel theChannel = channels[i];
			TextView textView = new TextView(this);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			textView.setLayoutParams(layoutParams);
			textView.setText(theChannel.getName() + " " + theChannel.getNumber());
			
			result.addView(textView);
		}
		
		return result;
	}
	
	private ListingSource getListingSource()
	{
		return new DummyListingSource();
	}

}
