package kaisaii.itsrainingclocks.interfaces.home;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.joxad.easydatabinding.activity.ActivityBaseVM;

import kaisaii.itsrainingclocks.R;
import kaisaii.itsrainingclocks.databinding.ActivityHomeBinding;
import kaisaii.itsrainingclocks.interfaces.clock.ClockView;
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
        final ClockView pmWeather = binding.pmWeather;
        pmWeather.addItem(1, R.color.sun);
        pmWeather.addItem(1, R.color.sun_cloudy);
        pmWeather.addItem(1, R.color.cloud_soft);
        pmWeather.addItem(1, R.color.cloud_medium);
        pmWeather.addItem(1, R.color.cloud_strong);
        pmWeather.addItem(1, R.color.rain_soft);

        pmWeather.addItem(1, R.color.rain_medium);
        pmWeather.addItem(1, R.color.rain_strong);
        pmWeather.addItem(1, R.color.rain_strong_active);
        pmWeather.addItem(1, R.color.snow);
        pmWeather.addItem(1, R.color.hail);
        pmWeather.addItem(1, R.color.storm);

        final ClockView background = binding.amWeatherBackground;
        background.addItem(12, R.color.white);

        final ClockView amWeather = binding.amWeather;
        amWeather.addItem(1, R.color.storm);
        amWeather.addItem(1, R.color.hail);
        amWeather.addItem(1, R.color.snow);
        amWeather.addItem(1, R.color.rain_strong_active);
        amWeather.addItem(1, R.color.rain_strong);
        amWeather.addItem(1, R.color.rain_medium);
        amWeather.addItem(1, R.color.rain_soft);
        amWeather.addItem(1, R.color.cloud_strong);
        amWeather.addItem(1, R.color.cloud_medium);
        amWeather.addItem(1, R.color.cloud_soft);
        amWeather.addItem(1, R.color.sun_cloudy);
        amWeather.addItem(1, R.color.sun);
    }

    private void displayMinutes() {
        binding.minutes.invalidate();
    }
}
