package com.howzits.baselib.base;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<VDB extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity {
    protected VM mViewModel;
    protected VDB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewBinding();
        initVM();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    private void initVM() {
        Class modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];//这个0是根据<>中的次序来定的
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            modelClass = ViewModel.class;
        }
        mViewModel = createViewModel(modelClass);

        binding.setVariable(getVariableId(), mViewModel);
        binding.setLifecycleOwner(this);
    }

    private void initViewBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutResId());
    }

    public abstract int getLayoutResId();

    private VM createViewModel(Class cls) {
        return (VM) new ViewModelProvider.NewInstanceFactory().create(cls);
    }

    public abstract int getVariableId();

    @Override
    protected void onDestroy() {
        if (binding != null) {
            binding.unbind();
        }
        super.onDestroy();
    }
}
