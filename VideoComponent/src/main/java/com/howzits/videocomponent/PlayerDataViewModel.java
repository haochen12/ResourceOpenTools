package com.howzits.videocomponent;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.howzits.baselib.bindingadapter.IClickCallback;

public class PlayerDataViewModel extends ViewModel {
    private MutableLiveData<Boolean> mPlayerLayoutShowStatus = new MutableLiveData<>();

    public PlayerDataViewModel() {
        this.mPlayerLayoutShowStatus.setValue(true);
    }

    public LiveData<Boolean> getPlayerLayoutShowStatus() {
        return mPlayerLayoutShowStatus;
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

    public IClickCallback mPlay = new IClickCallback() {
        @Override
        public void onClick() {
            Log.e("haochen", "play");
        }
    };
}
