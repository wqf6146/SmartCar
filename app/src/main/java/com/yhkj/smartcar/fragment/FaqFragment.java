package com.yhkj.smartcar.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.adapter.FaqAdapter;
import com.yhkj.smartcar.adapter.MessageAdapter;
import com.yhkj.smartcar.bean.FaqBean;
import com.yhkj.smartcar.bean.MessageBean;
import com.yhkj.smartcar.http.SmartCarApi;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/5/27.
 */

public class FaqFragment extends BaseToolbarFragment {

    @Bind(R.id.ff_recycleview)
    RecyclerView mRecycleView;

    public static FaqFragment getInstance() {
        FaqFragment fragment = new FaqFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_faq;
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
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        final SweetAlertDialog sDialog = showDialog("正在获取，请稍后‥");
        SmartCarApi.getInstance().getFaqList(_mActivity, new ApiCallback<FaqBean.DataBean>() {
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
            public void onNext(FaqBean.DataBean faqBean) {
                sDialog.dismissWithDelay(500);
                mRecycleView.setAdapter(new FaqAdapter(FaqFragment.this,faqBean.getList()));
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        setTitleText("常见问题");
        mRecycleView.setLayoutManager(new LinearLayoutManager(_mActivity));


    }
}
