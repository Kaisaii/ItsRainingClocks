package kaisaii.itsrainingclocks.interfaces.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by salon on 28/06/17.
 */

public class ClockView extends ViewGroup {
    private List<Segment> pmSegments = new ArrayList<>();
    private List<Segment> amSegments = new ArrayList<>();

    private ClockCircle pmCircle;
    private ClockCircle amCircle;
    private Paint mClockPaint;
    private float mTotal = 12;

    private RectF pmBounds = new RectF();
    private RectF amBounds = new RectF();

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mClockPaint.setStyle(Paint.Style.FILL);

        pmCircle = new ClockCircle(getContext());
        addView(pmCircle);
    }

    public int addItem(float value, int color) {
        Segment it = new Segment();
        it.mColor = ContextCompat.getColor(getContext(), color);
        it.mValue = value;

        pmSegments.add(it);
        onDataChanged();

        return pmSegments.size() - 1;
    }

    /**
     * Calculate all views when pmSegments changed
     */
    private void onDataChanged() {
        int currentAngle = 0;
        for (Segment it : pmSegments) {
            // Calculate angles
            it.mStartAngle = currentAngle;
            it.mEndAngle = (int) ((float) currentAngle + it.mValue * 360.0f / mTotal);
            currentAngle = it.mEndAngle;

            // Background color of item
            it.mShader = new SweepGradient(
                    pmBounds.width() / 2.0f,
                    pmBounds.height() / 2.0f,
                    new int[]{
                            it.mColor,
                            it.mColor,
                            it.mColor,
                            it.mColor,
                    },
                    new float[]{
                            0,
                            (float) (360 - it.mEndAngle) / 360.0f,
                            (float) (360 - it.mStartAngle) / 360.0f,
                            1.0f
                    }
            );
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Evaluate view padding
        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());

        float ww = (float) w - xpad;
        float hh = (float) h - ypad;

        // Figure out how big we can make the pie.
        float diameter = Math.min(ww, hh);

        float y = ( (hh - diameter) / 2) + getPaddingTop();
        pmBounds = new RectF(
                0.0f,
                0.0f,
                diameter,
                diameter);
        pmBounds.offsetTo(getPaddingLeft(), y);

        // Lay out the child view that actually draws the pie.
        pmCircle.layout((int) pmBounds.left,
                (int) pmBounds.top,
                (int) pmBounds.right,
                (int) pmBounds.bottom);

        onDataChanged();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try for a width based on our minimum
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();

        int w = Math.max(minw, MeasureSpec.getSize(widthMeasureSpec));

        setMeasuredDimension(w, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    /**
     *
     */
    private class ClockCircle extends View {
        RectF mBounds;

        public ClockCircle(Context context) {
            super(context);
        }

        public ClockCircle(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            for (Segment it : pmSegments) {
                mClockPaint.setShader(it.mShader);
                canvas.drawArc(mBounds,
                        360 - 90 + it.mStartAngle,
                        it.mEndAngle - it.mStartAngle,
                        true,
                        mClockPaint);
            }
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            mBounds = new RectF(0,0,w,h);
        }
    }


    /**
     * Segment defined by a color & a value
     * (max 12 for the 12 hours on clock)
     */
    private class Segment {
        public float mValue;
        public int mColor;

        public int mStartAngle;
        public int mEndAngle;
        public int mHighlight;
        public Shader mShader;
    }
}
