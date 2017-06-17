package com.yhkj.smartcar.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.NorBean;
import com.yhkj.smartcar.http.SmartCarApi;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/6/3.
 */

public class AdvanceReportFragment extends BaseToolbarFragment {

    @Bind(R.id.fa_edit_content)
    EditText mEditContent;

    @Bind(R.id.fa_edit_phone)
    EditText mEditPhone;

    @Bind(R.id.fa_btn_reports)
    TextView mTvReports;

    @Bind(R.id.fa_btn_back)
    TextView mTvBack;

    public static AdvanceReportFragment getInstance() {
        AdvanceReportFragment fragment = new AdvanceReportFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_advancereport;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();

        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mTvReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEditContent.getText().toString();
                String phone = mEditPhone.getText().toString();
                if (TextUtils.isEmpty(content)){
                    showTip(R.mipmap.warning_blue,"内容不能为空哦");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    showTip(R.mipmap.warning_blue,"请留下您的联系方式");
                    return;
                }
                final SweetAlertDialog sDialog = showDialog("正在提交，请稍后‥");
                SmartCarApi.getInstance().reportAdvance(_mActivity,
                        content, phone, new ApiCallback<NorBean>() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onError(ApiException e) {
                                sDialog.setTitleText("提交失败")
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
                            public void onNext(NorBean norBean) {
                                sDialog.dismissWithDelay(500);
                                showTip(R.mipmap.warning_blue,"提交成功");
                                pop();
                            }
                        });
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        setTitleText("意见反馈");
    }
}
