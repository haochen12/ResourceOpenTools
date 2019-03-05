package iflytek.com.mediaplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {
    private static final String TAG = CustomView.class.getSimpleName();
    private Paint mPaint = null;
    private int dx;
    private int dy;
    private int startX;
    private int startY;
    private Canvas mCanvas;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure");
        setMeasuredDimension(getDefaultSize(widthMeasureSpec), getDefaultSize(heightMeasureSpec));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                dx = (int) event.getX() - startX;
                dy = (int) event.getY() - startY;
                layout(getLeft() + dx, getTop() + dy, getRight() + dx, getBottom() + dy);
                invalidate();//onDraw
//                requestLayout();//onMeasure->onLayout
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        canvas.drawCircle(300, 300, 300, mPaint);
        Log.i(TAG, "onDraw");
    }

    private int getDefaultSize(int measureSpec) {
        int defaultSize = 100;
        int measureMode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);

        switch (measureMode) {
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, "MeasureSpec.UNSPECIFIED");
                return defaultSize;
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "MeasureSpec.AT_MOST");
                return measureSize;
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "MeasureSpec.EXACTLY");
                return measureSize;
        }
        return -1;
    }
}
