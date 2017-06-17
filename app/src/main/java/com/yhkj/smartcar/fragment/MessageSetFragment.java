package com.yhkj.smartcar.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.App;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.LocalConfig;
import com.yhkj.smartcar.bean.NorBean;
import com.yhkj.smartcar.bean.db.DbHelper;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.utils.CommonUtils;
import com.yhkj.smartcar.view.SwitchView;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/27.
 */

public class MessageSetFragment extends BaseToolbarFragment {


    @Bind(R.id.fm_btn_fm_rl_allowsysmes)
    SwitchView mBtnSysMes;

    @Bind(R.id.ie_img_load_sysmes)
    ImageView mImgLoadSysMes;
    
    public static MessageSetFragment getInstance() {
        MessageSetFragment fragment = new MessageSetFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_messageset;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
    }

    @Override
    protected void initData() {
        super.initData();
        setTitleText("消息设置");
        mBtnSysMes.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                mImgLoadSysMes.setVisibility(View.VISIBLE);
                ((AnimationDrawable) mImgLoadSysMes.getBackground()).start();
                setSysMes(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                mImgLoadSysMes.setVisibility(View.VISIBLE);
                ((AnimationDrawable) mImgLoadSysMes.getBackground()).start();
                setSysMes(false);

            }
        });
        if ( App.getInstance().getUser()!=null )
            mBtnSysMes.setOpened(App.getInstance().getUser().getSysmes());
    }
    
    private void setSysMes(final boolean offOrOn){
        SmartCarApi.getInstance().setSysMes(_mActivity, new ApiCallback<Object>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                ((AnimationDrawable) mImgLoadSysMes.getBackground()).stop();
                mImgLoadSysMes.setVisibility(View.GONE);
                CommonUtils.showTip(_mActivity,R.mipmap.warning_blue,e.getMessage());
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(Object o) {
                ((AnimationDrawable) mImgLoadSysMes.getBackground()).stop();
                mImgLoadSysMes.setVisibility(View.GONE);
                mBtnSysMes.toggleSwitch(offOrOn);
                LocalConfig localConfig = App.getInstance().getUser();
                localConfig.setSysmes(offOrOn);
                DbHelper.getInstance().configLongDBManager().update(localConfig);
            }
        });
    }
}
