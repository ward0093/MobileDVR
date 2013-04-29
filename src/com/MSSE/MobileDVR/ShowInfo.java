package com.MSSE.MobileDVR;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

public class ShowInfo
{
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((timeSlots == null) ? 0 : timeSlots.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShowInfo other = (ShowInfo) obj;
		if (description == null)
		{
			if (other.description != null)
				return false;
		}
		else if (!description.equals(other.description))
			return false;
		if (image == null)
		{
			if (other.image != null)
				return false;
		}
		else if (!image.equals(other.image))
			return false;
		if (timeSlots == null)
		{
			if (other.timeSlots != null)
				return false;
		}
		else if (!timeSlots.equals(other.timeSlots))
			return false;
		if (title == null)
		{
			if (other.title != null)
				return false;
		}
		else if (!title.equals(other.title))
			return false;
		return true;
	}

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
