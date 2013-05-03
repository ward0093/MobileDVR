package com.MSSE.MobileDVR;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 5/1/13
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ScheduledRecordingSource {
    public LinkedList<ScheduledRecording> getAllScheduled();

    public ScheduledRecording getScheduledRec(int id);

    public void add(ScheduledRecording scheduledRec);

    public void remove(int id);

    public void remove(ScheduledRecording scheduledRec);

    public int getNextId();

    public ScheduledRecording newScheduledRecording();
}
