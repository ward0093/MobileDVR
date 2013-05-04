package com.MSSE.MobileDVR;

import java.util.Date;

public interface ListingSource
{

	public Channel[] getChannels();
	
	public Channel lookupChannel(int number);
	public Channel lookupChannel(String name);
	
	public Date latest(); // can retrieve info from now through latest()
	
	public ShowInfo[] getShows();
	
	public ShowTimeSlot[] getTimeSlotsForShow(ShowInfo theShow);
	
	public ShowTimeSlot lookupTimeSlot(Channel channel, Date time);

    public RecordedShow[] getRecordedShows();
	
}
