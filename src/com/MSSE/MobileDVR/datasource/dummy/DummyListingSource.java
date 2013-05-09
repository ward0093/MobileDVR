package com.MSSE.MobileDVR.datasource.dummy;

import java.util.*;

import com.MSSE.MobileDVR.TabMainActivity;
import com.MSSE.MobileDVR.datamodel.Channel;
import com.MSSE.MobileDVR.datamodel.ShowInfo;
import com.MSSE.MobileDVR.datamodel.ShowTimeSlot;
import com.MSSE.MobileDVR.datasource.ListingSource;

public class DummyListingSource implements ListingSource
{
	private HashMap<Channel,ArrayList<ShowTimeSlot>> channelMap = new HashMap<Channel,ArrayList<ShowTimeSlot>>();
	private Channel currentChannel = null;
	private int currentMinute = 0;
	private int maxEndMinute = 0;
	private Calendar dawnOfMan = null;
    private int recordedShowOffset = 0;
	
	public DummyListingSource()
	{
        dawnOfMan = Calendar.getInstance();
        dawnOfMan.set(Calendar.YEAR, 2013);
        dawnOfMan.set(Calendar.MONTH, 5);
        dawnOfMan.set(Calendar.DAY_OF_MONTH, 1);
        dawnOfMan.set(Calendar.HOUR_OF_DAY, 0);
        dawnOfMan.set(Calendar.MINUTE, 0);
        dawnOfMan.set(Calendar.SECOND, 0);
        dawnOfMan.set(Calendar.MILLISECOND, 0);
        if (TabMainActivity.getShowTimeSlotDB().isEmpty()) {
            // This is 24 hours for channel 2
            //
            currentChannel = newChannel(2, "PBS");
            currentMinute = 0;
            addShow("Austin City Limits", 60, "http://www.youtube.com/watch?v=PxNnEEK6uG0");
            addShow("Antiques Roadshow", 60, "http://www.youtube.com/watch?v=hyUpT0g2KAE");
            addShow("Suspicion (1941), NR", 120, "http://www.youtube.com/watch?v=3YleRTgzQKY");
            addShow("Masterpiece Mystery!", 90, "http://www.youtube.com/watch?v=wwHlKwT6uQc");
            addShow("The Red Green Show", 30, "http://www.youtube.com/watch?v=G7nRRykiOGc");
            addShow("Sesame Street", 60, "http://www.youtube.com/watch?v=shbgRyColvE");
            addShow("Curious George Swings/Spring", 60, "http://www.youtube.com/watch?v=WYnyLDKhz8w");
            addShow("Super Why!", 30, "http://www.youtube.com/watch?v=FDtDPIR9PQ0");
            addShow("Dinosaur Train", 30, "http://www.youtube.com/watch?v=A6i3Z1tmfMA");
            addShow("Washington Week w/Glen Ifill", 30, "http://www.youtube.com/watch?v=8umyJ7FQaBY");
            addShow("Almanac", 60, "http://www.youtube.com/watch?v=FxF7CcKhDcQ");
            addShow("The McLuaghlin Group", 30, "http://www.youtube.com/watch?v=STHTy3LIyEM");
            addShow("To the Contrary/Bonnie Erbe", 30, "http://www.youtube.com/watch?v=u_p_qPVbfV8");
            addShow("Religion & Ethics News Weekly", 30, "http://www.youtube.com/watch?v=82JMEIBKTTk");
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
            assert currentMinute == minute(24, 0);

            // This is 24 hours for channel 3
            currentChannel = newChannel(3, "CNN");
            currentMinute = minute(1, 0);
            addShow("Anderson Cooper Special Report", 60, "http://www.youtube.com/watch?v=9OKgUdQF-Fg");
            addShow("WH Correspondents' Dinner", 120, "http://www.youtube.com/watch?v=qppLxlZ5kOs");
            addShow("Anderson Cooper Special Report", 60, "http://www.youtube.com/watch?v=9OKgUdQF-Fg");
            addShow("Weekend Early Start", 90, "http://www.youtube.com/watch?v=pp03f4OX7KI");
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
            assert currentMinute == minute(24, 0);

            // This is for channel 4
            currentChannel = newChannel(4, "CBS");
            currentMinute = 0;
            addShow("Criminal Minds", 60, "http://www.youtube.com/watch?v=CuZGW5-93tM");
            addShow("Leverage", 60, "http://www.youtube.com/watch?v=_nwJr8pxU14");
            addShow("WCCO 4 News at 10", 30, "http://www.youtube.com/watch?v=xRg7h53mqXc");
            addShow("WCCO 4 News at 10:30", 30, "http://www.youtube.com/watch?v=xRg7h53mqXc");
            addShow("Paid Programming", 30, null);
            addShow("Up to the Minute", minute(4, 0) - currentMinute, null);
            addShow("CBS Morning News", 30);
            addShow("4 News This Morning at 4:30 AM", 30);
            addShow("4 News This Morning 5AM", 60);
            addShow("4 News This Morning 6AM", 60);
            addShow("CBS This Morning", 120);
            assert currentMinute == minute(9, 0);

            // Channel 5
            currentChannel = newChannel(5, "ABC");
            currentMinute = 0;
            addShow("Coach", 30, "http://www.youtube.com/watch?v=LomCpLcIyTw");
            addShow("Da Vinci's Inquest", 60, "http://www.youtube.com/watch?v=MjsUwvHIRXo");
            addShow("My Family Recipe Rocks!", 30, "http://www.youtube.com/watch?v=oTrZgNra8OY");
            addShow("Motion", 30);
            addShow("World News Now", 90, "http://www.youtube.com/watch?v=-2pwhAEittU");
            addShow("America This Morning", 30, "http://www.youtube.com/watch?v=y9-2NvtTwEI");
            addShow("5 Eyewitness News at 4:30AM", 30);
            addShow("5 Eyewitness News at 5:00AM", 30);
            addShow("5 Eyewitness News AM", 60);
            addShow("Good Morning America", 120);
            addShow("Live! With Kelly and Michael", 60);
            addShow("The View", 60);
            addShow("5 Eyewitness News Midday", 60);
            assert currentMinute == minute(12, 0);

            // Channel 6
            currentChannel = newChannel(6, "MCN");
            currentMinute = 0;
            addShow("MCN Presents", 30, "http://www.youtube.com/watch?v=yR9puu3vXBA");
            addShow("MCN Presents", 30, "http://www.youtube.com/watch?v=yR9puu3vXBA");
            addShow("Talk of the Twin Cities", 30, "http://www.youtube.com/watch?v=CLM9WJhjOws");
            addShow("MCN Presents", 30, "http://www.youtube.com/watch?v=yR9puu3vXBA");
            addShow("MCN Presents", 30, "http://www.youtube.com/watch?v=yR9puu3vXBA");
            addShow("MCN Presents", 30, "http://www.youtube.com/watch?v=yR9puu3vXBA");
            addShow("MCN Presents", 30, "http://www.youtube.com/watch?v=yR9puu3vXBA");
            addShow("MCN Presents", 30, "http://www.youtube.com/watch?v=yR9puu3vXBA");
            addShow("MCN Presents", 30, "http://www.youtube.com/watch?v=yR9puu3vXBA");
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
            assert currentMinute == minute(12, 0);

            // Channel 7
            currentChannel = newChannel(7, "TBS");
            currentMinute = minute(1, 0);
            addShow("Evan Almighty", 120, "http://www.youtube.com/watch?v=3E-C9PipmaA");
            addShow("Married...With Children", 30, "http://www.youtube.com/watch?v=3E-C9PipmaA");
            addShow("Married...With Children", 30, "http://www.youtube.com/watch?v=3E-C9PipmaA");
            addShow("Married...With Children", 30, "http://www.youtube.com/watch?v=3E-C9PipmaA");
            addShow("Married...With Children", 30, "http://www.youtube.com/watch?v=3E-C9PipmaA");
            addShow("Married...With Children", 30, "http://www.youtube.com/watch?v=3E-C9PipmaA");
            addShow("Married...With Children", 30, "http://www.youtube.com/watch?v=3E-C9PipmaA");
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
            assert currentMinute == minute(12, 0);

            // Channel 8
            currentChannel = newChannel(8, "CW");
            currentMinute = 0;
            addShow("Minnesota Marketplace", 30);
            addShow("Minnesota Marketplace", 30);
            addShow("Ring of Honor Wrestling", 60, "http://www.youtube.com/watch?v=HoS8HdSRQGk&");
            addShow("Hollyscoop", 30, "http://www.youtube.com/watch?v=UUcBRiXkSg0");
            addShow("Beautiful Homes", 30, "http://www.youtube.com/watch?v=gXV8nbYhYn0");
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
            assert currentMinute == minute(13, 0);

            // Channel 9
            currentChannel = newChannel(9, "FOX");
            currentMinute = 0;
            addShow("TMZ", 60, "http://www.youtube.com/watch?v=9KlQewWpi8s");
            addShow("Paid Programming", 30);
            addShow("Paid Programming", 30);
            addShow("Fox ad 9", 60);
            addShow("Paid Programming", 30);
            addShow("TMZ", 30, "http://www.youtube.com/watch?v=9KlQewWpi8s");
            addShow("Access Hollywood", 30);
            addShow("Fox 9 Morning News at 4:30 AM", 30);
            addShow("Fox 9 Morning News at 5:00 AM", 60);
            addShow("Fox 9 Morning News at 6:00 AM", 60);
            addShow("Fox 9 Morning News at 7:00 AM", 60);
            addShow("Fox 9 Morning News at 8:00 AM", 60);
            addShow("Fox News: Morning Buzz", 60);
            addShow("Anderson Live", 60);
            addShow("The Wendy Williams Show", 60);
            assert currentMinute == minute(12, 0);

            // Channel 10
            currentChannel = newChannel(10, "myTV");
            currentMinute = 0;
            addShow("Whacked Out Sports", 30, "http://www.youtube.com/watch?v=2YH1tx1mZRE");
            addShow("Whacked Out Sports", 30, "http://www.youtube.com/watch?v=2YH1tx1mZRE");
            addShow("Paid Programming", 30);
            addShow("Original Sprinkle Diet", 30);
            addShow("Original Sprinkle Diet", 30);
            addShow("Cops", 30, "http://www.youtube.com/watch?v=6cjNUrlj9Ok");
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
            currentChannel = newChannel(11, "NBC");
            currentMinute = 5;
            addShow("Paid Programming", 60);
            addShow("Paid Programming", 30);
            addShow("KARE 11 News at 10", 30, "http://www.youtube.com/watch?v=PAdcSs_ILVA");
            addShow("Dateline NBC", 60, "http://www.youtube.com/watch?v=aGptggF2mf8");
            addShow("Meet the Press", 60, "http://www.youtube.com/watch?v=gs43RR7IiNU");
            addShow("American Athlete", 25);
            addShow("Early Today", 30);
            addShow("KARE 11 News Sunrise", 30);
            addShow("KARE 11 News Sunrise", 60);
            addShow("KARE 11 News Sunrise", 60);
            addShow("Today", 180);
            addShow("Today", 60);
            addShow("KARE 11 News at 11", 30);
            addShow("Jeopardy!", 30);
            assert currentMinute == minute(12, 0);
        }
	}

	@Override
	public Channel[] getChannels()
	{
        List<Channel> channelList = TabMainActivity.getChannelDB().getChannelList();
        Channel[] result = new Channel[channelList.size()];
		channelList.toArray(result);
//		Set<Channel> channels = channelMap.keySet();
//		Channel[] result = new Channel[channels.size()];
//		channels.toArray(result);
//		Arrays.sort(result, new Comparator<Channel>() {
//
//			@Override
//			public int compare(Channel lhs, Channel rhs)
//			{
//				int result = lhs.getNumber() - rhs.getNumber();
//				return result;
//			}
//
//		});
		return result;
	}

	@Override
	public Channel lookupChannel(int number)
	{
        return TabMainActivity.getChannelDB().getChannelByNumber(number);
//		for (Channel channel : channelMap.keySet())
//		{
//			if (channel.getNumber() == number)
//				return channel;
//		}
//
//		return null;
	}

	@Override
	public Channel lookupChannel(String name)
	{
        return TabMainActivity.getChannelDB().getChannelByName(name);
//		for (Channel channel : channelMap.keySet())
//		{
//			if (channel.getName().equals(name))
//				return channel;
//		}
//
//		return null;
	}
	
	@Override
	public Date getEarliest()
	{
		return dawnOfMan.getTime();
	}
	
	@Override
	public Date getLatest()
	{
//		Calendar theLatest = (Calendar)dawnOfMan.clone();
//		theLatest.add(Calendar.MINUTE, maxEndMinute);
//		Date result = theLatest.getTime();
        Date result = TabMainActivity.getShowTimeSlotDB().getLatestTime();
        if (result == null)
            result = getEarliest();
		return result;
	}
	
	@Override
	public ShowInfo[] getShows()
	{
        List<ShowInfo> shows = TabMainActivity.getShowInfoDB().getShowInfoList();
//		HashSet<ShowInfo> shows = new HashSet<ShowInfo>();
//		for (HashMap.Entry<Channel,ArrayList<ShowTimeSlot>> entry : channelMap.entrySet())
//		{
//			for (ShowTimeSlot timeSlot : entry.getValue())
//			{
//				shows.add(timeSlot.getShowInfo());
//			}
//		}
//
		ShowInfo[] result = new ShowInfo[shows.size()];
		shows.toArray(result);
//		Arrays.sort(result, new Comparator<ShowInfo>() {
//
//			@Override
//			public int compare(ShowInfo lhs, ShowInfo rhs)
//			{
//				int result = lhs.getTitle().compareTo(rhs.getTitle());
//				return result;
//			}
//
//		});
		return result;
	}
	
	@Override
	public ShowTimeSlot[] getTimeSlotsForShow(ShowInfo theShow)
	{
        return TabMainActivity.getShowTimeSlotDB().getTimeSlotsForShow(theShow);
//		ArrayList<ShowTimeSlot> timeSlots = new ArrayList<ShowTimeSlot>();
//
//		for (HashMap.Entry<Channel,ArrayList<ShowTimeSlot>> entry : channelMap.entrySet())
//		{
//			for (ShowTimeSlot timeSlot : entry.getValue())
//			{
//				ShowInfo aShow = timeSlot.getShowInfo();
//				if (aShow.equals(theShow))
//					timeSlots.add(timeSlot);
//			}
//		}
//
//		ShowTimeSlot[] result = new ShowTimeSlot[timeSlots.size()];
//		timeSlots.toArray(result);
//		Arrays.sort(result, new Comparator<ShowTimeSlot>() {
//
//			@Override
//			public int compare(ShowTimeSlot lhs, ShowTimeSlot rhs)
//			{
//				long l = lhs.getStartTime().getTime();
//				long r = rhs.getStartTime().getTime();
//				return l < r ? -1 : (l > r ? 1 : 0);
//			}
//
//		});
//		return result;
	}
	
	@Override
	public ShowTimeSlot lookupTimeSlot(Channel channel, Date time)
	{
		ShowTimeSlot result = null;
		result = TabMainActivity.getShowTimeSlotDB().getShowTimeSlot(channel, time);
//		ArrayList<ShowTimeSlot> timeSlots = channelMap.get(channel);
//		for (ShowTimeSlot timeSlot : timeSlots)
//		{
//			long start = timeSlot.getStartTime().getTime();
//			long ms = time.getTime();
//			long end = timeSlot.getEndTime().getTime();
//			if (start <= ms && ms < end)
//			{
//				result = timeSlot;
//				break;
//			}
//		}
//
		return result;
	}

    private void addShow(String title, int durationMinutes, String previewUrl)
	{
		//ShowInfo showInfo = new ShowInfo(title, "No description for " + title);
        ShowInfo showInfo = TabMainActivity.getShowInfoDB().createShowInfo(title, "No description for " + title, null);
		Calendar start = makeCalendar(currentMinute);
        //ShowTimeSlot timeSlot = new ShowTimeSlot(showInfo, currentChannel, start.getTime(), durationMinutes);
		ShowTimeSlot timeSlot = TabMainActivity.getShowTimeSlotDB().createShowTimeSlot(currentChannel, showInfo, start.getTime(), durationMinutes, previewUrl);
		ArrayList<ShowTimeSlot> timeSlots = channelMap.get(currentChannel);
		timeSlots.add(timeSlot);
		
		currentMinute += durationMinutes;
		if (maxEndMinute < currentMinute)
			maxEndMinute = currentMinute;
        if ((recordedShowOffset % 17) == 0) {
            addRecording(timeSlot);
        }
        recordedShowOffset++;
	}

    private void addShow(String title, int durationMinutes)
    {
        addShow(title, durationMinutes, null);
    }

    private void addRecording(ShowTimeSlot showTimeSlot)
    {
        Date keepUntil = showTimeSlot.getStartTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(showTimeSlot.getStartTime());
        calendar.add(Calendar.DATE, 10);
        TabMainActivity.getRecordedShowDB().createRecordedShow(showTimeSlot.getShowInfo(), showTimeSlot, calendar.getTime());
    }

	private Calendar makeCalendar(int minuteOfDay)
	{
		Calendar result = (Calendar)dawnOfMan.clone();
		
		int hour = minuteOfDay / 60;
		int minute = minuteOfDay - hour * 60;
		result.set(Calendar.HOUR_OF_DAY, hour);
		result.set(Calendar.MINUTE, minute);
		
		return result;
	}
	
	private static int minute(int hour, int minute)
	{
		return hour*60 + minute;
	}
	
	private Channel newChannel(int number, String name)
	{
		//Channel result = new Channel(number, name);
        Channel result = TabMainActivity.getChannelDB().createChannel(number, name);
		
		ArrayList<ShowTimeSlot> timeSlots = new ArrayList<ShowTimeSlot>();
		channelMap.put(result, timeSlots);
		
		return result;
	}
}
