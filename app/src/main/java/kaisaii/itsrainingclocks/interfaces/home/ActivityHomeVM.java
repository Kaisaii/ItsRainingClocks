package kaisaii.itsrainingclocks.interfaces.home;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.joxad.easydatabinding.activity.ActivityBaseVM;

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
        buildClock();

        registerTimeChange();
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
                displayMinutes();
            }
        });
        activity.registerReceiver(timeChangeReceiver, timeIntent);
    }


    /**
     * Build all views
     */
    private void buildClock() {
        final ClockView clock = binding.clock;
        clock.addItem(SegmentTypeEnum.PM, 3, R.color.sun);
        clock.addItem(SegmentTypeEnum.PM, 5, R.color.sun_cloudy);
        clock.addItem(SegmentTypeEnum.PM, 2, R.color.cloud_medium);
        clock.addItem(SegmentTypeEnum.PM, 2, R.color.rain_medium);

        clock.addItem(SegmentTypeEnum.AM, 7, R.color.rain_medium);
        clock.addItem(SegmentTypeEnum.AM, 2, R.color.sun_cloudy);
        clock.addItem(SegmentTypeEnum.AM, 3, R.color.sun);
    }

    private void displayMinutes() {
        binding.minutes.invalidate();
    }
}
