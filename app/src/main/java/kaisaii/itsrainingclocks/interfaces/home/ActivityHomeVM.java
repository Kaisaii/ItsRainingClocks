package kaisaii.itsrainingclocks.interfaces.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.joxad.easydatabinding.activity.ActivityBaseVM;

import kaisaii.itsrainingclocks.R;
import kaisaii.itsrainingclocks.databinding.ActivityHomeBinding;
import kaisaii.itsrainingclocks.interfaces.ClockView;

/**
 * Created by salon on 26/06/17.
 */

public class ActivityHomeVM extends ActivityBaseVM<ActivityHome, ActivityHomeBinding> {
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
        Resources res = activity.getResources();

        final ClockView pie = binding.pie;
        pie.addItem(1, res.getColor(R.color.sun));
        pie.addItem(1, res.getColor(R.color.sun_cloudy));
        pie.addItem(1, res.getColor(R.color.cloud_soft));
        pie.addItem(1, res.getColor(R.color.cloud_medium));
        pie.addItem(1, res.getColor(R.color.cloud_strong));
        pie.addItem(1, res.getColor(R.color.rain_soft));

        pie.addItem(1, res.getColor(R.color.rain_medium));
        pie.addItem(1, res.getColor(R.color.rain_strong));
        pie.addItem(1, res.getColor(R.color.rain_strong_active));
        pie.addItem(1, res.getColor(R.color.snow));
        pie.addItem(1, res.getColor(R.color.hail));
        pie.addItem(1, res.getColor(R.color.storm));

    }
}
