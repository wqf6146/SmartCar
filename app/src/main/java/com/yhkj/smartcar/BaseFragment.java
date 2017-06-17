package com.yhkj.smartcar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.vise.xsnow.event.BusFactory;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.activity.LoginActivity;
import com.yhkj.smartcar.bean.db.DbHelper;
import com.yhkj.smartcar.utils.CommonUtils;
import com.yhkj.smartcar.view.CookieBar;
import com.yhkj.smartcar.view.alertview.AlertView;
import com.yhkj.smartcar.view.alertview.OnItemClickListener;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.yokeyword.fragmentation.SupportFragment;

import static android.os.Build.VERSION_CODES.KITKAT;
import static com.vise.xsnow.net.mode.ApiCode.Response.ACCESS_TOKEN_ERROR;
import static com.vise.xsnow.net.mode.ApiCode.Response.ACCESS_TOKEN_EXPIRED;
import static com.vise.xsnow.net.mode.ApiCode.Response.ACCESS_TOKEN_FAILD;

/**
 * Created by Administrator on 2017/2/28.
 */

public abstract class BaseFragment extends SupportFragment {
    protected View mRootView;
    protected Context mContext;
    protected Resources mResources;
    protected LayoutInflater mInflater;
    private boolean isOnResumeRegisterBus = false;
    private boolean isOnStartRegisterBus = false;
    private Toast mToast;

    private boolean isBackEnable = true;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
        this.mResources = mContext.getResources();
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getFragmentLayoutId(), container, false);
        ButterKnife.bind(this,mRootView);
        initView(mRootView,savedInstanceState);
        bindEvent();
        initData();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isOnResumeRegisterBus) {
            BusFactory.getBus().register(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isOnStartRegisterBus) {
            BusFactory.getBus().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isOnResumeRegisterBus) {
            BusFactory.getBus().unregister(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isOnStartRegisterBus) {
            BusFactory.getBus().unregister(this);
        }
    }

    private SweetAlertDialog mDialog;

    public SweetAlertDialog showDialog(String msg){
        mDialog = new SweetAlertDialog(_mActivity,SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(msg)
                .showCancelButton(false)
                .setCancelClickListener(null)
                .setConfirmClickListener(null);
        mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.car_blue));
        mDialog.show();
        return mDialog;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDialog!=null)
            mDialog.dismiss();
    }

    protected boolean isOnResumeRegisterBus() {
        return isOnResumeRegisterBus;
    }

    protected BaseFragment setOnResumeRegisterBus(boolean onResumeRegisterBus) {
        isOnResumeRegisterBus = onResumeRegisterBus;
        return this;
    }

    protected boolean isOnStartRegisterBus() {
        return isOnStartRegisterBus;
    }

    protected BaseFragment setOnStartRegisterBus(boolean onStartRegisterBus) {
        isOnStartRegisterBus = onStartRegisterBus;
        return this;
    }

    /**
     * 初始化子View
     */
    protected abstract void initView(View contentView,Bundle savedInstanceState);

    /**
     * 绑定事件
     */
    protected abstract void bindEvent();

    /**
     * 初始化数据
     */
    protected abstract void initData();
    /**
     * 布局的LayoutID
     *
     * @return
     */
    abstract protected int getFragmentLayoutId();

    public void showTip(@DrawableRes int icon, String str){
        if (Build.VERSION.SDK_INT  < KITKAT ){
            new CookieBar.Builder(_mActivity)
                    .setIcon(icon)
                    .setBackgroundColor(R.color.toastbg)
                    .setLayoutGravity(Gravity.BOTTOM)
                    .setMessageColor(R.color.white)
                    .setMessage(str)
                    .show();
            return;
        }
        if (CommonUtils.areNotificationsEnabled(_mActivity)){
            showToast(str);
        }else{
            new CookieBar.Builder(_mActivity)
                    .setIcon(icon)
                    .setBackgroundColor(R.color.toastbg)
                    .setLayoutGravity(Gravity.BOTTOM)
                    .setMessageColor(R.color.white)
                    .setMessage(str)
                    .show();
        }
    }

    public void showToast(String string){
        mToast = Toast.makeText(_mActivity, string,Toast.LENGTH_SHORT);
        mToast.show();
    }
    public <T> boolean checkNotNull(T reference) {
        if (reference == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onBackPressedSupport() {
        if (isBackEnable)
            return super.onBackPressedSupport();
        else
            return true;
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
                new AlertView(null, "\n登录过期需要重新登录", null, new String[]{"确定"}, null, _mActivity, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position==0){
                            Intent intent = new Intent();
                            intent.setClass(_mActivity,LoginActivity.class);
                            startActivity(intent);
                            _mActivity.finish();
                        }
                    }
                }).show();
                return true;
        }
        return false;
    }
}
