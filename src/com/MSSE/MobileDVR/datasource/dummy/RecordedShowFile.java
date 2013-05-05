package com.MSSE.MobileDVR.datasource.dummy;

import java.util.HashMap;
import java.util.LinkedList;

import com.MSSE.MobileDVR.datamodel.RecordedShow;
import com.MSSE.MobileDVR.datasource.RecordedShowSource;

/**
 * Created with IntelliJ IDEA.
 * User: dward
 * Date: 5/4/13
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class RecordedShowFile implements RecordedShowSource {

    public RecordedShowFile() {

    }

    @Override
    public RecordedShow[] getRecordedShows() {
        return new RecordedShow[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
