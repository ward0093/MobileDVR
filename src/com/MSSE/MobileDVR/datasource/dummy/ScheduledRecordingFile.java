package com.MSSE.MobileDVR.datasource.dummy;

import java.util.HashMap;
import java.util.LinkedList;

import com.MSSE.MobileDVR.datamodel.ScheduledRecording;
import com.MSSE.MobileDVR.datasource.ScheduledRecordingSource;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 5/1/13
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduledRecordingFile implements ScheduledRecordingSource {
    private HashMap<Integer, ScheduledRecording> scheduled = new HashMap<Integer, ScheduledRecording>();
    private int maxId;

    public ScheduledRecordingFile() {
        maxId = 0;
    }

    @Override
    public ScheduledRecording newScheduledRecording() {
        int id = maxId;
        maxId++;
        ScheduledRecording newRec = new ScheduledRecording(id);
        scheduled.put(newRec.getId(), newRec);
        return newRec;
    }

    @Override
    public LinkedList<ScheduledRecording> getAllScheduled() {
        LinkedList<ScheduledRecording> scheduledList = new LinkedList<ScheduledRecording>();
        for (ScheduledRecording rec : scheduled.values()) {
            scheduledList.add(rec);
        }
        return scheduledList;
    }

    @Override
    public ScheduledRecording getScheduledRec(int id) {
        return scheduled.get(id);
    }

    @Override
    public void add(ScheduledRecording scheduledRec) {
        scheduled.put(scheduledRec.getId(), scheduledRec);
    }

    @Override
    public void remove(int id) {
        if (scheduled.containsKey(id)) {
            scheduled.remove(id);
        }
    }

    @Override
    public void remove(ScheduledRecording scheduledRec) {
        if (scheduled.containsValue(scheduledRec)) {
            scheduled.remove(scheduledRec.getId());
        }
    }

    public int getNextId() {
        int result = maxId;
        maxId++;
        return result;
    }
}
