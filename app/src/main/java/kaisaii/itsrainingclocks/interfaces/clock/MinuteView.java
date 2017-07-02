package kaisaii.itsrainingclocks.interfaces.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

import kaisaii.itsrainingclocks.R;

/**
 * Created by salon on 01/07/17.
 */

public class MinuteView extends View {
    RectF mBounds = new RectF();
    private int minutesWidth = 50;
    private int minutesValue = 0;

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

    public void updateMinutes(int minutes) {
        minutesValue = minutes;
        invalidate();
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
        int radius = centerX - minutesWidth;

        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    /**
     * Draw blue circle and place it on first circle
     * Angle depends on current Time
     * @param canvas
     */
    private void drawMinutes(Canvas canvas) {
        double minuteRad = (minutesValue * 2 * Math.PI / 60) - Math.PI / 2;

        int canvasCenterX = canvas.getWidth() / 2;
        int canvasCenterY = canvas.getHeight() / 2;
        int radius = canvasCenterX - minutesWidth;

        float centerX = (float) (canvasCenterX + radius * Math.cos(minuteRad));
        float centerY = (float) (canvasCenterY + radius * Math.sin(minuteRad));

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.hail_active));

        canvas.drawCircle(centerX, centerY, minutesWidth, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mBounds.offsetTo(getPaddingLeft(), getPaddingTop());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
