package com.howzits.baselib.bindingadapter;

import android.view.View;

import androidx.databinding.BindingAdapter;

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
}
