package com.howzits.videocomponent;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

import com.howzits.baselib.BaseActivity;
import com.howzits.videocomponent.databinding.ActivityMainBinding;

public class VideoPlayerActivity extends BaseActivity<ActivityMainBinding, PlayerDataViewModel> implements SurfaceHolder.Callback {
    private String path = "/sdcard/导出资源/【情境课文】雪地里的小画家情境课文（诵读.mp4";
    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SurfaceView mSurfaceView = binding.surfaceview;
        mSurfaceView.getHolder().addCallback(this);
    }

    @Override
    public int getVariableId() {
        return BR.dataviewmodel;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        PlayerManager.newInstance().initMediaPlayer(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayerManager.newInstance().stop();
    }
}