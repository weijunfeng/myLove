package weijunfeng.com.mylove.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * 1. 如何得知用户点击了哪个元素
 * 2. 如何取得被点击元素的信息
 * 3. 如何通过layout进行重绘绘制水波纹
 * 4. 如果延迟up事件的分发
 */
public class RevealLayout extends FrameLayout {
    private static final int INVALIDATE_DURATION = 100;
    private View mTouchTarget;
    private boolean mIsPressed;
    private int mCenterX, mCenterY;
    private int mTargetWidth = 0;
    private boolean mShouldDoAnimation;
    private int mMinBetweenWidthAndHeight;
    private int mRevealRadius;
    private int mRevealRadiusGap = 15;
    private int mMaxRevealRadius;
    private int[] mLocationInScreen = new int[2];
    private Paint mPaint;
    private DispatchUpTouchEventRunnable mDispatchUpTouchEventRunnable;

    public RevealLayout(Context context) {
        this(context, null, 0);
    }

    public RevealLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(0x66888888);
        mDispatchUpTouchEventRunnable = new DispatchUpTouchEventRunnable();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            View touchTarget = getTouchTarget(this, x, y);
            if (touchTarget != null && touchTarget.isClickable() && touchTarget.isEnabled()) {
                mTouchTarget = touchTarget;
                initParametersForChild(event, touchTarget);
                postInvalidateDelayed(INVALIDATE_DURATION);
            }
        } else if (action == MotionEvent.ACTION_UP) {
            mIsPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
            mDispatchUpTouchEventRunnable.event = event;
            postDelayed(mDispatchUpTouchEventRunnable, 400);
            return true;
        } else if (action == MotionEvent.ACTION_CANCEL) {
            mIsPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
        }

        return super.dispatchTouchEvent(event);
    }

    private View getTouchTarget(View view, int x, int y) {
        View target = null;
        ArrayList<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews) {
            if (isTouchPointInView(child, x, y)) {
                target = child;
                break;
            }
        }

        return target;
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (view.isClickable() && y >= top && y <= bottom
                && x >= left && x <= right) {
            return true;
        }
        return false;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!mShouldDoAnimation || mTargetWidth <= 0 || mTouchTarget == null) {
            return;
        }

        if (mRevealRadius > mMinBetweenWidthAndHeight / 2) {
            mRevealRadius += mRevealRadiusGap * 4;
        } else {
            mRevealRadius += mRevealRadiusGap;
        }
        int[] location = new int[2];
        mTouchTarget.getLocationOnScreen(location);
//        mTouchTarget.getLocationInWindow(location);
        int left = location[0] - mLocationInScreen[0];
        int top = location[1] - mLocationInScreen[1];
        int right = left + mTouchTarget.getMeasuredWidth();
        int bottom = top + mTouchTarget.getMeasuredHeight();
//        Rect rect = new Rect();
////        mTouchTarget.getDrawingRect(rect);
////        mTouchTarget.getFocusedRect(rect);
////        mTouchTarget.getGlobalVisibleRect(rect);
//        mTouchTarget.getLocalVisibleRect(rect);
        canvas.save();
        canvas.clipRect(left, top, right, bottom);
        canvas.drawCircle(mCenterX, mCenterY, mRevealRadius, mPaint);
        canvas.restore();

        if (mRevealRadius <= mMaxRevealRadius) {
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
        } else if (!mIsPressed) {
            mShouldDoAnimation = false;
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
        }
    }

    //初始化效果开始的数据
    private void initParametersForChild(MotionEvent event, View view) {
        mCenterX = (int) event.getX();
        mCenterY = (int) event.getY();
        mTargetWidth = view.getMeasuredWidth();
        mShouldDoAnimation = true;
        mMinBetweenWidthAndHeight = Math.min(view.getMeasuredWidth(), view.getMeasuredHeight());
        mMaxRevealRadius = getMaxRevealRadius(event, view);
        mRevealRadius = 0;
        getLocationOnScreen(mLocationInScreen);
//        getLocationInWindow(mLocationInScreen);
    }

    //获取最大半径
    private int getMaxRevealRadius(MotionEvent event, View view) {
        Point centerP = new Point((int) event.getX(), (int) event.getY());

//        Point ltP = new Point(0, 0);
//        Point lbP = new Point(0, view.getMeasuredHeight());
//        Point trP = new Point(view.getMeasuredWidth(), 0);
//        Point tbP = new Point(view.getMeasuredWidth(), view.getMeasuredHeight());

        int[] location = new int[2];
        mTouchTarget.getLocationOnScreen(location);
//        mTouchTarget.getLocationInWindow(location);
        int left = location[0] - mLocationInScreen[0];
        int top = location[1] - mLocationInScreen[1];
        int right = left + mTouchTarget.getMeasuredWidth();
        int bottom = top + mTouchTarget.getMeasuredHeight();
        Point ltP = new Point(left, top);
        Point lbP = new Point(left, bottom);
        Point trP = new Point(right, top);
        Point tbP = new Point(right, bottom);
        int length = Math.max(Math.max(centerP.betweenlength(ltP), centerP.betweenlength(lbP)), Math.max(centerP.betweenlength(trP), centerP.betweenlength(tbP)));
        return length;
    }

    private class DispatchUpTouchEventRunnable implements Runnable {
        public MotionEvent event;

        @Override
        public void run() {
            if (mTouchTarget == null || !mTouchTarget.isEnabled()) {
                return;
            }

            if (isTouchPointInView(mTouchTarget, (int) event.getRawX(), (int) event.getRawY())) {
                mTouchTarget.dispatchTouchEvent(event);
            }
        }
    }

    class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        //两点之间的距离
        public int betweenlength(Point other) {
            return (int) Math.sqrt(StrictMath.pow((x - other.x), 2) + StrictMath.pow((y - other.y), 2));
        }
    }
}