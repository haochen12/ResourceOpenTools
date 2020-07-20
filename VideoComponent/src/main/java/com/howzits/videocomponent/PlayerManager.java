package com.howzits.videocomponent;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import com.howzits.baselib.Constant;

import java.io.IOException;

public class PlayerManager implements MediaPlayer.OnCompletionListener {

    private MediaPlayer mMediaPlayer;

    private int status = -1;

    private static PlayerManager INSTANCE;

    public static PlayerManager newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }

    private PlayerManager() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            status = Constant.STATUS_INIT;
        }

        mMediaPlayer.setOnCompletionListener(this);
    }

    public void setDisplay(SurfaceHolder surfaceHolder) {
        mMediaPlayer.setDisplay(surfaceHolder);
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public int playStatus() {
        return status;
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public void playVideo(String path) {
        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            status = Constant.STATUS_PLAY;
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mHandler.sendEmptyMessage(0);
    }


    public void stopPlay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            status = Constant.STATUS_STOP;
        }
    }

    public void replay() {
        if (status == Constant.STATUS_STOP) {
            mMediaPlayer.start();
            status = Constant.STATUS_PLAY;
            mHandler.sendEmptyMessage(0);
        }
    }


    public void seek(int time) {
        mMediaPlayer.seekTo(time);
    }

    public int currentPercentage() {
        return 100 * mMediaPlayer.getCurrentPosition() / mMediaPlayer.getDuration();
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (PlayerManager.newInstance().playStatus() == Constant.STATUS_PLAY) {
                mIProgressListener.clickProgress(currentPercentage());
                mHandler.sendEmptyMessage(0);
            }
            return false;
        }
    });

    public void destroy() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            status = Constant.STATUS_INIT;
            mMediaPlayer = null;
        }

        if (INSTANCE != null) {
            INSTANCE = null;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        status = Constant.STATUS_STOP;
    }

    public IProgressListener mIProgressListener;

    public void setProgressListener(IProgressListener iProgressListener) {
        this.mIProgressListener = iProgressListener;
    }

    public interface IProgressListener {
        void clickProgress(int i);
    }
}
