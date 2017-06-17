package com.yhkj.smartcar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vise.xsnow.event.BusFactory;
import com.yhkj.smartcar.BaseFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.activity.CarTrackActivity;
import com.yhkj.smartcar.activity.PositionActivity;
import com.yhkj.smartcar.bean.HomeMsgBean;
import com.yhkj.smartcar.event.JumpFragmentWithResultEvent;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/23.
 */

public class CarFragment extends BaseFragment {

    @Bind(R.id.fc_tv_cartitle)
    TextView mTvCarTitle;

    @Bind(R.id.fc_btn_track)
    LinearLayout mLlTrack;

    @Bind(R.id.fc_btn_position)
    LinearLayout mLlPosition;

    @Bind(R.id.fc_tv_car_in_t)
    TextView mTvCarInt;

    @Bind(R.id.fc_tv_car_out_t)
    TextView mTvCarOutt;

    public static CarFragment getInstance() {
        CarFragment fragment = new CarFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_car;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {

    }

    public void setData(HomeMsgBean.DataBean data){
        mTvCarTitle.setText(data.getPlatenum() + "已连接");
        mTvCarInt.setText(String.format(getResources().getString(R.string.car_in_t),data.getTemp()));
        mTvCarOutt.setText(String.format(getResources().getString(R.string.car_out_t),data.getHumidity()));

    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initData() {
        mLlTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(_mActivity, CarTrackActivity.class);
                startActivity(intent);
            }
        });
        mLlPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(_mActivity, PositionActivity.class);
                startActivity(intent);
            }
        });
    }
}
