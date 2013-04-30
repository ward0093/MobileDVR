package com.MSSE.MobileDVR;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ChannelGuide extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);		
		
		ChannelGuideView view = new ChannelGuideView(this);
		
		setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.channel_guide, menu);
		return true;
	}

}
