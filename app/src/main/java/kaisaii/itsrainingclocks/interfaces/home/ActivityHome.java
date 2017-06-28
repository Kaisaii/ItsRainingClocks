package kaisaii.itsrainingclocks.interfaces.home;

import android.os.Bundle;
import com.joxad.easydatabinding.activity.ActivityBase;
import kaisaii.itsrainingclocks.BR;
import kaisaii.itsrainingclocks.R;
import kaisaii.itsrainingclocks.databinding.ActivityHomeBinding;

public class ActivityHome extends ActivityBase<ActivityHomeBinding, ActivityHomeVM> {
    @Override
    public int data() {
        return BR.activityHomeVM;
    }

    @Override
    public int layoutResources() {
        return R.layout.activity_home;
    }

    @Override
    public ActivityHomeVM baseActivityVM(ActivityHomeBinding binding, Bundle savedInstanceState) {
        return new ActivityHomeVM(this, binding, savedInstanceState);
    }
}
