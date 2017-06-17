package com.yhkj.smartcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.vise.xsnow.net.func.ApiDataFunc;
import com.vise.xsnow.net.mode.ApiCode;
import com.vise.xsnow.net.mode.ApiResult;
import com.yhkj.smartcar.App;
import com.yhkj.smartcar.BaseActivity;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.LocalConfig;
import com.yhkj.smartcar.bean.LoginBean;
import com.yhkj.smartcar.bean.db.DbHelper;
import com.yhkj.smartcar.fragment.BindCarFragment;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.utils.CommonUtils;
import com.yhkj.smartcar.utils.SystemBarTintManager;

import java.util.List;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2017/5/27.
 */

public class LaunchActiviy extends BaseActivity {

    private TextView mTvskip;

    private int timeTag = 4;

    private Handler mHander = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            timeTag--;
            mTvskip.setText(String.format("%s 跳过",timeTag));
            if (timeTag > 1)
                mHander.sendEmptyMessageDelayed(1,1000);
            else
                doSkip();
            return false;
        }
    });

    private void doSkip() {
        mHander.removeMessages(1);
        //自动登录
        List<LocalConfig> localConfigList = DbHelper.getInstance().configLongDBManager().loadAll();

        if (localConfigList.size() > 0){
            LocalConfig localConfig = localConfigList.get(0);
            if (localConfig.getBAutoLogin() &&
                    !TextUtils.isEmpty(localConfig.getPhone()) && !TextUtils.isEmpty(localConfig.getPassword())){
//                doBackLogin(localConfig);
                App.getInstance().setUserBean(localConfig);
                Intent intent = new Intent();
                intent.setClass(LaunchActiviy.this, MainActivity.class);
                LaunchActiviy.this.startActivity(intent);
                LaunchActiviy.this.finish();
            }else{
                Intent intent = new Intent();
                intent.setClass(LaunchActiviy.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }else{
            Intent intent = new Intent();
            intent.setClass(LaunchActiviy.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initView();
        bindEvent();
        initData();
    }

    protected void bindEvent() {
        mTvskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSkip();
            }
        });
    }


    protected void initData() {
        mHander.sendEmptyMessageDelayed(1,1000);
    }

    protected void initView() {
        mTvskip = (TextView)findViewById(R.id.al_tv_skip);
    }
    private void doBackLogin(final LocalConfig localConfig){
        SmartCarApi.getInstance().startLogin(this, localConfig.getPhone(), localConfig.getPassword(), new ApiCallback<LoginBean.DataBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                CommonUtils.showTip(LaunchActiviy.this,R.mipmap.warning_blue,e.getMessage());
                Intent intent = new Intent();
                intent.setClass(LaunchActiviy.this,LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(LoginBean.DataBean loginBean) {
                App.getInstance().setUserBean(localConfig);
                if (loginBean.isHasbind()){
                    CommonUtils.showTip(LaunchActiviy.this,R.mipmap.warning_blue,"登录成功");
                    Intent intent = new Intent();
                    intent.setClass(LaunchActiviy.this, MainActivity.class);
                    LaunchActiviy.this.startActivity(intent);
                    LaunchActiviy.this.finish();
                }else {
                    Intent intent = new Intent();
                    intent.setClass(LaunchActiviy.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

//
//                if (loginBean.getCode() == 0){
//                }else{
//                    CommonUtils.showTip(LaunchActiviy.this,R.mipmap.warning_blue,loginBean.getMsg());
//                    Intent intent = new Intent();
//                    intent.setClass(LaunchActiviy.this,LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
            }
        });
    }
}
