package com.howzits.videocomponent;

import android.media.MediaPlayer;
import android.view.SurfaceHolder;

import java.io.IOException;

public class PlayerManager {

    private MediaPlayer mMediaPlayer;

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
        }
    }

    public void initMediaPlayer(SurfaceHolder surfaceHolder) {
        mMediaPlayer.setDisplay(surfaceHolder);
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public void setDataSource(String path) {

    }

    public void playVideo(String path) {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        } else {
            try {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(path);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.start();
        }
    }

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    public void seek(int time) {
        mMediaPlayer.seekTo(time);
    }

    public int currentPercentage() {
        return 100 * mMediaPlayer.getCurrentPosition() / mMediaPlayer.getDuration();
    }
}
