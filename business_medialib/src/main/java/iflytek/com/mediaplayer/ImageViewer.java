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
    private float endDis = 0f;
    private float old = 0f;

    public ImageViewer(Context context) {
        super(context);

    }

    public ImageViewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
        this.setScaleType(ScaleType.MATRIX);//这个千万不能忘记，不然不起作用。
    }

    public ImageViewer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch");
        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                startPointF.set(event.getX(), event.getY());
                currentMatrix.set(getImageMatrix());
                MODE_DRAG = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - startPointF.x;
                float dy = event.getY() - startPointF.y;
                if (MODE_DRAG) {
                    tempMatrix.set(currentMatrix);
                    tempMatrix.postTranslate(dx, dy);
                }

                /**
                 * 两个手指
                 */
                if (event.getPointerCount() == 2) {
                    endDis = calculatePointersDistance(event);
                    if (endDis > ViewConfiguration.get(this.getContext()).getScaledTouchSlop()) {
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
                    if (Math.abs(angle - old) > 3) {
                        PointF mid = calculateMidPointF(event);
                        tempMatrix.postRotate(angle - old, mid.x, mid.y);
                        Log.i(TAG, (angle - old) + "°");
                        setImageMatrix(tempMatrix);
                        old = angle;
                    }
                }

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                MODE_SCALE = true;
                startDis = calculatePointersDistance(event);
                old = calculateAngle(event);
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
     * @param event ,计算两个手指形成的向量与x轴的夹角。
     * @return
     */
    private float calculateAngle(MotionEvent event) {
        return (float) Math.toDegrees(Math.atan2(event.getY(0) - event.getY(1), event.getX(0) - event.getX(1)));
    }

    /**
     * 计算两个手指之间的距离
     *
     * @param event
     * @return
     */
    private float calculatePointersDistance(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            float dx = event.getX(0) - event.getX(1);
            float dy = event.getY(0) - event.getY(1);

            return (float) Math.sqrt(dx * dx + dy * dy);
        }
        return -1;
    }
}
