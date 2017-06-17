package com.yhkj.smartcar.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;

import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.adapter.EquitMentAdapter;
import com.yhkj.smartcar.bean.EquitBean;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.utils.Constant;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/5/25.
 */

public class EquitManageFragment extends BaseToolbarFragment {

    @Bind(R.id.fe_recycleview)
    RecyclerView mRecycleView;

    @Bind(R.id.fe_btn_back)
    TextView mBtnBack;

    @Bind(R.id.fe_btn_bindnew)
    LinearLayout mBtnBindNew;


    public final static int REQUEST_CODE = 0x1;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_equitmanage;
    }

    public static EquitManageFragment getInstance() {
        EquitManageFragment fragment = new EquitManageFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
        mRecycleView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void initData() {
        super.initData();
        setTitleText("设备管理");
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        getEquitListData();
    }

    private void getEquitListData(){
        final SweetAlertDialog sDialog = showDialog("正在获取，请稍后‥");
        SmartCarApi.getInstance().getEquitList(_mActivity, new ApiCallback<EquitBean.DataBean>() {
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
                        .changeAlertType(SweetAlertDialog.WARNING_TYPE,false,true);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(EquitBean.DataBean equitBean) {
                sDialog.dismissWithDelay(500);
                EquitMentAdapter adapter = new EquitMentAdapter(_mActivity,equitBean.getList());
                adapter.setRecycleView(mRecycleView);
                mRecycleView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && Constant.RESULT_CODE_OK == resultCode){
            getEquitListData();
        }
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();

        mBtnBindNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BindCarFragment fragment = BindCarFragment.getInstance();
                Bundle bundle = fragment.getArguments();
                bundle.putBoolean("new",true);
                bundle.putInt("requestCode",REQUEST_CODE);
                startForResult(fragment,REQUEST_CODE);
//                BusFactory.getBus().post(new JumpFragmentEvent(fragment));
            }
        });
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

    }
}
