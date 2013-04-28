package com.MSSE.MobileDVR;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

public class ShowInfo
{
	private ArrayList<ShowTimeSlot> timeSlots = new ArrayList<ShowTimeSlot>();
	private String title = "";
	private String description = "";
	private Drawable image = null;
	
	public ShowInfo()
	{
		this("", "", null);
	}
	
	public ShowInfo(String title)
	{
		this(title, "", null);
	}
	
	public ShowInfo(String title, String description)
	{
		this(title, description, null);
	}
	
	public ShowInfo(String title, String description, Drawable image)
	{
		setTitle(title);
		setDescription(description);
		setImage(image);
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public Drawable getImage()
	{
		return image;
	}
	
	public ShowTimeSlot[] getTimeSlots()
	{
		ShowTimeSlot[] result = new ShowTimeSlot[timeSlots.size()];
		timeSlots.toArray(result);
		return result;
	}
	
	private void setTitle(String title)
	{
		this.title = title != null ? title : "";
	}
	
	private void setDescription(String description)
	{
		this.description = description != null ? description : "";
	}
	
	private void setImage(Drawable image)
	{
		this.image = image; // no check for null
	}
	
}
