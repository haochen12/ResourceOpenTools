package com.howzits.baselib.bindingadapter;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.databinding.BindingAdapter;

import com.howzits.baselib.R;

public class ViewAdapter {

    @BindingAdapter(value = {"clickCommand"}, requireAll = false)
    public static void clickEvent(View view, final IClickCallback iClickCallback) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickCallback.onClick();
            }
        });
    }

    @BindingAdapter(value = {"loadbitmap"}, requireAll = false)
    public static void loadBitmap(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter(value = {"drag_progress"}, requireAll = false)
    public static void getProgress(SeekBar seekBar, final IClickCallbackObject iClickCallbackObject) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                iClickCallbackObject.onClick(seekBar.getProgress());
            }
        });
    }

}
