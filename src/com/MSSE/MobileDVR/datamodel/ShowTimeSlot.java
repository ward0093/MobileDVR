package com.MSSE.MobileDVR.datamodel;

import java.util.Calendar;
import java.util.Date;

public class ShowTimeSlot
{
	public static final int MAX_DURATION_MINUTES = 60 * 24;
	private Channel channel;
	private Date startTime;
	private ShowInfo showInfo;
	private int durationMinutes;
    private String previewUrl;
	
	public ShowTimeSlot(ShowInfo showInfo, Channel channel, Date startTime, int durationMinutes, String previewUrl)
	{
		if (showInfo == null)
			throw new IllegalArgumentException("showInfo");
		if (channel == null)
			throw new IllegalArgumentException("channel");
		if (startTime == null)
			throw new IllegalArgumentException("startTime");
		if (durationMinutes <= 0 || durationMinutes > MAX_DURATION_MINUTES)
			throw new IllegalArgumentException("durationMinutes");
		
		this.showInfo = showInfo;
		this.channel = channel;
		this.startTime = startTime;
		this.durationMinutes = durationMinutes;
        this.previewUrl = previewUrl;
	}
	
	public ShowInfo getShowInfo()
	{
		return showInfo;
	}
	
	public Channel getChannel()
	{
		return channel;
	}
	
	public Date getStartTime()
	{
		return startTime;
	}
	
	public Date getEndTime()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.add(Calendar.MINUTE, durationMinutes);
		Date result = cal.getTime();
		return result;
	}
	
	public int getDurationMinutes()
	{
		return durationMinutes;
	}

    public String getPreviewUrl()
    {
        return previewUrl;
    }
	
	private static int minutes(Date startTime, Date endTime)
	{
		long msec = endTime.getTime() - startTime.getTime();
		long lresult = (msec + 59999) / 60000; // 60,000 msec per minute
		int result = (int)lresult;
		if (result != lresult)
			throw new IllegalArgumentException("duration too long");
		return result;
	}
}
