package com.MSSE.MobileDVR.fragments.guide;

import java.util.ArrayList;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class ObservableHorizontalScrollView extends HorizontalScrollView
{
	private ArrayList<ScrollListener> listeners = new ArrayList<ScrollListener>();

	public ObservableHorizontalScrollView(Context context)
	{
		super(context);
	}

	public ObservableHorizontalScrollView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public ObservableHorizontalScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	public void addListener(ScrollListener listener)
	{
		listeners.add(listener);
	}
	
	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy)
	{
		super.onScrollChanged(x, y, oldx, oldy);
		
		for (ScrollListener listener : listeners)
		{
			listener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}

}
