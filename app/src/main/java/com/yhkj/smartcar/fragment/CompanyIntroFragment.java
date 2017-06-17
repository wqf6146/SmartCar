package com.yhkj.smartcar.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.CompanyIntroBean;
import com.yhkj.smartcar.bean.ServerProtocolBean;
import com.yhkj.smartcar.http.SmartCarApi;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import im.delight.android.webview.AdvancedWebView;

/**
 * Created by Administrator on 2017/5/23.
 */

public class CompanyIntroFragment extends BaseToolbarFragment {

    @Bind(R.id.fp_btn_cancel)
    TextView mTvCancel;

    @Bind(R.id.fp_webview)
    AdvancedWebView mWebView;

    public static CompanyIntroFragment getInstance() {
        CompanyIntroFragment fragment = new CompanyIntroFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_protocol;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitleText("公司介绍");
        mWebView.setGeolocationEnabled(false);
        mWebView.setMixedContentAllowed(true);
        mWebView.setCookiesEnabled(true);
        mWebView.setThirdPartyCookiesEnabled(true);
        mWebView.setBackgroundColor(getResources().getColor(R.color.black_bg_21));
        mWebView.addHttpHeader("X-Requested-With", "");
        final SweetAlertDialog sDialog = showDialog("正在加载，请稍后‥");
        SmartCarApi.getInstance().getCompanyIntro(_mActivity, new ApiCallback<CompanyIntroBean.DataBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                sDialog.setTitleText("加载失败")
                        .setContentText(e.getMessage())
                        .showCancelButton(false)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(null)
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE,false,true);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(CompanyIntroBean.DataBean dataBean) {
                sDialog.dismissWithDelay(500);
                mWebView.loadDataWithBaseURL(null,dataBean.getIntroduce(),"text/html","UTF-8",null);
            }
        });
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }
}
