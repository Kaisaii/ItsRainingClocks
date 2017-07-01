package kaisaii.itsrainingclocks.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * Created by salon on 29/06/17.
 */

public class TimeChangeReceiver extends BroadcastReceiver {
    private Runnable onTick;

    public TimeChangeReceiver() {
    }

    public TimeChangeReceiver(Runnable onTick) {
        this.onTick = onTick;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (action.equals(Intent.ACTION_TIME_TICK) ||
                action.equals(Intent.ACTION_TIME_CHANGED) ||
                action.equals(Intent.ACTION_TIMEZONE_CHANGED)) {
            if (onTick != null) {
                onTick.run();
            }
        }
    }
}
