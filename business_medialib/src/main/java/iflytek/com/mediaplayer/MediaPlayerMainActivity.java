package iflytek.com.mediaplayer;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class MediaPlayerMainActivity extends AppCompatActivity implements View.OnTouchListener {
    private ImageView mImageView;
    private Matrix mCurrentMatrix = new Matrix();
    private Matrix mTempMatrix = new Matrix();

    private PointF startPointF = new PointF();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.image);
        mImageView.setOnTouchListener(this);
        mImageView.setScaleType(ImageView.ScaleType.MATRIX);//不设置就无法有效果。
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mCurrentMatrix.set(mImageView.getImageMatrix());
                startPointF.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                PointF mPointF = new PointF();
                mPointF.set(event.getX(), event.getY());
                float dx = event.getX() - startPointF.x;
                float dy = event.getY() - startPointF.y;
                mTempMatrix.set(mCurrentMatrix);
                mTempMatrix.postTranslate(dx, dy);
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        mImageView.setImageMatrix(mTempMatrix);
        return true;
    }
}
