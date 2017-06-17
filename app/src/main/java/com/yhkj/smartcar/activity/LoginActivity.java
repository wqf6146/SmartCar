package com.yhkj.smartcar.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yhkj.smartcar.BaseActivity;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.fragment.LoginFragment;

/**
 * Created by Administrator on 2017/5/22.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadRootFragment(R.id.al_container, LoginFragment.getInstance());
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindEvent() {

    }
}
