package iflytek.com.mediaplayer;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class ImageViewer extends android.support.v7.widget.AppCompatImageView implements View.OnTouchListener {
    public static final String TAG = ImageViewer.class.getSimpleName();

    private Matrix currentMatrix = new Matrix();
    private PointF startPointF = new PointF();
    private Matrix tempMatrix = new Matrix();
    private boolean MODE_DRAG = false;
    private boolean MODE_SCALE = false;
    private float startDis = 0f;
    private float oldAngle;

    public ImageViewer(Context context) {
        super(context);

    }

    public ImageViewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
        this.setScaleType(ScaleType.MATRIX);
    }

    public ImageViewer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                startPointF.set(event.getX(), event.getY());
                currentMatrix.set(this.getImageMatrix());
                MODE_DRAG = true;
                oldAngle = calculateAngle(event);
                Log.i(TAG, "onTouch");
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - startPointF.x;
                float dy = event.getY() - startPointF.y;
                if (MODE_DRAG) {
                    tempMatrix.set(currentMatrix);
                    tempMatrix.postTranslate(dx, dy);
                }

                if (event.getPointerCount() == 2) {
                    float endDis = calculatePointerDistance(event);
                    if (endDis > ViewConfiguration.getTouchSlop()) {
                        currentMatrix.set(getImageMatrix());
                        tempMatrix.set(currentMatrix);
                        float[] values = new float[9];
                        tempMatrix.getValues(values);
                        tempMatrix.postScale(endDis / startDis, endDis / startDis, event.getX(), event.getY());
                        setImageMatrix(tempMatrix);
                        startDis = endDis;
                    }
                    //旋转

                    float angle = calculateAngle(event);
                    Log.i("haochen12", angle - oldAngle + "flsjflskjdlfk");
                    if (angle - oldAngle > 10) {
                        PointF mid = calculateMidPointF(event);
                        tempMatrix.postRotate(angle - oldAngle, mid.x, mid.y);
                        setImageMatrix(tempMatrix);
                    }

                }

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                MODE_SCALE = true;
                startDis = calculatePointerDistance(event);
                Log.i(TAG, "ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                MODE_SCALE = false;
                MODE_DRAG = false;
                break;
        }

        this.setImageMatrix(tempMatrix);
        return true;
    }

    /**
     * 计算两个手指间的中位点。
     *
     * @param event
     * @return
     */
    private PointF calculateMidPointF(MotionEvent event) {
        PointF mPointF = new PointF();
        mPointF.set((event.getX(0) + event.getX(1)) / 2, (event.getY(0) + event.getY(1)) / 2);
        return mPointF;
    }


    /**
     * 计算两个手指的旋转角度
     *
     * @param event
     * @return
     */
    private float calculateAngle(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            float sum = event.getX(0) * event.getX(1) + event.getY(0) * event.getY(1);
            float eDis1 = (float) Math.sqrt(event.getX(0) * event.getX(0) + event.getY(0) * event.getY(0));
            float eDis2 = (float) Math.sqrt(event.getX(1) * event.getX(1) + event.getY(1) * event.getY(1));
            return (float) Math.toDegrees(Math.acos(sum / (eDis1 * eDis2)));
        }

        return -1;
    }

    /**
     * 计算两个手指之间的距离
     *
     * @param event
     * @return
     */
    private float calculatePointerDistance(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            float dx = event.getX(0) - event.getX(1);
            float dy = event.getY(0) - event.getY(1);

            return (float) Math.sqrt(dx * dx + dy * dy);
        }
        return -1;
    }
}
