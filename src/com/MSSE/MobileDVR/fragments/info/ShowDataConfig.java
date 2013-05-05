package com.MSSE.MobileDVR.fragments.info;

import android.app.Activity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.MSSE.MobileDVR.R;
import com.MSSE.MobileDVR.R.id;
import com.MSSE.MobileDVR.datamodel.ShowTimeSlot;

/**
 * Created with IntelliJ IDEA.
 * User: Darin
 * Date: 4/30/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowDataConfig {
    private static final String SHOW_TIME_FORMAT = "h:mm a";
    private static final String SHOW_DATE_FORMAT = "EEE M/d";
    private Activity _activity;

    public ShowDataConfig(Activity activity) {
        _activity = activity;
        if (!hasShowData()) {
            throw new RuntimeException("Trying to add show data to a layout that doesn't have one!");
        }
    }

    public boolean hasShowData() {
        return _activity.findViewById(R.id.show_data) != null;
    }

    public TextView getShowNameTextView() {
        return (TextView)_activity.findViewById(R.id.showInfoShowNameData);
    }

    public void setShowName(String name) {
        TextView showNameData = getShowNameTextView();
        showNameData.setText(name);
    }

    public TextView getChannelTextView() {
        return (TextView)_activity.findViewById(R.id.showInfoChannelData);
    }

    public void setChannel(Integer channel) {
        TextView showChannelData = getChannelTextView();
        showChannelData.setText(channel.toString());
    }

    public TextView getTimeTextView() {
        return (TextView)_activity.findViewById(R.id.showInfoTimeData);
    }

    public void setShowTime(Date showDate) {
        TextView showTimeData = getTimeTextView();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(SHOW_TIME_FORMAT);
        showTimeData.setText(dateFormatter.format(showDate));
    }

    public TextView getDateTextView() {
        return (TextView)_activity.findViewById(R.id.showInfoDateData);
    }

    public void setShowDate(Date showDate) {
        TextView showDateData = getDateTextView();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(SHOW_DATE_FORMAT);
        showDateData.setText(dateFormatter.format(showDate));
    }

    public void setAllShowData(ShowTimeSlot showTimeSlot) {
        setShowName(showTimeSlot.getShowInfo().getTitle());
        setChannel(showTimeSlot.getChannel().getNumber());
        setShowTime(showTimeSlot.getStartTime());
        setShowDate(showTimeSlot.getStartTime());
    }
}