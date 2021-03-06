package com.yhkj.smartcar;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.vise.xsnow.event.BusFactory;
import com.vise.xsnow.manager.AppManager;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.activity.LoginActivity;
import com.yhkj.smartcar.bean.db.DbHelper;
import com.yhkj.smartcar.utils.CommonUtils;
import com.yhkj.smartcar.utils.SystemBarTintManager;
import com.yhkj.smartcar.view.CookieBar;
import com.yhkj.smartcar.view.alertview.AlertView;
import com.yhkj.smartcar.view.alertview.OnItemClickListener;


import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.yokeyword.fragmentation.SupportActivity;

import static android.os.Build.VERSION_CODES.KITKAT;
import static com.vise.xsnow.net.mode.ApiCode.Response.ACCESS_TOKEN_ERROR;
import static com.vise.xsnow.net.mode.ApiCode.Response.ACCESS_TOKEN_EXPIRED;
import static com.vise.xsnow.net.mode.ApiCode.Response.ACCESS_TOKEN_FAILD;

/**
 * Created by Administrator on 2017/2/27.
 */

public abstract class BaseActivity extends SupportActivity {

    protected Context mContext;
    private boolean isOnResumeRegisterBus = false;
    private boolean isOnStartRegisterBus = false;
    protected KProgressHUD mHud;
    private int mStatusBar = R.color.gray_nav;
    private boolean isBackEnable = true;
//    private static final String EXITACTION = "action.exit";
//    private ExitReceiver exitReceiver = new ExitReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        AppManager.getInstance().addActivity(this);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintResource(mStatusBar);
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(EXITACTION);
//        registerReceiver(exitReceiver, filter);

    }


    protected void setStatusBarColor(@ColorRes int color){
        mStatusBar = color;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initView();
        bindEvent();
        initData();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initView();
        bindEvent();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOnResumeRegisterBus) {
            BusFactory.getBus().register(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isOnStartRegisterBus) {
            BusFactory.getBus().register(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isOnResumeRegisterBus) {
            BusFactory.getBus().unregister(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isOnStartRegisterBus) {
            BusFactory.getBus().unregister(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sDialog!=null)
            sDialog.dismiss();
        AppManager.getInstance().removeActivity(this);
    }

    protected boolean isOnResumeRegisterBus() {
        return isOnResumeRegisterBus;
    }

    protected BaseActivity setOnResumeRegisterBus(boolean onResumeRegisterBus) {
        isOnResumeRegisterBus = onResumeRegisterBus;
        return this;
    }

    protected boolean isOnStartRegisterBus() {
        return isOnStartRegisterBus;
    }

    protected BaseActivity setOnStartRegisterBus(boolean onStartRegisterBus) {
        isOnStartRegisterBus = onStartRegisterBus;
        return this;
    }

    /**
     * 初始化子View
     */
    protected abstract void initView();

    /**
     * 绑定事件
     */
    protected abstract void bindEvent();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    private SweetAlertDialog sDialog;
    public SweetAlertDialog showDialog(String msg){
        sDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(msg)
                .showCancelButton(false)
                .setCancelClickListener(null)
                .setConfirmClickListener(null);
        sDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.car_blue));
        sDialog.show();
        return sDialog;
    }

    protected Toast mToast;
    protected void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void showTip(@DrawableRes int icon, String str){
        if (Build.VERSION.SDK_INT  < KITKAT ){
            new CookieBar.Builder(this)
                    .setIcon(icon)
                    .setBackgroundColor(R.color.toastbg)
                    .setLayoutGravity(Gravity.BOTTOM)
                    .setMessageColor(R.color.white)
                    .setMessage(str)
                    .show();
            return;
        }
        if (CommonUtils.areNotificationsEnabled(this)){
            showToast(str);
        }else{
            new CookieBar.Builder(this)
                    .setIcon(icon)
                    .setBackgroundColor(R.color.toastbg)
                    .setLayoutGravity(Gravity.BOTTOM)
                    .setMessageColor(R.color.white)
                    .setMessage(str)
                    .show();
        }
    }

    protected void showToast(String text,int mis) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text,
                    mis);
        } else {
            mToast.setText(text);
            mToast.setDuration(mis);
        }
        mToast.show();
    }

    public <T> boolean checkNotNull(T reference) {
        if (reference == null) {
            return true;
        }
        return false;
    }

    protected void onReceiverDeadSigned(){

    }

//    class ExitReceiver extends BroadcastReceiver {
//        @Override public void onReceive(Context context, Intent intent) {
//            onReceiverDeadSigned();
//            BaseActivity.this.finish();
//        }
//    }


    @Override
    public void onBackPressedSupport() {
        if (isBackEnable)
            super.onBackPressedSupport();
    }

    /***
     *
     * @param sDialog
     * @param e
     * @return
     */
    protected boolean filterHttpCallback(SweetAlertDialog sDialog, ApiException e){
        switch (e.getCode()){
            //授权失败 需重新登录
            case ACCESS_TOKEN_ERROR :
            case ACCESS_TOKEN_FAILD :
            case ACCESS_TOKEN_EXPIRED :
                if (sDialog!=null)
                    sDialog.dismissWithDelay(500);
                isBackEnable = false;
                DbHelper.getInstance().configLongDBManager().deleteAll();
                new AlertView(null, "\n授权过期需要重新登录", null, new String[]{"确定"}, null, BaseActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position==0){
                            Intent intent = new Intent();
                            intent.setClass(BaseActivity.this,LoginActivity.class);
                            startActivity(intent);
                            AppManager.getInstance().finishAllActivity();
                        }
                    }
                }).show();
                return true;
        }
        return false;
    }
}
