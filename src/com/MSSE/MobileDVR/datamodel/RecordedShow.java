package com.MSSE.MobileDVR.datamodel;

import java.util.Date;

public class RecordedShow
{

	private Date keepUntil;
	private ShowInfo showInfo;
	private ShowTimeSlot originalAirtime;
    private long id;

    public RecordedShow (long id, ShowInfo showInfo, ShowTimeSlot showTimeSlot, Date keepUntil) {
        this.id = id;
        this.showInfo = showInfo;
        this.originalAirtime = showTimeSlot;
        this.keepUntil = keepUntil;
    }

    public Date getKeepUntil() {
        return keepUntil;
    }

    public ShowInfo getShowInfo() {
        return showInfo;
    }

    public ShowTimeSlot getOriginalAirtime() {
        return originalAirtime;
    }

    public long getId() {
        return id;
    }
    // some handle to the audio/video data

}
