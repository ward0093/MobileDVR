package com.MSSE.MobileDVR;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DummyListingSource implements ListingSource
{
	private Channel[] channels;
	private ShowTimeSlot[] timeSlots;
	private ArrayList<ShowTimeSlot> lastTimeSlots = null;
	private int lastChannel = 0;
	private int lastEndMinute = 0;

	@Override
	public Channel[] getChannels()
	{
		if (channels == null)
		{
			ArrayList<Channel> theChannels = new ArrayList<Channel>();

			makeUpChannels(theChannels);

			channels = new Channel[theChannels.size()];
			theChannels.toArray(channels);
		}

		return channels;
	}

	@Override
	public Channel getChannel(int number)
	{
		Channel result = null;

		getChannels();
		for (int i = 0; result == null && i < channels.length; ++i)
		{
			Channel theChannel = channels[i];
			if (theChannel.getNumber() == number)
				result = theChannel;
		}

		return result;
	}

	@Override
	public Channel getChannel(String name)
	{
		Channel result = null;

		getChannels();
		for (int i = 0; result == null && i < channels.length; ++i)
		{
			Channel theChannel = channels[i];
			if (theChannel.getName().equals(name))
				result = theChannel;
		}

		return result;
	}

	@Override
	public ShowTimeSlot[] getTimeSlots(Date begin, Date end)
	{
		ArrayList<ShowTimeSlot> timeSlots = new ArrayList<ShowTimeSlot>();

		makeUpTimeSlots(timeSlots);

		ShowTimeSlot[] result = new ShowTimeSlot[timeSlots.size()];
		timeSlots.toArray(result);

		return result;
	}

	private void makeUpChannels(ArrayList<Channel> channels)
	{
		channels.add(new Channel(2, "PBS"));
		channels.add(new Channel(3, "CNN"));
		channels.add(new Channel(4, "CBS"));
		channels.add(new Channel(5, "ABC"));
		channels.add(new Channel(6, "MCN"));
		channels.add(new Channel(7, "TBS"));
		channels.add(new Channel(8, "CW"));
		channels.add(new Channel(9, "FOX"));
		channels.add(new Channel(10, "myTV"));
		channels.add(new Channel(11, "NBC"));
	}
	
	private static int minute(int hour, int minute)
	{
		return hour*60 + minute;
	}

	private void makeUpTimeSlots(ArrayList<ShowTimeSlot> timeSlots)
	{
		addShow(timeSlots, "Austin City Limits", 2, minute(0, 0), minute(1, 0));
		addShow("Antiques Roadshow", 60);
		addShow("Suspicion (1941), NR", 120);
		addShow("Masterpiece Mystery!", 90);
		addShow("The Red Green Show", 30);
		addShow("Sesame Street", 60);
		addShow("Curious George Swings/Spring", 60);
		addShow("Super Why!", 30);
		addShow("Dinosaur Train", 30, minute(9, 0));
		addShow("Washington Week w/Glen Ifill", 30);
		addShow("Almanac", 60);
		addShow("The McLuaghlin Group", 30);
	}

	private void addShow(String title, int durationMinutes)
	{
		addShow(lastTimeSlots, title, lastChannel, lastEndMinute, lastEndMinute + durationMinutes);
	}

	private void addShow(String title, int durationMinutes, int expectedEndMinute)
	{
		addShow(title, durationMinutes);
		assert lastEndMinute == expectedEndMinute;
	}

	private void addShow(ArrayList<ShowTimeSlot> timeSlots, String title, int channelNum, int startMinute, int endMinute)
	{
		Channel channel = getChannel(channelNum);
		assert channel != null;
		lastChannel = channelNum;
		
		ShowInfo showInfo = new ShowInfo(title);
		Calendar cal = makeCalendar(startMinute);
		int durationMinutes = endMinute - startMinute;
		lastEndMinute = endMinute;
		
		ShowTimeSlot timeSlot = new ShowTimeSlot(showInfo, channel, cal.getTime(), durationMinutes);
		timeSlots.add(timeSlot);
	}
	
	private Calendar makeCalendar(int minuteOfDay)
	{
		Calendar cal = Calendar.getInstance();
		
		int hour = minuteOfDay / 60;
		int minute = minuteOfDay - hour * 60;
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		
		return cal;
	}
}
