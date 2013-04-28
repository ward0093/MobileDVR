package com.MSSE.MobileDVR;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class DummyListingSource implements ListingSource
{
	private Channel[] channels = null;
	private ShowTimeSlot[] timeSlots = null;
	private ShowInfo[] shows = null;
	private ArrayList<ShowTimeSlot> lastTimeSlots = null;
	private int lastChannel = 0;
	private int lastEndMinute = 0;
	
	public DummyListingSource()
	{
		
	}

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
		
		// This is for channel 4
		addShow(timeSlots, "Criminal Minds", 4, minute(0, 5), minute(1, 5));
		addShow("Leverage", 60);
		addShow("WCCO 4 News at 10", 35);
		addShow("WCCO 4 News at 10:30", 30);
		addShow("Paid Programming", 30);
		addShow("Up to the Minute", minute(4, 0) - lastEndMinute);
		addShow("CBS Morning News", 30);
		addShow("4 News This Morning at 4:30 AM", 30);
		addShow("4 News This Morning 5AM", 60);
		addShow("4 News This Morning 6AM", 60);
		addShow("CBS This Morning", 120);
		assert lastEndMinute == minute(9, 0);
		
		// Channel 5
		addShow(timeSlots, "Coach", 5, minute(0, 0), minute(0, 30));
		addShow("Da Vinci's Inquest", 60);
		addShow("My Family Recipe Rocks!", 30);
		addShow("Motion", 30);
		addShow("World News Now", 90);
		addShow("America This Morning", 30);
		addShow("5 Eyewitness News at 4:30AM", 30);
		addShow("5 Eyewitness News at 5:00AM", 30);
		addShow("5 Eyewitness News AM", 60);
		addShow("Good Morning America", 120);
		addShow("Live! With Kelly and Michael", 60);
		addShow("The View", 60);
		addShow("5 Eyewitness News Midday", 60);
		assert lastEndMinute == minute(12, 0);
		
		// Channel 6
		addShow(timeSlots, "MCN Presents", 6, 0, 30);
		addShow("MCN Presents", 30);
		addShow("Talk of the Twin Cities", 30);
		addShow("MCN Presents", 30);
		addShow("MCN Presents", 30);
		addShow("MCN Presents", 30);
		addShow("MCN Presents", 30);
		addShow("MCN Presents", 30);
		addShow("MCN Presents", 30);
		addShow("MCN Presents", 30);
		addShow("MCN Presents", 30);
		addShow("MCN Presents", 30);
		addShow("MCN Presents", 30);
		addShow("MCN Presents", 30);
		addShow("MCN Presents", 30);
		addShow("MCN Presents", 30);
		addShow("Talk of the Twin Cities", 30);
		addShow("Around Town", 30);
		addShow("MCN Presents", 30);
		addShow("It's a Woman's World", 30);
		addShow("St. Olaf's Mass", 90);
		addShow("Active Aging Presents Active Seniors", 30);
		assert lastEndMinute == minute(12, 0);
		
		// Channel 7
		addShow(timeSlots, "Evan Almighty", 7, minute(1, 0), minute(3, 0));
		addShow("Married...With Children", 30);
		addShow("Married...With Children", 30);
		addShow("Married...With Children", 30);
		addShow("Married...With Children", 30);
		addShow("Married...With Children", 30);
		addShow("Married...With Children", 30);
		addShow("My Name is Earl", 30);
		addShow("My Name is Earl", 30);
		addShow("The Fresh Prince of Bel-Air", 30);
		addShow("The Fresh Prince of Bel-Air", 30);
		addShow("Tyler Perry's House of Payne", 30);
		addShow("Tyler Perry's House of Payne", 30);
		addShow("The Fresh Prince of Bel-Air", 30);
		addShow("The Fresh Prince of Bel-Air", 30);
		addShow("Rules of Engagement", 30);
		addShow("Rules of Engagement", 30);
		addShow("According to Jim", 30);
		addShow("Everybody Loves Raymond", 30);
		assert lastEndMinute == minute(12, 0);
		
		// Channel 8
		addShow(timeSlots, "Minnesota Marketplace", 8, 0, 30);
		addShow("Minnesota Marketplace", 30);
		addShow("Ring of Honor Wrestling", 60);
		addShow("Hollyscoop", 30);
		addShow("Beautiful Homes", 30);
		addShow("Better", 60);
		addShow("Minnesota Marketplace", 30);
		addShow("Right Side With Armstrong Williams", 30);
		addShow("Minnesota Marketplace", 30);
		addShow("Out of Zion", 30);
		addShow("Joseph Prince", 30);
		addShow("Joyce Meyer Ministries: Enjoying Everyday Life", 30);
		addShow("Believer's Voice of Victory", 30);
		addShow("The Gospel Truth", 30);
		addShow("Life Today With James Robison", 30);
		addShow("Dragonfly TV", 30);
		addShow("The Steve Wilkos Show", 60);
		addShow("Jerry Springer", 60);
		addShow("Maury", 60);
		addShow("Judge Mathis", 60);
		assert lastEndMinute == minute(13, 0);
		
		// Channel 9
		addShow(timeSlots, "TMZ", 9, 5, 65);
		addShow("Paid Programming", 30);
		addShow("Paid Programming", 30);
		addShow("Fox ad 9", 60);
		addShow("Paid Programming", 25);
		addShow("TMZ", 30);
		addShow("Access Hollywood", 30);
		addShow("Fox 9 Morning News at 4:30 AM", 30);
		addShow("Fox 9 Morning News at 5:00 AM", 60);
		addShow("Fox 9 Morning News at 6:00 AM", 60);
		addShow("Fox 9 Morning News at 7:00 AM", 60);
		addShow("Fox 9 Morning News at 8:00 AM", 60);
		addShow("Fox News: Morning Buzz", 60);
		addShow("Anderson Live", 60);
		addShow("The Wendy Williams Show", 60);
		assert lastEndMinute == minute(12, 0);
		
		// Channel 10
		addShow(timeSlots, "Whacked Out Sports", 10, 0, 30);
		addShow("Whacked Out Sports", 30);
		addShow("Paid Programming", 30);
		addShow("Original Sprinkle Diet", 30);
		addShow("Original Sprinkle Diet", 30);
		addShow("Cops", 30);
		addShow("Paid Programming", 30);
		addShow("Cops", 30);
		addShow("Paid Programming", 30);
		addShow("Paid Programming", 30);
		addShow("Paid Programming", 30);
		addShow("Paid Programming", 30);
		addShow("Paid Programming", 30);
		addShow("Paid Programming", 30);
		addShow("10 Minute Workout", 30);
		addShow("The Wendy Williams Show", 60);
		addShow("The Ricki Lake Show", 60);
		addShow("The Trisha Goddard Show", 60);
		addShow("Divorce Court", 30);
		addShow("Divorce Court", 30);
		addShow("Judge Judy", 30);
		addShow("Judge Judy", 30);
		addShow("Judge Joe Brown", 30);
		addShow("Judge Joe Brown", 30);
		
		// Channel 11
		addShow(timeSlots, "Paid Programming", 11, 5, 65);
		addShow("Paid Programming", 30);
		addShow("KARE 11 News at 10", 30);
		addShow("Dateline NBC", 60);
		addShow("Meet the Press", 60);
		addShow("American Athlete", 25);
		addShow("Early Today", 30);
		addShow("KARE 11 News Sunrise", 30);
		addShow("KARE 11 News Sunrise", 60);
		addShow("KARE 11 News Sunrise", 60);
		addShow("Today", 180);
		addShow("Today", 60);
		addShow("KARE 11 News at 11", 30);
		addShow("Jeopardy!", 30);
		assert lastEndMinute == minute(12, 0);
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

	@Override
	public ShowInfo[] getShows(Date begin, Date end)
	{
		if (shows == null)
		{
			getTimeSlots(begin, end);
			
			HashSet<ShowInfo> theShows = new HashSet<ShowInfo>();
			for (int i = 0; i < timeSlots.length; ++i)
			{
				ShowTimeSlot ts = timeSlots[i];
				theShows.add(ts.getShowInfo());
			}
			
			shows = new ShowInfo[theShows.size()];
			theShows.toArray(shows);
		}
		
		return shows;
	}
}
