package kaisaii.itsrainingclocks.interfaces;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by salon on 28/06/17.
 */

public class ClockView extends ViewGroup {

    private List<Segment> mData = new ArrayList<Segment>();
    private ClockCircle mClockCircle;
    private Paint mClockPaint;
    private float mTotal = 12;


    private RectF mPieBounds = new RectF();
    private float xpad = 20;
    private float ypad = 20;

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

        mClockCircle = new ClockCircle(getContext());
        addView(mClockCircle);
    }

    public int addItem(float value, int color) {
        Segment it = new Segment();
        it.mColor = color;
        it.mValue = value;

        mData.add(it);
        onDataChanged();

        return mData.size() - 1;
    }

    /**
     * Calculate all views when mData changed
     */
    private void onDataChanged() {
        int currentAngle = 0;
        for (Segment it : mData) {
            // Calculate angles
            it.mStartAngle = currentAngle;
            it.mEndAngle = (int) ((float) currentAngle + it.mValue * 360.0f / mTotal);
            currentAngle = it.mEndAngle;

            // Background color of item
            it.mShader = new SweepGradient(
                    mPieBounds.width() / 2.0f,
                    mPieBounds.height() / 2.0f,
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
        mPieBounds = new RectF(
                0.0f,
                0.0f,
                diameter,
                diameter);
        mPieBounds.offsetTo(getPaddingLeft(), getPaddingTop());

        // Lay out the child view that actually draws the pie.
        mClockCircle.layout((int) mPieBounds.left,
                (int) mPieBounds.top,
                (int) mPieBounds.right,
                (int) mPieBounds.bottom);
        //mClockCircle.setPivot(mPieBounds.width() / 2, mPieBounds.height() / 2);

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

            for (Segment it : mData) {
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
