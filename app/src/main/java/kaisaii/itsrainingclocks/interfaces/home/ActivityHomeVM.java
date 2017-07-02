package kaisaii.itsrainingclocks.interfaces.home;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.joxad.easydatabinding.activity.ActivityBaseVM;

import java.util.Calendar;

import kaisaii.itsrainingclocks.R;
import kaisaii.itsrainingclocks.databinding.ActivityHomeBinding;
import kaisaii.itsrainingclocks.interfaces.clock.ClockView;
import kaisaii.itsrainingclocks.interfaces.clock.SegmentTypeEnum;
import kaisaii.itsrainingclocks.listener.TimeChangeReceiver;

/**
 * Created by salon on 26/06/17.
 */

public class ActivityHomeVM extends ActivityBaseVM<ActivityHome, ActivityHomeBinding> {
    private BroadcastReceiver timeChangeReceiver;
    private int currentHour = 0; // from 0 to 23

    /***
     * @param activity
     * @param binding
     * @param savedInstance
     */
    public ActivityHomeVM(ActivityHome activity, ActivityHomeBinding binding, @Nullable Bundle savedInstance) {
        super(activity, binding, savedInstance);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstance) {
        registerTimeChange();
        updateView();
    }

    @Override
    public void onDestroy() {
        activity.unregisterReceiver(timeChangeReceiver);
    }

    /**
     * Register to time change to udpate views
     */
    private void registerTimeChange() {
        IntentFilter timeIntent = new IntentFilter();
        timeIntent.addAction(Intent.ACTION_TIME_TICK);
        timeIntent.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        timeIntent.addAction(Intent.ACTION_TIME_CHANGED);

        timeChangeReceiver = new TimeChangeReceiver(new Runnable() {
            @Override
            public void run() {
                updateView();
            }
        });

        activity.registerReceiver(timeChangeReceiver, timeIntent);
    }

    private void updateView() {
        Calendar rightNow = Calendar.getInstance();
        int minutes = rightNow.get(Calendar.MINUTE);
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);

        if (hour != currentHour) {
            displayTemp(hour);
            currentHour = hour;
        }
        displayMinutes(minutes);
    }

    /**
     * Highlight current Segment
     * @param hour
     */
    private void displayTemp(int hour) {
        final ClockView clock = binding.clock;
        clock.cleanSegments();

        boolean isAM = hour < 12;
        int index = 0;

        index = clock.addItem(SegmentTypeEnum.AM, 7, R.color.rain_medium, R.color.rain_medium_active, isAM && hour>=index && hour<index+7);
        index = clock.addItem(SegmentTypeEnum.AM, 2, R.color.sun_cloudy, R.color.sun_cloudy_active, isAM && hour>=index && hour<index+2);
        index = clock.addItem(SegmentTypeEnum.AM, 3, R.color.sun, R.color.sun_active, isAM && hour>=index && hour<index+3);

        index = 12;
        index = 12+clock.addItem(SegmentTypeEnum.PM, 3, R.color.sun, R.color.sun_active,!isAM && hour>=index && hour<index+3);
        index = 12+clock.addItem(SegmentTypeEnum.PM, 5, R.color.sun_cloudy, R.color.sun_cloudy_active, !isAM && hour>=index && hour<index+3);
        index = 12+clock.addItem(SegmentTypeEnum.PM, 2, R.color.cloud_medium, R.color.cloud_medium_active, !isAM && hour>=index && hour<index+3);
        index = 12+clock.addItem(SegmentTypeEnum.PM, 2, R.color.rain_medium, R.color.rain_medium_active, !isAM && hour>=index && hour<index+3);
    }

    /**
     * Update minute circle angle
     * @param minutes
     */
    private void displayMinutes(int minutes) {
        binding.minutes.updateMinutes(minutes);
    }
}
