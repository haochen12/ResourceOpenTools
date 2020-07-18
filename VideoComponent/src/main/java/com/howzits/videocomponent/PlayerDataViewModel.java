package com.howzits.videocomponent;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.howzits.baselib.bindingadapter.IClickCallback;
import com.howzits.baselib.bindingadapter.IClickCallbackObject;
import com.howzits.baselib.util.DateUtil;
import com.howzits.baselib.util.VideoUtil;

public class PlayerDataViewModel extends ViewModel {
    private String path = "/sdcard/导出资源/【情境课文】雪地里的小画家情境课文（诵读.mp4";
    private MutableLiveData<Boolean> mPlayerLayoutShowStatus = new MutableLiveData<>();
    public MutableLiveData<Integer> mPlayerProgress = new MutableLiveData<>();
    public MutableLiveData<String> mTotalTime = new MutableLiveData<>();
    public MutableLiveData<String> mCurrentTime = new MutableLiveData<>();
    public MutableLiveData<Bitmap> firstFrameBitmap = new MutableLiveData<>();
    public MutableLiveData<Boolean> showFirstFrameBitmap = new MutableLiveData<>();

    public PlayerDataViewModel() {
        this.mPlayerLayoutShowStatus.setValue(true);
        firstFrameBitmap.setValue(VideoUtil.getFirstBitmpa(path));
        showFirstFrameBitmap.setValue(true);
    }

    public LiveData<Bitmap> mBitmapLiveData() {
        return firstFrameBitmap;
    }

    public LiveData<Boolean> getPlayerLayoutShowStatus() {
        return mPlayerLayoutShowStatus;
    }

    public LiveData<Integer> getPlayerProgress() {
        return mPlayerProgress;
    }

    public IClickCallback mShowPlayerMenuStatusCallback = new IClickCallback() {
        @Override
        public void onClick() {
            if (!mPlayerLayoutShowStatus.getValue()) {
                mPlayerLayoutShowStatus.setValue(true);
            } else {
                mPlayerLayoutShowStatus.setValue(false);
            }
        }
    };

    public IClickCallback mPre = new IClickCallback() {
        @Override
        public void onClick() {
            Log.e("haochen", "pre");
        }
    };

    public IClickCallback mNext = new IClickCallback() {
        @Override
        public void onClick() {
            Log.e("haochen", "next");
        }
    };

    public IClickCallbackObject mIClickCallbackObject = new IClickCallbackObject() {
        @Override
        public void onClick(int p) {
            PlayerManager.newInstance().seek(PlayerManager.newInstance().getDuration() * p / 100);
            Log.e("haochen", "pppp");
        }
    };


    public IClickCallback mPlay = new IClickCallback() {
        @Override
        public void onClick() {
            if (PlayerManager.newInstance().isPlaying()) {
                PlayerManager.newInstance().stop();
            } else {
                PlayerManager.newInstance().playVideo(path);
                showFirstFrameBitmap.setValue(false);
            }
            mTotalTime.setValue(DateUtil.formatStandardTime(PlayerManager.newInstance().getDuration()));
            Log.e("haochen", "play");
            mHandler.sendEmptyMessage(0);
        }
    };

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            mPlayerProgress.setValue(PlayerManager.newInstance().currentPercentage());
            mCurrentTime.setValue(DateUtil.formatStandardTime(PlayerManager.newInstance().getCurrentPosition()));
            Log.e("haochen", "tests");
            if (PlayerManager.newInstance().isPlaying()) {
                mHandler.sendEmptyMessage(0);
            }
            return false;
        }
    });

    @Override
    protected void onCleared() {
        super.onCleared();
        PlayerManager.newInstance().stop();
    }
}
