package com.howzits.videocomponent;

import com.howzits.baselib.BaseActivity;
import com.howzits.videocomponent.databinding.ActivityMainBinding;

public class VideoPlayerActivity extends BaseActivity<ActivityMainBinding, PlayerDataViewModel> {

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public int getVariableId() {
        return BR.dataviewmodel;
    }
}