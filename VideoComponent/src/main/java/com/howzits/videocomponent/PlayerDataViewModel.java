package com.howzits.videocomponent;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.howzits.baselib.Constant;
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

    public MutableLiveData<Boolean> changePlayerStatus = new MutableLiveData<>();

    public PlayerDataViewModel() {
        this.mPlayerLayoutShowStatus.setValue(true);
        firstFrameBitmap.setValue(VideoUtil.getFirstBitmpa(path));
        showFirstFrameBitmap.setValue(true);
    }

    public LiveData<Boolean> changePlayerStatus(){
        return changePlayerStatus;
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
            if (PlayerManager.newInstance().playStatus() == Constant.STATUS_PLAY) {
                PlayerManager.newInstance().stopPlay();
                changePlayerStatus.setValue(true);
            } else if (PlayerManager.newInstance().playStatus() == Constant.STATUS_STOP) {
                PlayerManager.newInstance().replay();
                changePlayerStatus.setValue(false);
            } else {
                PlayerManager.newInstance().playVideo(path);
                showFirstFrameBitmap.setValue(false);
                changePlayerStatus.setValue(false);
            }
            mTotalTime.setValue(DateUtil.formatStandardTime(PlayerManager.newInstance().getDuration()));
            Log.e("haochen", "play" + PlayerManager.newInstance().playStatus());
            PlayerManager.newInstance().setProgressListener(new PlayerManager.IProgressListener() {
                @Override
                public void clickProgress(int i) {
                    mCurrentTime.setValue(DateUtil.formatStandardTime(PlayerManager.newInstance().getCurrentPosition()));
                    mPlayerProgress.setValue(i);
                }
            });
        }
    };

    @Override
    protected void onCleared() {
        super.onCleared();
        PlayerManager.newInstance().stopPlay();
    }
}
