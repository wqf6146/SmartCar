package com.yhkj.smartcar.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.CompanyMsgBean;
import com.yhkj.smartcar.http.SmartCarApi;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/6/3.
 */

public class AboutUsFragment extends BaseToolbarFragment {


    @Bind(R.id.fa_img_companylogo)
    ImageView mImgCompanyLogo;

    @Bind(R.id.fa_tv_companyphone)
    TextView mTvCompanyPhone;

    @Bind(R.id.fa_rl_advance)
    RelativeLayout mRlAdvance;


    @Bind(R.id.fa_rl_phone)
    RelativeLayout mRlphone;

    @Bind(R.id.fa_rl_server)
    RelativeLayout mRlServer;

    @Bind(R.id.fa_rl_company)
    RelativeLayout mRlCompany;

    private CompanyMsgBean.DataBean mDataBean;
    public static AboutUsFragment getInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_aboutus;
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
        setTitleText("关于我们");

        SmartCarApi.getInstance().getCompanyInfo(_mActivity, new ApiCallback<CompanyMsgBean.DataBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(CompanyMsgBean.DataBean dataBean) {
                mDataBean = dataBean;
                Glide.with(_mActivity).load(dataBean.getLogo()).into(mImgCompanyLogo);
                mTvCompanyPhone.setText(String.format(getString(R.string.companyphone),dataBean.getTel()));
            }
        });
        mRlphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBean == null)
                    return;

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+mDataBean.getTel()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mRlServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(ProtocolFragment.getInstance());
            }
        });
        mRlAdvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(AdvanceReportFragment.getInstance());
            }
        });
        mRlCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(CompanyIntroFragment.getInstance());
            }
        });

    }
}
