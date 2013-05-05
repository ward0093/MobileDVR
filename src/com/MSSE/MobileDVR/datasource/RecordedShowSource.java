package com.MSSE.MobileDVR.datasource;

import com.MSSE.MobileDVR.datamodel.RecordedShow;

/**
 * Created with IntelliJ IDEA.
 * User: dward
 * Date: 5/4/13
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public interface RecordedShowSource {

    public RecordedShow[] getRecordedShows();
}
