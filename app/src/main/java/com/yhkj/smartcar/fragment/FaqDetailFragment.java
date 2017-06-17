package com.yhkj.smartcar.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.FaqDetailBean;
import com.yhkj.smartcar.http.SmartCarApi;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import im.delight.android.webview.AdvancedWebView;

/**
 * Created by Administrator on 2017/5/27.
 */

public class FaqDetailFragment extends BaseToolbarFragment {

    @Bind(R.id.ff_webview)
    AdvancedWebView mWebView;

//    @Bind(R.id.ff_tv_title)
//    TextView mTvTitle;

    private String mId;
    private String mFlag;
    private String mNumb;
    public static FaqDetailFragment getInstance() {
        FaqDetailFragment fragment = new FaqDetailFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_faqdetail;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
        setTitleText("问题详情");

        mId = getArguments().getString("faqid");
        mFlag = getArguments().getString("flag");
        mNumb = getArguments().getString("num");
        mWebView.setGeolocationEnabled(false);
        mWebView.setMixedContentAllowed(true);
        mWebView.setCookiesEnabled(true);
        mWebView.setThirdPartyCookiesEnabled(true);
        mWebView.setBackgroundColor(getResources().getColor(R.color.black_bg_21));
        mWebView.addHttpHeader("X-Requested-With", "");
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
    }

    @Override
    protected void initData() {
        super.initData();

        if (TextUtils.isEmpty(mFlag)){
            final SweetAlertDialog sDialog = showDialog("正在获取，请稍后‥");
            SmartCarApi.getInstance().getFaqDetail(_mActivity, mId,mNumb, new ApiCallback<FaqDetailBean.DataBean>() {
                @Override
                public void onStart() {

                }

                @Override
                public void onError(ApiException e) {
                    sDialog.setTitleText("获取失败")
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
                public void onNext(FaqDetailBean.DataBean faqDetailBean) {
                    sDialog.dismissWithDelay(500);
                    mWebView.loadDataWithBaseURL(null,faqDetailBean.getAttachment(),"text/html","UTF-8",null);
                }
            });
        }else{
            final SweetAlertDialog sDialog = showDialog("正在获取，请稍后‥");
            SmartCarApi.getInstance().getQuestionDetail(_mActivity, mFlag, new ApiCallback<FaqDetailBean.DataBean>() {
                @Override
                public void onStart() {

                }

                @Override
                public void onError(ApiException e) {
                    sDialog.setTitleText("获取失败")
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
                public void onNext(FaqDetailBean.DataBean faqDetailBean) {
                    sDialog.dismissWithDelay(500);
                    mWebView.loadDataWithBaseURL(null,faqDetailBean.getAttachment(),"text/html","UTF-8",null);
                }
            });
        }


    }
}
