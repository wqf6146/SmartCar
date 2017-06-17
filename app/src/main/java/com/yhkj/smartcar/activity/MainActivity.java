package com.yhkj.smartcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.vise.xsnow.manager.AppManager;
import com.yhkj.smartcar.BaseActivity;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.fragment.MainFragment;

/**
 * Created by Administrator on 2017/5/23.
 */

public class MainActivity extends BaseActivity {

    Boolean mPrepareExit = false;
    private final int BACK = 1;
    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == BACK){
                mPrepareExit = false;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadRootFragment(R.id.am_container, MainFragment.getInstance());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressedSupport() {
//        moveTaskToBack(false);
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (mPrepareExit){
                onReceiverDeadSigned();
                AppManager.getInstance().appExit(this);
            }else{
                mPrepareExit = true;
                showToast("再按一次退出汽车伴侣",2000);
                mHandler.sendEmptyMessageDelayed(BACK,2000l);
            }
        }
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }
}
