package com.MSSE.MobileDVR;

import java.util.Date;

public interface ListingSource
{

	public Channel[] getChannels();
	public Channel getChannel(int number);
	public Channel getChannel(String name);
	
	public ShowTimeSlot[] getTimeSlots(Date begin, Date end);
	
}
