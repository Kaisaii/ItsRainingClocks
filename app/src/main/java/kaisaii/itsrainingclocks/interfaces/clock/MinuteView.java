package kaisaii.itsrainingclocks.interfaces.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;

import kaisaii.itsrainingclocks.R;

/**
 * Created by salon on 01/07/17.
 */

public class MinuteView extends View {
    RectF mBounds;

    public MinuteView(Context context) {
        super(context);
        init();
    }

    public MinuteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MinuteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawExternalCircle(canvas);
        drawMinutes(canvas);
    }

    /**
     * Draw dashed circle around clock
     * @param canvas
     */
    private void drawExternalCircle(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.rain_strong));
        paint.setStrokeWidth(4);

        DashPathEffect dashPath = new DashPathEffect(new float[]{20,20}, (float)1.0);
        paint.setPathEffect(dashPath);

        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;

        canvas.drawCircle(centerX, centerY, centerX, paint);
    }

    private void drawMinutes(Canvas canvas) {
        Calendar rightNow = Calendar.getInstance();
        int minutes = rightNow.get(Calendar.MINUTE);

        double minuteRad = (minutes * 2 * Math.PI / 60) - Math.PI / 2;

        int canvasCenterX = canvas.getWidth() / 2;
        int canvasCenterY = canvas.getHeight() / 2;
        int radius = canvasCenterX;

        float centerX = (float) (canvasCenterX + radius * Math.cos(minuteRad));
        float centerY = (float) (canvasCenterY + radius * Math.sin(minuteRad));

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.hail_active));

        canvas.drawCircle(centerX, centerY, 50, paint);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
