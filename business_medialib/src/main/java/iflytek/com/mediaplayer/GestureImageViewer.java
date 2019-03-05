package iflytek.com.mediaplayer;

import android.content.Context;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class GestureImageViewer extends ImageView implements GestureDetector.OnGestureListener,
        ScaleGestureDetector.OnScaleGestureListener,
        GestureDetector.OnDoubleTapListener {
    private static final String TAG = GestureImageViewer.class.getSimpleName();
    private GestureDetector mGestureDetector = null;
    private ScaleGestureDetector mScaleGestureDetector = null;
    private Matrix mMatrix = new Matrix();
    private float startDis = 0f;
    private float oldAngle = 0f;

    public GestureImageViewer(Context context) {
        super(context);
    }

    public GestureImageViewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);//这个千万不要忘记了，否则没有效果。
        mGestureDetector = new GestureDetector(context, this);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mMatrix.set(this.getImageMatrix());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        if (mScaleGestureDetector.onTouchEvent(event)) {
            return true;
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                oldAngle = calculateAngle(event);
                return false;
            case MotionEvent.ACTION_MOVE:
                float angle = oldAngle - calculateAngle(event);
                mMatrix.postRotate(angle, event.getX(), event.getY());
                oldAngle = angle;
                break;
        }


        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(TAG, "onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(TAG, "onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent event, float distanceX, float distanceY) {
        Log.i(TAG, "onScroll");
        mMatrix.postTranslate(-distanceX, -distanceY);
        setImageMatrix(mMatrix);

        return false;
    }

    private float calculateAngle(MotionEvent event) {
        return (float) Math.toDegrees(Math.atan2(event.getY(0) - event.getY(1), event.getX(0) - event.getX(1)));
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(TAG, "onLongPress");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG, "onFling" + "velocityX:" + velocityX + "velocityY:" + velocityY);
        return false;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        mMatrix.set(getImageMatrix());
        float scaleFactor = detector.getCurrentSpan() / startDis;
        mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
        setImageMatrix(mMatrix);
        startDis = detector.getCurrentSpan();
        Log.i(TAG, "onScale" + scaleFactor);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        startDis = detector.getCurrentSpan();
        Log.i(TAG, "onScaleBegin");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.i(TAG, "onScaleEnd");

    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.i(TAG, "onSingleTapConfirmed");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG, "onDoubleTap");
        mMatrix.set(getImageMatrix());
        float[] values = new float[9];
        mMatrix.getValues(values);
        float scaleFactor = values[Matrix.MSCALE_X];
        if (scaleFactor == 1) {
            mMatrix.postScale(1.5f, 1.5f, e.getX(), e.getY());
        } else {
            mMatrix.reset();
        }
        setImageMatrix(mMatrix);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.i(TAG, "onDoubleTapEvent");
        return true;
    }
}
