package com.MSSE.MobileDVR.datamodel;

import java.util.Calendar;
import java.util.Date;


public class ScheduledRecording
{
	private boolean recurring;
	private int minutesBefore;
	private int minutesAfter;
	private int showsToKeep;
	private Date keepUntil;
	private ShowInfo showInfo;
	private ShowTimeSlot originalAirtime;
    private long id;

    public ScheduledRecording() {

    }

    public ScheduledRecording(long id, ShowInfo showInfo, ShowTimeSlot originalAirtime, Date keepUntil, boolean recurring,
                              int minutesBefore, int minutesAfter, int showsToKeep) {
        this.id = id;
        this.showInfo = showInfo;
        this.originalAirtime = originalAirtime;
        this.keepUntil = keepUntil;
        this.recurring = recurring;
        this.minutesBefore = minutesBefore;
        this.minutesAfter = minutesAfter;
        this.showsToKeep = showsToKeep;
    }

    public long getId() {
        return this.id;
    }

    public int getMinutesBefore() {
        return minutesBefore;
    }

    public void setMinutesBefore(int minutesBefore) {
        this.minutesBefore = minutesBefore;
    }

    public int getMinutesAfter() {
        return minutesAfter;
    }

    public void setMinutesAfter(int minutesAfter) {
        this.minutesAfter = minutesAfter;
    }

    public int getShowsToKeep() {
        return showsToKeep;
    }

    public void setShowsToKeep(int showsToKeep) {
        this.showsToKeep = showsToKeep;
    }

    public Date getKeepUntil() {
        return keepUntil;
    }

    public void setKeepUntil(Date keepUntil) {
        this.keepUntil = keepUntil;
    }

    /*
     * setKeepUntil - Set the keepUntil date to a set number of days in the future
     */
    public void setKeepUntil(Date airDate, int daysToKeep) {
        if (daysToKeep > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(airDate);
            calendar.add(Calendar.DATE, daysToKeep);
            this.keepUntil = calendar.getTime();
        } else {
            /* Keep forever */
            this.keepUntil = null;
        }
    }

    public ShowInfo getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(ShowInfo showInfo) {
        this.showInfo = showInfo;
    }

    public ShowTimeSlot getOriginalAirtime() {
        return originalAirtime;
    }

    public void setOriginalAirtime(ShowTimeSlot originalAirtime) {
        this.originalAirtime = originalAirtime;
    }

    public boolean getRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }
}
