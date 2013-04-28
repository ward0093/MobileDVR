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
		// This is 24 hours for channel 2
		//
		addShow(timeSlots, "Austin City Limits", 2, minute(0, 0), minute(1, 0));
		addShow("Antiques Roadshow", 60);
		addShow("Suspicion (1941), NR", 120);
		addShow("Masterpiece Mystery!", 90);
		addShow("The Red Green Show", 30);
		addShow("Sesame Street", 60);
		addShow("Curious George Swings/Spring", 60);
		addShow("Super Why!", 30);
		addShow("Dinosaur Train", 30);
		addShow("Washington Week w/Glen Ifill", 30);
		addShow("Almanac", 60);
		addShow("The McLuaghlin Group", 30);
		addShow("To the Contrary/Bonnie Erbe", 30);
		addShow("Religion & Ethics News Weekly", 30);
		addShow("Moyers & Company", 60);
		addShow("Spanning Time: Covered Bridges", 60);
		addShow("Tribute to Bacharach and David", 60);
		addShow("Live From Lincoln Center", 2*60+30);
		addShow("Great Romances of 20th Century", 30);
		addShow("mn original", 30);
		addShow("Rudy Maxa's World", 30);
		addShow("Call the midwife", 60);
		addShow("Masterpiece Classic", 60);
		addShow("The Bletchley Circle", 60);
		addShow("mn original", 30);
		addShow("Independent Lens", 90);
		assert lastEndMinute == minute(24, 0);
		
		// This is 24 hours for channel 3
		addShow(timeSlots, "Anderson Cooper Special Report", 3, minute(1, 0), minute(2, 0));
		addShow("WH Correspondents' Dinner", 120);
		addShow("Anderson Cooper Special Report", 60);
		addShow("Weekend Early Start", 90);
		addShow("Sunday Morning", 30);
		addShow("Sunday Morning", 60);
		addShow("State of the Union/Crowley", 60);
		addShow("Fareed Zakaria GPS", 60);
		addShow("Reliable Sources", 60);
		addShow("State of the Union/Crowley", 60);
		addShow("Fareed Zakaria GPS", 60);
		addShow("CNN Newsroom", 60);
		addShow("CNN Newsroom", 60);
		addShow("CNN Newsroom", 120);
		addShow("CNN Newsroom", 120);
		addShow("Anthony Bourdain Parts Unknown", 60);
		addShow("Anderson Cooper Special Report", 60);
		addShow("Anthony Bourdain Parts Unknown", 60);
		addShow("Anthony Bourdain Parts Unknown", 60);
		assert lastEndMinute == minute(24, 0);
	}

	private void addShow(String title, int durationMinutes)
	{
		addShow(lastTimeSlots, title, lastChannel, lastEndMinute, lastEndMinute + durationMinutes);
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
