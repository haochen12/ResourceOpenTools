package com.howzits.videocomponent;

import android.media.MediaPlayer;

public class PlayerManager {

    MediaPlayer mMediaPlayer;

    public static PlayerManager newInstance() {
        PlayerManager fragment = new PlayerManager();
        return fragment;
    }

    public PlayerManager() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }

    }

    public void adjustVolume(int volume) {
        mMediaPlayer.setVolume(volume, volume);
    }

    public void stop() {
        mMediaPlayer.stop();
    }

    public void seek(int time) {
        mMediaPlayer.seekTo(time);
    }
}
